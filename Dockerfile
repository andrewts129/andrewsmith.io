# syntax=docker/dockerfile:experimental

# Preprocesses static assets. Right now this only minifies JS files
FROM node:12.3.1-alpine AS front-end-processor
COPY . /
RUN npm install grunt grunt-contrib-uglify-es --save-dev && npm install -g grunt-cli && grunt

# Creates an executable binary of the project using sbt. Based on https://github.com/hseeberger/scala-sbt
FROM openjdk:11.0.3-jdk-stretch AS packager

# Env variables
ENV SCALA_VERSION 2.12.8
ENV SBT_VERSION 1.2.8
ENV PROJECT_VERSION 1.1

# Install Scala
## Piping curl directly in tar
RUN \
  curl -fsL https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo "export PATH=~/scala-$SCALA_VERSION/bin:$PATH" >> /root/.bashrc

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion && \
  mkdir project && \
  echo "scalaVersion := \"${SCALA_VERSION}\"" > build.sbt && \
  echo "sbt.version=${SBT_VERSION}" > project/build.properties && \
  echo "case object Temp" > Temp.scala && \
  sbt compile && \
  rm -r project && rm build.sbt && rm Temp.scala && rm -r target

# Define working directory
COPY . /root
COPY --from=front-end-processor /app/assets /root/app/assets
WORKDIR /root

RUN sbt dist
RUN set -x && unzip -d svc target/universal/*-$PROJECT_VERSION.zip && mv svc/*/* svc/ && rm svc/bin/*.bat && mv svc/bin/* svc/bin/start

# Lightweight image to execute the binary created above
FROM openjdk:11.0.3-jre-slim

COPY --from=packager /root/svc/. svc/

EXPOSE 8000
CMD svc/bin/start -Dhttp.port=8000 -Dplay.http.secret.key=$SECRET -J-Xmx256m -J-XX:+UseContainerSupport

# syntax=docker/dockerfile:experimental

# Preprocesses static assets. Right now this only minifies JS files
FROM node:12-alpine AS front-end-processor
COPY . /
RUN npm install grunt grunt-contrib-uglify-es --save-dev && npm install -g grunt-cli && grunt

# Creates an executable binary of the project using sbt. Based on https://github.com/hseeberger/scala-sbt
FROM openjdk:8u212 AS packager

# Env variables
ENV SCALA_VERSION 2.12.8
ENV SBT_VERSION 1.2.8
ENV PROJECT_VERSION 1.1
#ENV JAVA_OPTS "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"

# Scala expects this file
RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

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
  sbt sbtVersion

# Define working directory
COPY . /root
COPY --from=front-end-processor /app/assets /root/app/assets
WORKDIR /root

RUN sbt dist
RUN set -x && unzip -d svc target/universal/*-$PROJECT_VERSION.zip && mv svc/*/* svc/ && rm svc/bin/*.bat && mv svc/bin/* svc/bin/start

# Lightweight image to execute the binary created above
FROM openjdk:8u212-jre-alpine

# Play Framework seems to require bash
RUN apk add --no-cache bash
COPY --from=packager /root/svc/. svc/

# Does not do anything on Heroku; only for local testing
EXPOSE $PORT

CMD svc/bin/start -Dhttp.port=$PORT -Dplay.http.secret.key=$SECRET -J-Xmx128m -J-XX:+UnlockExperimentalVMOptions -J-XX:+UseCGroupMemoryLimitForHeap

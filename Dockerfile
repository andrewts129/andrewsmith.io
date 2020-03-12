FROM mozilla/sbt AS packager

COPY . /app
WORKDIR /app

RUN apt-get update && apt-get -y install curl gnupg && curl -sL https://deb.nodesource.com/setup_12.x  | bash - && apt-get -y install nodejs

ENV SCALAJS_PROD true
RUN sbt assembly

# TODO find a way to use a slimmer image
FROM openjdk:12-jdk

COPY --from=packager /app/server/target/scala-2.13/AndrewSmithDotIo-server-assembly-0.1.0-SNAPSHOT.jar /app.jar

ENV SCALAJS_PROD true
EXPOSE 8000
CMD java -jar /app.jar
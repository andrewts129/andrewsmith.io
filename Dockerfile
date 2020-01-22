FROM mozilla/sbt AS packager

COPY . /app
WORKDIR /app

RUN sbt assembly

FROM openjdk:12-jdk-alpine

COPY --from=packager /app/server/target/scala-2.12/AndrewSmithDotIo-server-assembly-0.1.0-SNAPSHOT.jar /app.jar

EXPOSE 4000
CMD java -jar /app.jar
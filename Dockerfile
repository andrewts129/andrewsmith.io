FROM mozilla/sbt AS packager

COPY . /app
WORKDIR /app

RUN sbt assembly

# TODO find a way to use a slimmer image
FROM openjdk:12-jdk

COPY --from=packager /app/server/target/scala-2.13/AndrewSmithDotIo-server-assembly-0.1.0-SNAPSHOT.jar /app.jar

EXPOSE 4000
CMD java -jar /app.jar
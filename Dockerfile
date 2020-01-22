FROM mozilla/sbt:latest

COPY . /app
WORKDIR /app

RUN sbt assembly

EXPOSE 4000
CMD java -jar server/target/scala-2.12/AndrewSmithDotIo-server-assembly-0.1.0-SNAPSHOT.jar
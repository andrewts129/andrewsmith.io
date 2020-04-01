FROM mozilla/sbt AS packager

RUN apt-get update && apt-get -y install curl gnupg && curl -sL https://deb.nodesource.com/setup_12.x  | bash - && apt-get -y install nodejs

COPY . /app
WORKDIR /app

RUN sbt assembly

# TODO find a way to use a slimmer image
FROM openjdk:12-jdk

COPY --from=packager /app/target/scala-2.13/AndrewSmithDotIo-assembly-1.2.jar /app.jar

EXPOSE 8000
CMD java -jar /app.jar
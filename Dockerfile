FROM mozilla/sbt:11.0.8_1.3.13 AS packager

RUN apt-get --allow-releaseinfo-change update && apt-get -y install curl gnupg && curl -sL https://deb.nodesource.com/setup_16.x  | bash - && apt-get -y install nodejs

COPY . /app
WORKDIR /app

RUN cd client && npm install && npm run build
RUN sbt assembly

FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

COPY --from=packager /app/server/target/scala-2.13/website-server-assembly-0.1.0-SNAPSHOT.jar /app.jar

EXPOSE 8000
CMD java -jar /app.jar

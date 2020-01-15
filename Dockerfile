#TODO this probably doesn't even work
FROM mozilla/sbt:latest
COPY . /
CMD sbt run
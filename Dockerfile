FROM maven:3.8.6-jdk-11 AS builder
WORKDIR /tmp
COPY ./src ./src
COPY ./pom.xml .
RUN mvn package

FROM openjdk:11
COPY --from=builder /tmp/target/app.jar /app/app.jar
EXPOSE 8080
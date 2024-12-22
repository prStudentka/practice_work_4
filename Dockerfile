FROM maven:3.9.9-eclipse-temurin-23-alpine

ENV MAVEN_CONFIG=/root/.m2

COPY pom.xml .

COPY src src

RUN mvn clean test

RUN mvn site:site
RUN mvn allure:serve

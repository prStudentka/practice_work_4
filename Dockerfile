FROM maven:3.9.9-eclipse-temurin-23-alpine

COPY pom.xml .
COPY src src

RUN mvn clean

CMD ["/bin/sh", "-c", "mvn test; mvn site:site; mvn allure:aggregate"]


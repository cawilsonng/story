FROM maven:3.8.5-openjdk-18 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:18-jdk
EXPOSE 8080
RUN mkdir /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-Dspring.config.location=/app/application.properties","-jar","/app/spring-boot-application.jar"]
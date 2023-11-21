FROM maven:3.5-jdk-8 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dskiptests

FROM openjdk:8-jre-alpine
COPY --from=builder /usr/src/app/target/weatherviewer-1.0.0.jar /usr/app/weatherviewer-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/weatherviewer-1.0.0.jar"]
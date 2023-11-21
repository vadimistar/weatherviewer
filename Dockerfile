FROM openjdk:8-jre-alpine
RUN ls
COPY target/weatherviewer-1.0.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
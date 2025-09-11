FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/hello-world-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

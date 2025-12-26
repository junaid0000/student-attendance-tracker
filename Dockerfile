# This keeps your Java 17 because Oracle removed ALL openjdk images from Docker Hub later 2022 so i am using FROM eclipse-temurin:17  distributions.
FROM eclipse-temurin:17

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY jar/movement-service-1.0.0.jar movement-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "movement-service.jar"]

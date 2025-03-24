FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY jar/customer-service-1.0.0.jar customer-service.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "customer-service.jar"]

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/store-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

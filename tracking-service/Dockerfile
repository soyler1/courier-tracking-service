FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/tracking-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

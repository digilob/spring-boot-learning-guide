# Build Stage
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy the rest of the source code
COPY src ./src
# Build the application and skip tests if desired
RUN mvn clean package -DskipTests

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the packaged jar from the build stage
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
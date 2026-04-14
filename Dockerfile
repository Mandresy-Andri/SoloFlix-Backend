# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first for better caching
RUN mvn dependency:go-offline -B
COPY src ./src
# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copy the built jar from the build stage. 
# We use a wildcard in case the version number changes in pom.xml
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# Step 1: Use a Maven image to build the Spring Boot application
FROM maven:3.8.5-openjdk-17 AS build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the pom.xml and download dependencies (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Step 4: Copy the entire project into the container
COPY . .

# Step 5: Package the Spring Boot application (skipping tests for faster builds)
RUN mvn clean package -DskipTests

# Step 6: Use a smaller JDK image to run the application
FROM openjdk:17-jdk-slim

# Step 7: Set the working directory in the container
WORKDIR /app

# Step 8: Copy the built jar file from the Maven build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Step 9: Expose port 8080 for the application
EXPOSE 8080

# Step 10: Run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]


FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/lector-pdf-1.0-jar-with-dependencies.jar app.jar
COPY documento.pdf .
CMD ["java", "-jar", "app.jar"]

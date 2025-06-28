FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/pdfextractor-1.0-jar-with-dependencies.jar app.jar
COPY --from=build /app/sample.pdf sample.pdf
CMD ["java", "-jar", "app.jar"]

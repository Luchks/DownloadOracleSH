
FROM maven:3.8.6-openjdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:11
COPY --from=build /app/target/lector-pdf-1.0-SNAPSHOT.jar app.jar
COPY documento.pdf documento.pdf
ENTRYPOINT ["java", "-jar", "app.jar"]


FROM openjdk:17

WORKDIR /app

COPY backend /app/backend
COPY sample.pdf /app/sample.pdf
COPY tabula/tabula.jar /app/tabula/tabula.jar

WORKDIR /app/backend

RUN javac *.java

CMD ["java", "backend.App"]

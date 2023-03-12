FROM openjdk:17-slim
COPY target/*.jar /app.jar
CMD java -jar app.jar
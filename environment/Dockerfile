FROM openjdk:18.0-slim
COPY target/*.jar Environment.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "Environment.jar"]

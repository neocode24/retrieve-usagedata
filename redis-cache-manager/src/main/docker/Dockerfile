FROM openjdk:8-jdk-alpine
ADD manage-cache-svc.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

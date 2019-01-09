FROM openjdk:8-jdk-alpine
ADD retrieve-usagedata-svc.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

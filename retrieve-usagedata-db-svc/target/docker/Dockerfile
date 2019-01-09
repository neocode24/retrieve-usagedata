FROM openjdk:8-jdk-alpine
ADD retrieve-usagedata-db-svc.jar app.jar
ENV POSTGRES_ENV_IP 127.0.0.1
ENV POSTGRES_ENV_PORT 5432
ENV POSTGRES_ENV_DB garage
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

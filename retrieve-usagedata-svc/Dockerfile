FROM openjdk:8-jdk-alpine
COPY ./target/usagedata.jar /usr/src/kos/app.jar
WORKDIR /usr/src/kos
# ENTRYPOINT ["/runApp.sh"]
# CMD ["-Dspring.redis.host=127.0.0.1 -Dspring.redis.port=6379"]
CMD java -Dspring.redis.host=127.0.0.1 -Dspring.redis.port=6379 -jar app.jar
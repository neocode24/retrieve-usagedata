FROM openjdk:8-jdk-alpine
COPY ./target/usagedata-db.jar /usr/src/kos/app.jar
WORKDIR /usr/src/kos
# ENTRYPOINT ["/runApp.sh"]
# CMD ["-Dspring.datasource.url=jdbc:postgresql://localhost/testdb", "-Dspring.data.source.username=kos_user", "-Dspring.datasource.password=new1234!"]
CMD java -Dspring.datasource.url=jdbc:postgresql://kos-mvp_db_1/testdb -Dspring.data.source.username=kos_user -Dspring.datasource.password=new1234! -jar app.jar
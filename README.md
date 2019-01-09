## 소스 다운로드

1. git 에서 소스 clone

```
git clone http://gitlab.169.56.74.6.nip.io/jhjang/kos-mvp.git
```



##  Project Tree



## Maven Build

1. Retreive DB

   ```s
   cd {Project Root}/retrieve-usagedata-svc
   mvn clean install -DskipTests
   ```

2. Retreive Redis

   ```shell
   cd {Project Root}/retrieve-usagedata-db-svc
   mvn clean install -DskipTests
   ```

## Docker Build

1. Retrieve DB

   - Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     VOLUME /tmp
     COPY ./target/usagedata.jar app.jar
     CMD java -Dspring.redis.host=kos-mvp_redis_1 -Dspring.redis.port=6379 -jar app.jar
     ```

2. Retrieve Redis

   - Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     COPY ./target/usagedata-db.jar app.jar
     CMD java -Dspring.datasource.url=jdbc:postgresql://kos-mvp_db_1:5432/testdb -Dspring.data.source.username=kos_user -Dspring.datasource.password=new1234! -jar /app.jar
     ```

## Docker Compose

1. docker-compose.yml

   ```
   version: '3' 
   services:
    web_redis:
     build:
      context: ./retrieve-usagedata-svc
     ports:
      - "8001:8001"
     depends_on:
      - redis
     networks:
      - network1
   
    web_db:
     build:
      context: ./retrieve-usagedata-db-svc
     ports:
      - "8002:8002"
     depends_on:
      - db
     networks:
      - network1
   
    redis:
     image: "redis:alpine"
     ports:
      - "6379:6379"
     networks:
      - network1
   
    db: 
     image: postgres
     environment:
      - POSTGRES_DB=testdb
      - POSTGRES_USER=kos_user
      - POSTGRES_PASSWORD=new1234!
      - POSTGRES_INITDB_ARGS=--encoding=UTF-8
     ports:
       - "5432:5432"
     volumes:
       - ./data/postgres:/var/lib/postgresql/data
       # - ./data/data.sql:/docker-entrypoint-initdb.d/init.sql
     networks:
      - network1
   
   networks:
    network1
   ```

   1. docker-compose run

   ```shell
   docker-compose up --build -d
   ```

   1. docker-compose stop

   ```
   docker-compose down
   ```

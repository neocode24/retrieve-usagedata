## 소스 다운로드

1. git clone

```
git clone https://github.com/neocode24/retrieve-usagedata.git
(git clone http://gitlab.169.56.74.6.nip.io/jhjang/kos-mvp.git)
```

##  Project Tree

  ```
  ├── data
  │   └── postgres
  ├── redis-cache-manager
  │   ├── src
  │   └── target
  ├── retrieve-usagedata-db-svc
  │   ├── src
  │   └── target
  └── retrieve-usagedata-svc
      ├── bin
      ├── src
      └── target
  ```

## Maven Build

1. Retreive UsageData Service

   ```shell
   > cd {Project Root}/retrieve-usagedata-svc
   > mvn clean install -DskipTests
   ```

2. Retreive DB Service

   ```shell
   > cd {Project Root}/retrieve-usagedata-db-svc
   > mvn clean install -DskipTests
   ```

3. Redis Cache Manager

   ```shell
   > cd {Project Root}/redis-cache-manager
   > mvn clean install -DskipTests
   ```

## Docker Build

1. Retreive UsageData Service

   - Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     COPY ./target/usagedata.jar /usr/src/kos/app.jar
     WORKDIR /usr/src/kos
     CMD java -Dspring.redis.host=kos-redis -Dspring.redis.port=6379 -jar app.jar
     ```

2. Retreive DB Service

   - Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     COPY ./target/usagedata-db.jar /usr/src/kos/app.jar
     WORKDIR /usr/src/kos
     CMD java -Dspring.datasource.url=jdbc:postgresql://kos-postgres/testdb -Dspring.data.source.username=kos_user -Dspring.datasource.password=new1234! -jar app.jar
     ```
3. Redis Cache Manager

   - Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     COPY ./target/cache-manager.jar /usr/src/kos/app.jar
     WORKDIR /usr/src/kos
     CMD java -Dspring.redis.host=kos-redis -Dspring.redis.port=6379 -jar app.jar
     ```

## Docker Compose

1. docker-compose.yml

   ```
    version: '3'
    services:
      redis:
        container_name: kos-redis
        image: "redis:alpine"
        ports:
        - "6379:6379"
        networks:
        - backend

      postgres:
        container_name: kos-postgres
        image: postgres:9.6.11
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
        - backend

      svc_redis:
        container_name: retrieve-usagedata-svc
        image: svc_redis
        build:
        context: ./retrieve-usagedata-svc
        ports:
        - "80:8080"
        depends_on: 
        - redis
        networks:
        - frontend
        - backend

      svc_db:
        container_name: retrieve-usagedata-db-svc
        image: svc_db
        build:
        context: ./retrieve-usagedata-db-svc
        depends_on: 
        - postgres
        networks:
        - backend

      manager:
        container_name: redis-cache-manager
        image: manager
        build:
        context: ./redis-cache-manager
        depends_on: 
        - redis
        networks:
        - backend

    networks:
      frontend:
      backend:
   ```

2. docker-compose up

   ```shell
    > docker-compose up --build -d

    ...
    Successfully built f90c3843dc6e
    Successfully tagged manager:latest
    Creating kos-redis    ... done
    Creating kos-postgres           ... done
    Creating retrieve-usagedata-svc    ... done
    Creating redis-cache-manager       ... done
    Creating retrieve-usagedata-db-svc ... done
   ```

3. docker-compose down

   ```
    > docker-compose down

    ...
    Stopping retrieve-usagedata-db-svc ... done
    Stopping retrieve-usagedata-svc    ... done
    Stopping redis-cache-manager       ... done
    Stopping kos-postgres              ... done
    Stopping kos-redis                 ... done
    Removing retrieve-usagedata-db-svc ... done
    Removing retrieve-usagedata-svc    ... done
    Removing redis-cache-manager       ... done
    Removing kos-postgres              ... done
    Removing kos-redis                 ... done
    Removing network kos-mvp_backend
    Removing network kos-mvp_frontend
   ```

## TODO
- Docker Maven Plugin

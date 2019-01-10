
## 요약
1. STS or Eclipse 에서 compile 및 docker build 까지 가능합니다. 빌드가 필요하시면 아래 내용 참고하세요.
2. Dockerfile 들이 간편하게 수정되었습니다. (각 프로젝트 src/main/docker 위치)
3. docker-compose 명령으로 모든 container가 정상 동작합니다.
   - kos-mvp-vmdb-docker-compose.yaml 로 실행하면, external VM DB에 접속하여 기능이 됩니다. 
   - kos-mvp-docker-compose.yaml 로 실행하면, container db로 접속합니다만, 데이터가 없어서 결과가 없습니다. (db insert 하며 됩니다.)
     (물리 공간 생성방식이니 insert 하시면 됩니다.)




### 프로젝트 초기 설정

1. STS 또는 Eclipse 인 경우 lombok 설치 필요. (개발 코드 수정 및 빌드 할 경우 필요)

   - lombok 라이브러리 위치에서 오른쪽 마우스로 executable  jar 실행

   - 자세한 설치 방법은 아래 참고. 

     ```
     http://countryxide.tistory.com/16
     ```




### [retrieve-usage-svc] 구성 설정(상세 내용 포함. 이하 프로젝트는 중복 생략)

1. 외부로 부터 서비스 요청받는 최 앞단 기능.

2. Spring Boot 기본 프로젝트 구조

   ```
   ├── mvnw
   ├── mvnw.cmd
   ├── pom.xml
   └── src
       ├── main
       │   ├── docker
       │   │   └── Dockerfile
       │   ├── java
       │   │   └── com
       │   │       └── garage
       │   │           └── usagedata
       │   │               ├── RetrieveUsagedataSvcApplication.java
       │   │               ├── bean
       │   │               │   ├── UsageData.java
       │   │               │   └── UsePtrn3monsRetvRedis.java
       │   │               ├── config
       │   │               │   ├── EmbeddedRedisConfig.java
       │   │               │   └── RedisRepositoryConfig.java
       │   │               ├── controller
       │   │               │   └── UsageDataController.java
       │   │               ├── remote
       │   │               │   └── bean
       │   │               │       ├── Data_DataSvcDrctlyUseQntList.java
       │   │               │       └── Data_UsePtrn3monsRetv.java
       │   │               └── repository
       │   │                   └── UsageDataRedisRepository.java
       │   └── resources
       │       └── application.properties
       └── test
           └── java
               └── com
                   └── garage
                       └── usagedata
                           └── RetrieveUsagedataSvcApplicationTests.java
   ```

3. docker file 포함.

   - 위치 : /src/main/docker/Dockerfile

     ```
     FROM openjdk:8-jdk-alpine
     ADD retrieve-usagedata-svc.jar app.jar
     ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
     ```

1. pom.xml 주요내용

   - docker build를 mvn 으로 진행하기 위한 항목 추가

   - docker image 명칭은 <imageName/> 테그에 명시함. 

     ```
     <build>
     		<finalName>retrieve-usagedata-svc</finalName>
     		<plugins>
     			<plugin>
     				<groupId>org.springframework.boot</groupId>
     				<artifactId>spring-boot-maven-plugin</artifactId>
     			</plugin>
     			
     			<plugin>
     			    <groupId>com.spotify</groupId>
     			    <artifactId>docker-maven-plugin</artifactId>
     			    <configuration>
     			        <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
     			        <dockerDirectory>src/main/docker</dockerDirectory>
     			        <resources>
     			            <resource>
     			                <targetPath>/</targetPath>
     			                <directory>${project.build.directory}</directory>
     			                <include>${project.build.finalName}.jar</include>
     			            </resource>
     			        </resources>
     			    </configuration>
     			</plugin>
     		</plugins>
     	</build>
     ```

2. build 방법

   - STS 또는Eclipse 일 경우 (SKIP 테스트 할 경우)

     - Run as --> Maven Build --> check skip Test --> goal 항목이 이하 내용 기술

       ```
       clean package docker:build
       ```

     - 프로젝트 정상 빌드 후 docker image 생성

       ```
       REPOSITORY                                        TAG                 IMAGE ID            CREATED             SIZE
       kos-mvp/usagedata                                 latest              a79276e0dec3        6 seconds ago       136MB
       ```

   - Console 일 경우

     - 프로젝트 위치에서 (SKIP 테스트할 경우)

       ```
       mvn clean package -DMaven.test.skip=true docker:build
       ```

   - 주의사항

     - docker build 이후 실행중인 상태에서 재빌드 시에는 docker image에 이전 이미지는 <none>  으로 변경됨.

     - docker 이미지 삭제 후 실행 필요.

       (TAG를 명시하지 않아, 재 생성이 동일한 이름과  TAG로 만들어 지기 떄문임.)

       ```
       REPOSITORY                                        TAG                 IMAGE ID            CREATED             SIZE
       kos-mvp/usagedata                                 latest              a79276e0dec3        6 seconds ago       136MB
       <none>                                            <none>              8dcd2ed42baa        20 minutes ago      136MB
       ```


1. docker 실행 방법

   - 생략(일반적인 방법 검색 권고). 아래는 예.

     ```
     docker run -p 8080:8080 -it kos-mvp/usagedata
     ```

   - 단, 시스템 변수로 전달할 수 있음.

     ```
     docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=local" -it kos-mvp/usagedata 
     ```



## [retrieve-usage-db-svc] 구성 설정

1. DB 와 커뮤니케이션을 전담하는 기능

2. Dockerfile 특이 사항

   - 외부 DB 연결에 대한 설정으로 SpringBoot 에 있는 application.properties 에 대한 실행 시점 변경에 대한 부분

   - 다양한 구성 설정 방법들

     1. Dockerfile 생성시 ENV 키워드 등록하여, application.properties 에서 참조하는 방법

        - Dockerfile

          ```properties
          FROM openjdk:8-jdk-alpine
          ADD retrieve-usagedata-db-svc.jar app.jar
          ENV POSTGRES_ENV_IP 127.0.0.1
          ENV POSTGRES_ENV_PORT 5432
          ENV POSTGRES_ENV_DB garage
          ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
          
          
          ```


        - application.properties 파일

          ```properties
          logging.level.root=info
          logging.level.com.garage.usagedata=debug
          
          spring.datasource.url=jdbc:postgresql://${POSTGRES_ENV_IP}:${POSTGRES_ENV_PORT}/${POSTGRES_ENV_DB}
          spring.datasource.username=kos_user
          spring.datasource.password=new1234!
          spring.jpa.generate-ddl=true
          
          spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
          
          target.service.name=redis
          
          
          ```

        - 실행

          -e "<ENV key = ENV value>" 형태로 입력.

          **-it 키워드 보다 앞에 위치 함에 주의.**

          ```
          docker run -p 8080:8080 -e "POSTGRES_ENV_IP=169.56.74.8" -e "POSTGRES_ENV_PORT=5432" -e "POSTGRES_ENV_DB=garage" -it kos-mvp/usagedata-db
          ```

     2. application.properties 파일에 최우선 적용하는 방법 (java -D 방식과 동일하게 적용)

        - application.properties 파일 (위 1번 사항과 동일.)

          ```properties
          logging.level.root=info
          logging.level.com.garage.usagedata=debug
          
          spring.datasource.url=jdbc:postgresql://${POSTGRES_ENV_IP}:${POSTGRES_ENV_PORT}/${POSTGRES_ENV_DB}
          spring.datasource.username=kos_user
          spring.datasource.password=new1234!
          spring.jpa.generate-ddl=true
          
          spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
          
          target.service.name=redis
          
          
          ```

        - 실행

          -e "<ENV key = ENV value>" 형태로 입력.

          application.properties에 소문자 형태로 기록되지만, 대문자 형태로 기술하는 것이 Reference 임.

          ```
          docker run -p 8080:8080 -e "SPRING_DATASOURCE_URL=jdbc:postgresql://169.56.74.8/garage" -it kos-mvp/usagedata-db
          ```

          또는 (소문자도 동작함.)

          ```
          docker run -p 8080:8080 -e "spring.datasource.url=jdbc:postgresql://169.56.74.8/garage" -it kos-mvp/usagedata-db
          ```


## [redis-cache-manager] 구성 설정

1. Cache 관리 기능.
2. 특이 사항 없음. 위 내용과 모두 동일.





## Docker Compose 설정

1. compose 파일 생성 (kos-mvp-docker-compose.yaml)

   - 영구 저장 볼륨이 필요한 경우 아래 키워드 사전에 실행 필요

     ```
     docker volumn create --name=data
     ```

   - 기본적으로 image는 위에서 개별 검증과정으로 존재한다는 가정하에, build는 왠만하면 수행하지 않는 것으로 함.

   ```yaml
   version: '3'
   services:
     usagedata:
       #    build: ./retrieve-usagedata-svc
       image: kos-mvp/usagedata
       environment:
         target.service.name: usagedata-db:8080
         spring.redis.host: redis
       ports:
        - "8080:8080"
       links:
        - usagedata-db
        - redis
   
     usagedata-db:
       #    build: ./retrieve-usagedata-db-svc
       image: kos-mvp/usagedata-db
       environment:
         target.service.name: manage-cache:8080
         POSTGRES_ENV_IP: postgres
         POSTGRES_ENV_PORT: 5432
         POSTGRES_ENV_DB: engage
       links:
        - manage-cache
        - postgres
   
     manage-cache:
       #    build: ./redis-cache-manager
       image: kos-mvp/manage-cache
       environment:
         spring.redis.host: redis
       links:
        - redis
   
     redis:
       image: "redis:alpine"
   
     postgres:
       image: postgres
       environment:
        - POSTGRES_DB=engage
        - POSTGRES_USER=kos_user
        - POSTGRES_PASSWORD=new1234!
        - POSTGRES_INITDB_ARGS=--encoding=UTF-8
       volumes:
         - data:/var/lib/postgresql/data
         # - ./data/data.sql:/docker-entrypoint-initdb.d/init.sql
   volumes:
     data:
       external: true
   ```

   - docker compose 실행

     ```
     docker-compose -f kos-mvp-docker-compose.yaml up -d --build
     ```

   - docker compose 종료

     - 종료는 하나, container가 살아 있음.

     - 다시 docker-compose up 해도 중복되지 않음.

       ```
       docker-compose down
       ```

   - docker compose 완전 종료

     ```
     docker-compose down --remove-orphans
     ```


1. compose 파일 생성 (외부 DB를 보는 경우. kos-mvp-vmdb-docker-compose.yaml)

   - 실행 및 종료 방법은 위와 동일.

   ```yaml
   version: '3'
   services:
     usagedata:
       #    build: ./retrieve-usagedata-svc
       image: kos-mvp/usagedata
       environment:
         target.service.name: usagedata-db:8080
         spring.redis.host: redis
       ports:
        - "8080:8080"
       links:
        - usagedata-db
        - redis
   
     usagedata-db:
       #    build: ./retrieve-usagedata-db-svc
       image: kos-mvp/usagedata-db
       environment:
         target.service.name: manage-cache:8080
         spring.datasource.url: jdbc:postgresql://169.56.74.8:5432/garage
         spring.datasource.username: kos_user
         spring.datasource.password: new1234!
       links:
        - manage-cache
   
     manage-cache:
       #    build: ./redis-cache-manager
       image: kos-mvp/manage-cache
       environment:
         spring.redis.host: redis
       links:
        - redis
   
     redis:
       image: "redis:alpine"
   ```




## 수행결과

1. DB(postgres) container 인 경우





1. DB(postgres) 외부 VM 인 경우

   - 호출

     ```
     curl localhost:8080/usage/usePtrn3monsRetv/680350947/201811
     ```

   - usagedata 로그 (pod1) : cache 유무로 DB 조회 서비스 호출

     ```
     c.g.u.controller.UsageDataController     : url:http://usagedata-db:8080/usage/usePtrn3monsRetv/680350947/201811
     c.g.u.controller.UsageDataController     : Return usage data from DB
     ```

   - usagedat-db 로그(pod2) : 실제 DB 조회 및 cache 저장 서비스 호출 후 응답

     ```
     c.g.u.c.UsePtrn3monsRetvController       : Get Data with ID = 680350947, 201811...
     c.g.u.c.UsePtrn3monsRetvController       : url:http://manage-cache:8080/cache/create/usePtrn3monsRetv-680350947-201811
     c.g.u.c.UsePtrn3monsRetvController       : cache result : Create
     ```

   - manage-cache 로그(pod3) : cache 생성 처리

     ```
     c.g.manage.controller.DataController     : Cache Create. key:[usePtrn3monsRetv-680350947-201811]
     ```




   - 호출 2회차

     ```
     curl localhost:8080/usage/usePtrn3monsRetv/680350947/201811
     ```

   - usagedata 로그 (pod1) : cache 응답.

     ```
     c.g.u.controller.UsageDataController     : Start [usePtrn3monsRetv] service...
     c.g.u.controller.UsageDataController     : Return usage data from Redis
     ```

   - cache 응답으로 이하 호출 없음.

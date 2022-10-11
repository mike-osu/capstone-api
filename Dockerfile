FROM maven:3.8.6-jdk-11

WORKDIR /app

COPY . .

RUN mvn package -Dmaven.test.skip

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/capstone.jar","--spring.profiles.active=docker"]
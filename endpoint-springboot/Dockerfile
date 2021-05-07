FROM maven:3.8.1-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -B -f /usr/src/app/pom.xml package -DskipTests

FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
COPY --from=build /usr/src/app/target/krakend-cache-test.jar /usr/app/krakend-cache-test.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/krakend-cache-test.jar"]
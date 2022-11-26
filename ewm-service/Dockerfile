FROM bellsoft/liberica-openjdk-alpine:11.0.16.1-1
COPY target/*.jar main-service.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "/main-service.jar"]
FROM openjdk:8
EXPOSE 8083
COPY target/krs-service.jar krs-service.jar
ENTRYPOINT ["java", "-jar", "/krs-service.jar"]
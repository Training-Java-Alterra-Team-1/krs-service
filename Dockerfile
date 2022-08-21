FROM openjdk:11
EXPOSE 8083
COPY target/krs-0.0.1.jar krs-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/krs-0.0.1.jar"]
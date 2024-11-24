FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY build/libs/lacak.challenge1-1.0.0.jar lacak.challenge1-1.0.0.jar
ENTRYPOINT ["java","-jar","/lacak.challenge1-1.0.0.jar"]
EXPOSE 8080

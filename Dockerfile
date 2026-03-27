FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/sayHello-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
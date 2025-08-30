FROM openjdk:21-jdk-slim
ARG JAVA_PATH=target/servicecube-0.0.1-SNAPSHOT.jar
COPY ${JAVA_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


FROM eclipse-temurin:21
WORKDIR /userservice

COPY ./target/userservice-0.0.1-SNAPSHOT.jar /userservice
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "userservice-0.0.1-SNAPSHOT.jar"]
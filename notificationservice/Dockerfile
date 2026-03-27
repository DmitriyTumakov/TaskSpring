FROM eclipse-temurin:21
WORKDIR /notificationservice

COPY ./target/notificationservice-0.0.1-SNAPSHOT.jar /notificationservice
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "notificationservice-0.0.1-SNAPSHOT.jar"]
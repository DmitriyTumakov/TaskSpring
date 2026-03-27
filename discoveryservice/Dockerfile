FROM eclipse-temurin:21
WORKDIR /discoveryservice

COPY ./target/discoveryservice-0.0.1-SNAPSHOT.jar /discoveryservice
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discoveryservice-0.0.1-SNAPSHOT.jar"]
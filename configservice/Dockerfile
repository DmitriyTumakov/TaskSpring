FROM eclipse-temurin:21
WORKDIR /configservice

COPY ./target/configservice-0.0.1-SNAPSHOT.jar /configservice
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "configservice-0.0.1-SNAPSHOT.jar"]
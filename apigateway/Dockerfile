FROM eclipse-temurin:21
WORKDIR /apigateway

COPY ./target/apigateway-0.0.1-SNAPSHOT.jar /apigateway
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "apigateway-0.0.1-SNAPSHOT.jar"]
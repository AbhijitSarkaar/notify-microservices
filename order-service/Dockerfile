
FROM eclipse-temurin:25

WORKDIR /root

COPY ./target/*.jar /root/orderservice.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "orderservice.jar"]

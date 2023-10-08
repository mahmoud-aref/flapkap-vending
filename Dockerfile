FROM eclipse-temurin:17

COPY build/libs/vending.jar app/vending.jar
ADD . /app
WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","vending.jar"]



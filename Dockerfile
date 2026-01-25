FROM amazoncorretto:17

WORKDIR /app

COPY /server/build/libs/server-1.0.0.jar server.jar

EXPOSE 5000

CMD ["java", "-jar", "server.jar"]
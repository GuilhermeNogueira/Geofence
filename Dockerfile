FROM openjdk:11-jdk-slim as app-builder

COPY . /home/app
WORKDIR /home/app
RUN ./gradlew --no-daemon --build-cache -x test clean bootJar

FROM openjdk:11-jre-slim

EXPOSE 8080

COPY /build/libs/*.jar geofence.jar
COPY /home/app/ .

CMD ["java","-jar", "geofence.jar"]
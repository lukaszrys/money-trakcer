FROM openjdk:11-jre-slim-sid
MAINTAINER Lukasz Rys <lukaszrys.java@gmail.com>

ADD ./build/libs/statistics-service.jar /app/
CMD ["java", "-Xmx512m", "-jar", "-Dspring.profiles.active=prod", "/app/statistics-service.jar"]

EXPOSE 8070
FROM openjdk:11-jre-slim-sid
MAINTAINER Lukasz Rys <lukaszrys.java@gmail.com>

ADD ./build/libs/account-service.jar /app/
CMD ["java", "-Xmx512m", "-jar", "-Dspring.profiles.active=prod", "/app/account-service.jar"]

EXPOSE 8080
FROM openjdk:8-jre

MAINTAINER "Dzmitry Kalachou <dmitry.kalachov@gmail.com>"

ADD hotel-*.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar

FROM openjdk:8-alpine

MAINTAINER Barbosa <infobarbosa@yahoo.com.br>

RUN apk add --no-cache wget bash \
    && mkdir -p /opt/B78Crawler

ADD target/B78Crawler-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/B78Crawler/B78Crawler.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/B78Crawler/B78Crawler.jar"]

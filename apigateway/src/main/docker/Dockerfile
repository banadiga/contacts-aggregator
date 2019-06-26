FROM anapsix/alpine-java:8

MAINTAINER contacts-aggregator

RUN mkdir -p /opt/contacts-aggregator
WORKDIR /opt/contacts-aggregator

ADD apigateway*.jar apigateway.jar
RUN bash -c 'touch ./apigateway.jar'

ENTRYPOINT ["java", "-jar", "./apigateway.jar"]


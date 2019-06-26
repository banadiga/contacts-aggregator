FROM anapsix/alpine-java:8

MAINTAINER contacts-aggregator

RUN mkdir -p /opt/contacts-aggregator
WORKDIR /opt/contacts-aggregator

ADD contacts-aggregator*.jar contacts-aggregator.jar
RUN bash -c 'touch ./contacts-aggregator.jar'

ENTRYPOINT ["java", "-jar", "./contacts-aggregator.jar"]


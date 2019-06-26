FROM anapsix/alpine-java:8

MAINTAINER ui

RUN mkdir -p /opt/ui
WORKDIR /opt/ui

ADD ui*.jar ui.jar
RUN bash -c 'touch ./ui.jar'

ENTRYPOINT ["java", "-jar", "./ui.jar"]


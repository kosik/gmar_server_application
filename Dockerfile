# This file is a template, and might need editing before it works on your project.
FROM maven:3.6-jdk-13 as BUILD

COPY . /usr/src/app
RUN mvn --batch-mode -f /usr/src/app/pom.xml clean package

#ENV PORT 4567
#EXPOSE 4567

RUN env

FROM tomcat:8.5.47
RUN apt-get update
RUN apt-get -qq -y install mc nano vim net-tools

RUN rm -vrf /usr/local/tomcat/webapps/*
RUN mkdir -pv /usr/local/tomcat/webapps/ROOT
#RUN mkdir -pv /mnt/dataStorage/storage/mavenRepo
#RUN mkdir -pv /mnt/dataStorage/projects/buzzer-space/doc/conf
RUN mkdir -pv /etc/buzzer
RUN mkdir -pv /var/log/buzzer

COPY ./docs/configs/web.xml /usr/local/tomcat/conf/web.xml
COPY ./docs/configs/server.xml /usr/local/tomcat/conf/server.xml 

COPY ./docs/configs/buzzer-local.properties /etc/buzzer/buzzer-local.properties
COPY ./docs/configs/web-local.properties /etc/buzzer/web-local.properties 
COPY ./docs/configs/log4j.properties /etc/buzzer/log4j.properties

#COPY ./docs/multitracking.key /mnt/dataStorage/projects/buzzer-space/doc/conf/multitracking.key

COPY --from=BUILD /usr/src/app/dist/webapp/target/gmar-webapp-1.0-SNAPSHOT/ /usr/local/tomcat/webapps/ROOT/

#RUN ls -la /usr/local/tomcat/webapps/

CMD sh /usr/local/tomcat/bin/catalina.sh jpda run


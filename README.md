# gmar_srv_app

To build and launch application follow the instruction:

- Download JDK: https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html
- Add distribution to the system classpath:

export JAVA_HOME=/mnt/dataStorage/storage/local/jdk-13_linux-x64_bin<br/>
export PATH=$PATH:$JAVA_HOME/bin

- Download maven and add it to the system classpath: http://maven.apache.org/download.cgi

export MAVEN_HOME=/mnt/dataStorage/storage/local/apache-maven-3.6.2
export PATH=$PATH:$MAVEN_HOME/bin

Note, maven fetchs all required librares automatically at user home directory. 
To keep things in a good order open /conf/settings.xml and put path to the local
repository which maven will use to store artifacts:

  <localRepository>/mnt/dataStorage/storage/mavenRepo</localRepository>
  
  - Go to the application root directory and compile the project:

$> mvn clean package

- Open ./gmar_srv_app/docs/configs directory. Copy gmar-local.properties 
and web-local.properties to /etc/buzzer/ directory. Here database and other instance
related configurations.

- Copy web.xml and server.xml into Tomcat /apache-tomcat-8.5.46/conf directory.
This is configurations of the application server port, SSL certificate password 
and redirect all HTTP requests to HTTPS - 443 port.

- Find ./gmar_srv_app/docs/scripts/local.sh to launch the server.

The server treats logs very scrupulous. 
Create folder /var/log/buzzer and monitor all kind of techie events there and
in /apache-tomcat-8.5.46/logs directories.
<br/>
<br/>
Congratulations!
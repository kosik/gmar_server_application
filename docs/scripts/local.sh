#killing local services by port
sh kill.sh

APP_SERVER_HOME=/mnt/dataStorage/projects/

rm $APP_SERVER_HOME/logs/*;
rm /var/log/buzzer-space/*;
sh $APP_SERVER_HOME/bin/catalina.sh stop;

#NOTE THE OD VARIABLE SHOULD POINT INTO ROOT PROJECT DIRECTORY
cd $BUZZER_HOME/server_app/
mvn clean package;

#enabling Remote debug feature on 8000 port
export MAVEN_OPTS=-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n

export CATALINA_OPTS="-Djava.awt.headless=true -Dfile.encoding=UTF-8
-server -Xms1536m -Xmx1536m
-XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m
-XX:MaxPermSize=256m -XX:+DisableExplicitGC"

#starting web server 
sh $APP_SERVER_HOME/bin/catalina.sh jpda start;

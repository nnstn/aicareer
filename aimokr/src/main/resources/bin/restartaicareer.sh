#!/bin/bash

BINDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

BASEPATH=`cd $BINDIR/..; pwd`

echo $BASEPATH

APP_NAME=$BASEPATH/mickafka-exec.jar
LOG_NAME=$BASEPATH/logs/mickafka.log

JAVA_OPTS="-server -Xmx4g -Xms4g -Xss256k -XX:MaxDirectMemorySize=1G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=40 -XX:+PrintGCDateStamps -Xloggc:/$BASEPATH/logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/$BASEPATH/logs/java.hprof -XX:+DisableExplicitGC -XX:-OmitStackTraceInFastThrow -XX:+PrintCommandLineFlags -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Djava.util.Arrays.useLegacyMergeSort=true -Dfile.encoding=UTF-8"


prepare(){
        if [ ! -d $BASEPATH/logs ];then
                mkdir $BASEPATH/logs
        fi
}
prepare;

#停止脚本
stop(){
    if [ -f $LOG_NAME ];then
        echo '备份日志:'$LOG_NAME
        cp $LOG_NAME $LOG_NAME.`date "+%Y%m%d%H%M"`
    fi

    tpid=`ps -ef|grep ${APP_NAME}|grep -v grep|grep -v kill|awk '{print $2}'`
    if [ ${tpid} ]; then
        echo 'Stop Process...'
        kill -15 ${tpid}
    fi

    tpid=`ps -ef|grep ${APP_NAME}|grep -v grep|grep -v kill|awk '{print $2}'`

    if [ ${tpid} ]; then
        echo 'Kill Process!'
        kill -9 ${tpid}
    else
        echo 'Stop Success!'
    fi
}
stop;

is_exist(){
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}' `
     #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    echo 'starting  Process!'
    if [ ! -f "$BASEPATH/conf/application.properties" ];then
        echo "nohup java $JAVA_OPTS -jar ${APP_NAME}  > $LOG_NAME 2>&1 &"
        nohup java $JAVA_OPTS -jar ${APP_NAME}  > $LOG_NAME 2>&1 &
    else
        #echo "nohup java -Dspring.config.location=$BASEPATH/conf/application.properties $JAVA_OPTS -jar ${APP_NAME}  > $LOG_NAME 2>&1 &"
        #nohup java -Dspring.config.location=$BASEPATH/conf/application.properties $JAVA_OPTS -jar ${APP_NAME}  > $LOG_NAME 2>&1 &
        nohup java  $JAVA_OPTS -jar ${APP_NAME}  > $LOG_NAME 2>&1 &
    fi
    echo "系统启动：${APP_NAME}"
  fi
}
start;

if [ "nolog" == $1"log" ]; then     #如果是linux的话打印linux字符串
    echo "started by script"
else
   tail -f $LOG_NAME
fi



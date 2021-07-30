#!/bin/sh
#
# ln -sf    /home/java/sdk_logs /home/java/logs
K8S_MASTER_IP=$HOST_IP
OS_SERVER_PORT=6700
OS_SERVER_CFG=doc
# MILVUS_ENGINE_TYPE=gpu
mkdir   -pv /home/java/models/serial/
###
#启动前端
\cp   /opt/web*.conf   /etc/nginx/conf.d/
\cp   /opt/nginx.conf   /etc/nginx/
\cp -r  /opt/dist/*    /usr/share/nginx/html/
echo  "while  [ true ]; do  sleep 5;echo \"nginx$(date)\" ; /etc/init.d/nginx restart   && break  1   ;done" > /nginx_start.sh
nohup  sh /nginx_start.sh  &

ls  -l  ${DIRECTORY}
echo  ${K8S_MASTER_IP}
export LD_LIBRARY_PATH=$PWD:$PWD/lib:$PWD/plugs:/usr/local/TensorRT-KL/lib/:/usr/local/libtorch/lib:$LD_LIBRARY_PATH
echo $LD_LIBRARY_PATH
#
CK="jdbc:clickhouse://clickhouse:48123/KoalaOsMagicData"
SQL="jdbc:mysql://my-mysql:43306/osmagic_app?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8"


java -Dfile.encoding=UTF-8 \
-Xloggc:/home/java/logs/gc.log \
-XX:ErrorFile=/home/java/logs/java_error.log \
-jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005   osmagic-sdk-1.0.jar \
--spring.datasource.password=admin \
--spring.datasource.jdbc-url=${SQL} \
--spring.clicksource.jdbc-url=${CK} \
--milvus.port=19530 \
--milvus.host=my-milvus-engine \
--osmagic.fdfs.url.upload=http://nginx-godfs:8095/group1/upload \
--spring.redis.host=my-redis-master \
--spring.redis.port=36379 \
--spring.profiles.active=${OS_SERVER_CFG} \
--milvus.engine-type=${MILVUS_ENGINE_TYPE} \
--server.port=${OS_SERVER_PORT} \
--os.version=${os_version}  \
--os.cluster=${os_cluster}  \
--k8s.port=46800        \
--k8s.master-ip=${K8S_MASTER_IP}     \
--k8s.domain-name=xxxxxx     \
--maxCameraInPerCard=16
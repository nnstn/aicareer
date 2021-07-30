#!/bin/bash
cd `dirname $0`
basepath=$(cd `dirname $0`; pwd)
mkdir  -pv /data/
ln  -sf  ${basepath}  /data/docker_cluster_start
#检查当前环境是否有显卡驱动
nvidia-smi && nvidia-docker  -v && docker-compose -version  && pbzip2 -V||  {
clear
echo "当前环境无显卡驱动/nvidia-docker/docker-compose 。  milvs,sdk将无法运行,终止启动"
echo "必须安装 440.82版本显卡驱动,nvidia-docker2,pbzip2,docker-ce 且配置好  /etc/docker/daemon.json"
exit 1
}
#检查端口占用信息
ss   -lntup| egrep   "46100|36379|48123|8095|49000|43306|46800|556|5005|6700|39082|19121|46100|3400|49000|19530|7500" && {
clear
echo "当前环境46100|36379|48123|8095|49000|43306|46800|556|5005|6700|39082|19121|46100|3400|49000|19530|7500 端口号冲突 OSMAGIC将无法运行,终止启动"
exit 1
}
#挂载目录占用信息
[[  -d   /data/ALLsoft/ ]] && [[  -d   /data/docker_cluster_start/ ]]  && [[  -d   /milvus_data/ ]] && {
clear
echo  -e "#当前环境以下目录已被占用/data/ALLsoft/  /data/docker_cluster_start/  /milvus_data/ \n#避免数据丢失终止启动,请将相关目录删除再执行启动脚本.\n#如果删除docker_cluster_start,请重新回到root目录后再次解压安装包重新执行脚本."
exit 1
}


IP=$(whiptail --title "#请输入集群本机Ip(master节点)并回车#" --inputbox "请检查IP是否一致,确定提交?" 10 60 "$IP" 3>&1 1>&2 2>&3)

[ -f ./images/images*.tar* ] && {
echo "正在导入离线镜像包.此处等待根据机器性能延长...请耐心等待即可"
pbzip2 -d -9  -p10  /data/docker_cluster_start/images/images_*.tar*  2>/dev/null
docker  load  -i  /data/docker_cluster_start/images/images_*.tar* 2>/dev/null
sdk_tag=$(docker images  192.168.2.100:5000/osmagic/java_cluster_sdk_docker| egrep  'TAG'  -v|head   -n 1|awk   '{print  $2}')
}


# IP=$(hostname -I |xargs -n 1   | grep  $(ip route |head  -n 1 | awk    '{print  $3}'  |  awk  -F  '.'  '{print  $1"."$2"."$3}')) ||IP=$(hostname -I |xargs -n 1   | grep  $(ip route |head  -n 1 | awk    '{print  $3}'  |  awk  -F  '.'  '{print  $1"."$2"."}'))
[ !  -n   "$sdk_tag" ]  &&  sdk_tag=$(docker images  192.168.2.100:5000/osmagic/java_cluster_sdk_docker|egrep   -v  "TAG|none" |awk '{print $1":"$2}'|head   -n 1 |awk  -F  ':'   '{print $NF}' )
[ !  -n   "$sdk_tag" ]  &&  sdk_tag=osmagic
echo $sdk_tag
sed  "s/127.0.0.1/$IP/g"  docker-compose.yml.bak  |sed  "s/d_version/${sdk_tag}/g"  |sed  "s/latest/${sdk_tag}/g"|sed  's/SDKMASTER/master/g' > docker-compose.yml
mkdir  -pv  /data/ALLsoft/milvus/conf/; \cp -avr ./milvus/* /data/ALLsoft/milvus/conf/
\cp -avr   ./sql   /data/ALLsoft/
# docker-compose up -d
docker-compose  -f /data/docker_cluster_start/docker-compose.yml   up -d  ||  {
    systemctl  restart  docker
    docker-compose  -f /data/docker_cluster_start/docker-compose.yml   up -d
}
#导入数据库
#检查数据库是否存在
while  [ true ]; do  sleep 1;echo "等待基础环境就绪";docker exec   -it osmagic-data_manager    mysql -uroot -padmin -h  localhost -P3400  -e "SHOW DATABASES;"  && break  1   ;done
while  [ true ]; do  sleep 1;echo "等待基础环境就绪";docker exec   -it  Allsoft-redis-mysql-ck-godfs  clickhouse-client  --host 127.0.0.1 --port 9000  -q    "show databases;"  && break  1   ;done

docker exec   -it  Allsoft-redis-mysql-ck-godfs  clickhouse-client  --host 127.0.0.1 --port 9000  -q    "show databases;" | grep KoalaOsMagicData|| {
while  [ true ]; do  sleep 1;echo "等待基础环境就绪";docker logs  Allsoft-redis-mysql-ck-godfs  | grep   8095   && break  1   ;done

docker exec  -it Allsoft-redis-mysql-ck-godfs sh /opt/sql/sql_install.sh  Import
docker exec  -it osmagic-data_manager  sh /opt/sql/sql_input_data_milvus.sh

}




docker-compose  -f /data/docker_cluster_start/docker-compose.yml    restart  sdk
echo  -e  "码极客docker版master节点启动完成\n1 mysql  外部端口  43306,密码admin\n2 redis外部端口  36379\n3 CK外部端口  49000\n4 gofastdfs外部端口  8095\n5 sdk外部端口  6700\n6 激活页面端口  39082  "





#导入sql

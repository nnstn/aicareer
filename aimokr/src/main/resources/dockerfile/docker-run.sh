#备注：docker 启动指定环境变量参数
docker run -d -it -p 9012:9012 -e "EUREKA_IP=192.168.162.128" -e "EUREKA_PORT=9011" -e "KAFKA_IP=172.18.199.14" -e "KAFKA_PORT=9092" -e "RECOGNITION_IP=172.18.199.14" -e "RECOGNITION_PORT=10102" --name=ciip-platform-test registry2/ciip-platform:1.5.0-SNAPSHOT

docker run  --restart=always -d -it -p 9012:9012 \
        -v /csp/applications/config/:/Intelligent/config/ \	#本地conf.properties配置文件绝对路径:/Intelligent/config/
        -v /csp/applications/images/ciip-platform/:/usr/resources/images/ciip-platform \	#图片保存文件夹:/usr/resources/images/ciip-platform
        -e "KAFKA_CLUSTER=10.183.80.141:9092" \	#上面配置的kafka本地主机ip
        -e "RECOGNITION_IP=172.18.199.14" \
        -e "RECOGNITION_PORT=10102" \
        -e "MYSQL_IP=10.183.80.141" \	#mysql所在主机ip
        -e "MYSQL_PORT=10106" \
	    -e "REDIS_IP=172.18.199.14" \
	    -e "REDIS_PORT=6379" \
        -e "INSTANCE_IP_ADDRESS=10.183.80.141" \	#上文eureka所在主机ip
        -e "DEFAULTZONE=http://admin:admin@10.183.80.141:9011/eureka" \ #上文eureka所在主机ip
        --name=platform registry2/ciip-platform:1.5.0-SNAPSHOT-model


        docker run  --restart=always -d -it -p 9012:9012 \
                -v /Intelligent/applications/config/:/Intelligent/config/ \
                -v /Intelligent/applications/images/ciip-platform/:/usr/resources/images/ciip-platform \
                -e "KAFKA_CLUSTER=172.18.233.120:9092" \
                -e "RECOGNITION_IP=172.18.233.120" \
                -e "RECOGNITION_PORT=10102" \
                -e "MYSQL_IP=172.18.233.120" \
                -e "MYSQL_PORT=3306" \
                -e "REDIS_IP=172.18.233.120" \
                -e "REDIS_PORT=6379" \
                -e "INSTANCE_IP_ADDRESS=172.18.233.120" \
                -e "DEFAULTZONE=http://admin:admin@172.18.233.120:9011/eureka" \
                --name=platform registry2/ciip-platform:1.5.0-SNAPSHOT-model
//优化后
 docker run  --restart=always -d -it -p 9012:9012 \
                        -v /Intelligent/applications/images/ciip-platform/:/usr/resources/images/ciip-platform \
                        -v /Intelligent/applications/ciip-platform/ciip-platform-exec.jar:/ciip-platform-exec.jar \
                        -v /Intelligent/applications/ciip-platform/logs:/logs \
                       --name=sitech-platform sitech/platform:latest
   // test 环境
   docker run  --restart=always -d -it -p 9012:9012 \
  -e "HOST_IP=172.18.194.74" \
  -v /Intelligent/applications/data/aiopen/:/Intelligent/applications/data/aiopen \
  -v /Intelligent/applications/ciip-platform/ciip-platform-exec.jar:/ciip-platform-exec.jar \
  -v /Intelligent/applications/ciip-platform/logs:/logs \
 --name=sitech-platform sitech/platform:latest

docker run  --restart=always -d -it -p 9012:9012 \
-e "HOST_IP=172.18.194.76" \
         -v /Intelligent/applications/data/aiopen/:/Intelligent/applications/data/aiopen \
        -v /Intelligent/applications/ciip-platform/ciip-platform-exec.jar:/ciip-platform-exec.jar \
        -v /Intelligent/applications/ciip-platform/logs:/logs \
        -v /Intelligent/applications/script/zipOperation.sh:/Intelligent/applications/script/zipOperation.sh \
        -v /Intelligent/nginxfileServer/website/file/download/images/:/Intelligent/file/download/images/  \
        --name=sitech-platform sitech/platform:latest

 容器文件下载服务器
 本地脚本.sh 同步到容器中，打包压缩均需要在容器中，然后将压缩打包的文件再映射到主机


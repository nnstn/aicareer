# 运行环境

FROM jdk8
MAINTAINER chenzq_ai
#持久化到指定目录
VOLUME /tmp
#VOLUME /log
ADD  ciip-platform-exec.jar /ciip-platform-exec.jar
#test环境设置host_ip
ENV HOST_IP 172.18.194.74
#设置系统编码
#RUN yum install kde-l10n-Chinese -y
#RUN yum install glibc-common -y
RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
ENV LC_ALL zh_CN.UTF-8
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
# 执行jar包  "-Djava.security.egd=file:/dev/./urandom"加快随机数产生过程
ENTRYPOINT ["java","-Duser.timezone=GMT+8","-Dspring.profiles.active=test","-Djava.security.egd=file:/dev/./urandom","-Dglobal.config.path=/Intelligent/config/conf.properties","-jar","/ciip-platform-exec.jar"]
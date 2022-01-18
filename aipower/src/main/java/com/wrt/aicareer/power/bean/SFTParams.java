package com.wrt.aicareer.power.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller()
/**
 * 
 * @author zhaoliang_ac
 * 配置文件为 /wsg-webapp/src/main/resources/config/test/restclient-config.properties
 */
public class SFTParams {
	//主机地址
	@Value("${file.host}")
    private String sftpReqHost;
	//主机端口
	@Value("${file.port:22}")
    private String sftpReqPort;
	//用户名
	@Value("${file.username}")
    private String sftpReqUsername;
	//密码
	@Value("${file.password}")
    private String sftpReqPassword;
	
	public String getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}

	@Value("${file.max_size:10000000}")
	private String maxSize;
	
	//文件上传到服务器目录
	@Value("${file.upload_dir:c}")
	private  String serverUploadLocationFolder;

	public String getServerUploadLocationFolder() {
		return serverUploadLocationFolder;
	}

	public void setServerUploadLocationFolder(String serverUploadLocationFolder) {
		this.serverUploadLocationFolder = serverUploadLocationFolder;
	}

	public String getSftpReqHost() {
		return sftpReqHost;
	}

	public void setSftpReqHost(String sftpReqHost) {
		this.sftpReqHost = sftpReqHost;
	}

	public String getSftpReqPort() {
		return sftpReqPort;
	}

	public void setSftpReqPort(String sftpReqPort) {
		this.sftpReqPort = sftpReqPort;
	}

	public String getSftpReqUsername() {
		return sftpReqUsername;
	}

	public void setSftpReqUsername(String sftpReqUsername) {
		this.sftpReqUsername = sftpReqUsername;
	}

	public String getSftpReqPassword() {
		return sftpReqPassword;
	}

	public void setSftpReqPassword(String sftpReqPassword) {
		this.sftpReqPassword = sftpReqPassword;
	}
}

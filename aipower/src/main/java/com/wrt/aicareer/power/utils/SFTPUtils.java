package com.wrt.aicareer.power.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.wrt.aicareer.power.bean.SFTParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
//import com.sitech.cfw.jadf.core.exception.CommonException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SFTPUtils {
	//使用ThreadLocal，防止并发产生的问题；
	private static  ThreadLocal<Session> threadSession =new ThreadLocal<Session>();
	private static  ThreadLocal<Channel> threadChannel =new ThreadLocal<Channel>();
	@Autowired
	private SFTParams sftpParams;
    private static final Logger LOG = LoggerFactory.getLogger(SFTPUtils.class);
   /**
    * @author zhaoliang_ac
    * @param ftpHost
    * @param ftpPort
    * @param ftpUserName
    * @param ftpPassword
    * @param timeout
    * @return
    * @throws JSchException
    */
    public ChannelSftp getChannel(String ftpHost,String ftpPort,String ftpUserName,String ftpPassword, int timeout) throws JSchException {
    	
    	Session session = null;
        Channel channel = null;
        
        int port = 22; //默认端口22
        /*if (ftpPort != null && !ftpPort.equals("")) {
            port = port;
        }*/
        
        JSch jsch = new JSch(); // 创建JSch对象
        
        session = jsch.getSession(ftpUserName, ftpHost, port); // 根据用户名，主机ip，端口获取一个Session对象
        LOG.debug("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        threadSession.set(session);//将session对象存到当前线程，关闭session时关闭当前线程的session
        LOG.debug("Session connected.");

        LOG.debug("Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        threadChannel.set(channel);//将channel对象存到当前线程，关闭channel时关闭当前线程的channel
        LOG.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (threadChannel.get() != null) {//从当前线程取出Channel
        	threadChannel.get().disconnect();
        }
        if (threadSession.get() != null) {//从当前线程取出Session
        	threadSession.get().disconnect();
        }
    }
    
    
	public ChannelSftp getChannelSftp() throws JSchException {
		ChannelSftp chSftp = this.getChannel(sftpParams.getSftpReqHost(),
				sftpParams.getSftpReqPort(),sftpParams.getSftpReqUsername(),sftpParams.getSftpReqPassword()
				, 60000);	
		return chSftp;
	}
	
	/**
	 * 创建文件目录 
	 * @param fileName 
	 * @param sftp sftp操作对象
	 * @return
	 * @throws SftpException
	 */
	public String getFilePath(String fileName,ChannelSftp sftp) {
        String baseFolder = sftpParams.getServerUploadLocationFolder();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
        String lastFolder = sdf.format(date);
        //文件路径 baseFolder/年/月/日
        String fileFolder = baseFolder + "/"  +   lastFolder;
        try {
        	if (isDirExist(fileFolder, sftp)) {  
    			sftp.cd(fileFolder);
    		}  
    	String pathArry[] = fileFolder.split("/");  
   		StringBuilder filePath = new StringBuilder("/");  
  		for (String path : pathArry) {  
   			if (path.equals("")) {  
    				continue;  
   			}  
   			filePath.append(path + "/");  
   			if (isDirExist(filePath.toString(), sftp)) {  
    				sftp.cd(filePath.toString());  
   			} else {  
  				// 建立目录  
    				sftp.mkdir(filePath.toString());
    				LOG.info("目录创建成功");
   				// 进入并设置为当前目录  
    				sftp.cd(filePath.toString());  
   			}  
   		}  
    		sftp.cd(fileFolder);  
    	} catch (SftpException e) {  
    		LOG.info("目录创建失败");
    	}  
        return fileFolder + "/" + fileName;
       // return fileFolder;
    }
	
	/**
	 * 判断是否有目录
	 * @param directory 目录
	 * @param sftp  sftp操作对象
	 * @return
	 */
	public static boolean isDirExist(String directory,ChannelSftp sftp) {  
    	boolean isDirExistFlag = false;  
    	try {  
    		SftpATTRS sftpATTRS = sftp.lstat(directory);  
    		isDirExistFlag = true;  
    		return sftpATTRS.isDir();  
    	} catch (Exception e) {  
    		if (e.getMessage().toLowerCase().equals("no such file")) {  
    			isDirExistFlag = false;  
    		}  
    	}  
    	return isDirExistFlag;  
    }  
	
	public String getMaxSize() {
		return sftpParams.getMaxSize();
	}
	
	//返回文件上传的目录
	public String getUploadDir() {
		return sftpParams.getServerUploadLocationFolder();
	}
}

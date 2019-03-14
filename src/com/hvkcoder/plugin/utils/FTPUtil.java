package com.hvkcoder.plugin.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.net.SocketException;

public class FTPUtil {

    public static FTPClient connectServert(String host, int port, String user, String password) {
        FTPClient ftpClient = new FTPClient();
        ftpClient = new FTPClient();
        int reply = 0 ;
        try {
            ftpClient.connect(host, port);// 连接FTP服务器
            ftpClient.login(user, password);// 登陆FTP服务器
            //登陆成功，返回码是230
            reply = ftpClient.getReplyCode();
            // 判断返回码是否合法
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }else{
               return ftpClient;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private FTPClient ftpClient;

    public synchronized boolean connectServer(String ftpIP, Integer ftpPort, String ftpUserName,
                                              String ftpPassword, String ftpEncode) {
        boolean flag = false;
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpEncode);//解决上传文件时文件名乱码
        int reply = 0 ;
        try {
            // 连接至服务器
            ftpClient.connect(ftpIP,ftpPort);
            // 登录服务器
            ftpClient.login(ftpUserName, ftpPassword);
            //登陆成功，返回码是230
            reply = ftpClient.getReplyCode();
            // 判断返回码是否合法
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }else{
                flag = true;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 断开与远程服务器的连接
     */
    public void closeClient(){
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
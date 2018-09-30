package com.clawhub.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * <Description> FTP工具 <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年04月27日<br>
 */
public class FtpUtil {
    /**
     * The constant ftpClient.
     */
    private static FTPClient ftpClient;
    /**
     * The constant BUFFER_SIZE.
     */
    private static final int BUFFER_SIZE = 1024;
    /**
     * The constant logger.
     */
    private static Logger logger = LogManager.getLogger(LogManager.class);

    /**
     * Description: Connect ftp <br>
     *
     * @param ftpConfig ftp config
     * @return boolean
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static boolean connectFTP(FtpConfig ftpConfig) {
        boolean flag = false;

        try {
            if (null != ftpConfig) {
                ftpClient = new FTPClient();
                ftpClient.connect(ftpConfig.getServerName(), ftpConfig.getProt());
                ftpClient.login(ftpConfig.getUserName(), ftpConfig.getPassword());
                ftpClient.changeWorkingDirectory("/");
                ftpClient.setRemoteVerificationEnabled(false);
                if (ftpConfig.getMode().equalsIgnoreCase("active")) {
                    ftpClient.enterLocalActiveMode();
                } else {
                    ftpClient.enterLocalPassiveMode();
                }
                flag = true;
            } else {
                flag = false;
            }
        } catch (SocketException var3) {
            flag = false;
            logger.error(var3.getMessage());
        } catch (IOException var4) {
            flag = false;
            logger.error(var4.getMessage());
        }

        return flag;
    }

    /**
     * Description: Upload file <br>
     *
     * @param localPath      local path
     * @param remotePath     remote path
     * @param targetFileName target file name
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static String uploadFile(String localPath, String remotePath, String targetFileName) {
        String statusCode = null;
        InputStream fis = null;

        try {
            File srcFile = new File(localPath);
            fis = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE);
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setBufferSize(BUFFER_SIZE);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(2);
            ftpClient.storeFile(targetFileName, fis);
            statusCode = ftpClient.getStatus();
        } catch (IOException var9) {
            throw new RuntimeException("FTP客户端出错！", var9);
        } finally {
            if (null != fis) {
                ftpClose(fis);
            }

        }

        return statusCode;
    }

//    public static void main(String[] args) {
//        FtpConfig ftpConfig = new FtpConfig();
//        ftpConfig.setMode("active");
//        ftpConfig.setServerName("221.226.184.138");
//        ftpConfig.setProt(5221);
//        ftpConfig.setUserName("platform2");
//        ftpConfig.setPassword("xskj20176yhn#EDC");
//        //获取连接
//        boolean isConnect = connectFTP(ftpConfig);
//        logger.info("获取连接:{}", isConnect);
//        //发送文件
//        String status = uploadFile("E:\\home\\data.zip", "/", new File("data.zip").getName());
//        logger.info("文件处理状态:{}", status);
//    }

    /**
     * Description: Ftp close <br>
     *
     * @param fis fis
     * @author LiZhiming <br>
     * @taskId <br>
     */
    private static void ftpClose(InputStream fis) {
        try {
            fis.close();
            if (null != ftpClient && ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException var2) {
            logger.error("close ftpclient error");
        }

    }
}

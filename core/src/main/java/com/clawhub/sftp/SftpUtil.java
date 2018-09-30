package com.clawhub.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * <Description> SFTP工具 <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年04月09日<br>
 */
public class SftpUtil {
    /**
     * 1分钟.
     */
    public static final int ONE_MINUTE = 60000;
    /**
     * 日志记录器
     */
    private static Logger logger = LogManager.getLogger(SftpUtil.class);
    /**
     * The Session.
     */
    private static Session session;

    /**
     * The constant channel.
     */
    private static Channel channel;

    /**
     * Description: 获得SFTP Channel <br>
     *
     * @param sftpBean sftp bean
     * @return channel sftp
     * @throws JSchException j sch exception
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static ChannelSftp getChannel(SftpBean sftpBean) throws JSchException {
        // 创建JSch对象
        JSch jsch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession(sftpBean.getSftpUserName(), sftpBean.getSftpHost(), sftpBean.getSftpPort());
        //logger.info("Session created.");
        if (sftpBean.getSftpPassword() != null) {
            // 设置密码
            session.setPassword(sftpBean.getSftpPassword());
        }
        Properties configTemp = new Properties();
        configTemp.put("StrictHostKeyChecking", "no");
        // 为Session对象设置properties
        session.setConfig(configTemp);
        // 设置timeout时间
        session.setTimeout(ONE_MINUTE);
        session.connect();
        // 通过Session建立链接
        // 打开SFTP通道
        channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        logger.info("Connected successfully to ftpHost = {} ,ftpUserName = {}", sftpBean.getSftpHost(), sftpBean.getSftpUserName());
        return (ChannelSftp) channel;
    }

    /**
     * Description: 断开SFTP Channel、Session连接 <br>
     *
     * @throws Exception exception
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        logger.info("disconnected SFTP successfully!");
    }

    /**
     * Description: 将源文件传送到目标路径<br>
     *
     * @param src      源文件
     * @param dst      目标文件
     * @param sftpBean SFTP信息
     * @throws Exception exception
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static void put(String src, String dst, SftpBean sftpBean) throws Exception {
        //获得SFTP Channel
        ChannelSftp chSftp = getChannel(sftpBean);
        //发送
        chSftp.put(src, dst, ChannelSftp.OVERWRITE);
        //退出
        chSftp.quit();
        //关闭连接
        closeChannel();
    }

    /**
     * Description: 将文件输入流写入远程目标文件 <br>
     *
     * @param inputStream input stream
     * @param dstDir      目标文件夹
     * @param fileName    文件名
     * @param chSftp      chSftp
     * @throws Exception exception
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static void put(InputStream inputStream, String dstDir, String fileName, ChannelSftp chSftp) throws Exception {
        //使用JSch远程创建递归目录
        createFolders(chSftp, dstDir);
        //输入流写入目标文件
        logger.debug("输入流写入目标文件");
        chSftp.put(inputStream, fileName, ChannelSftp.OVERWRITE);
        //回到根目录
        logger.debug("回到配置的目录");
        chSftp.cd("/");
    }

    /**
     * Description: Create folders <br>
     *
     * @param chSftp ch sftp
     * @param path   path
     * @author LiZhiming <br>
     * @taskId <br>
     */
    private static void createFolders(ChannelSftp chSftp, String path) {
        String[] folders = path.split("/");
        for (String folder : folders) {
            if (folder.length() > 0) {
                try {
                    logger.debug("尝试进入：{}", folder);
                    chSftp.cd(folder);
                } catch (SftpException e) {
                    logger.error("进入：{} 发生异常！{}", folder, e.getMessage());
                    try {
                        logger.debug("尝试创建目录：{}", folder);
                        chSftp.mkdir(folder);
                    } catch (SftpException e1) {
                        logger.error("创建目录：{} 发生异常！{}", folder, e1.getMessage());
                    }
                    try {
                        logger.debug("进入：{}", folder);
                        chSftp.cd(folder);
                    } catch (SftpException e1) {
                        logger.error("进入：{} 发生异常！{}", folder, e1.getMessage());
                    }
                }
            }
        }
    }
}

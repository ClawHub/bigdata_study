package com.clawhub.sftp;

/**
 * The type Sftp bean.
 */
public class SftpBean {
    /**
     * The Sftp host.
     */
    private String sftpHost;
    /**
     * The Sftp port.
     */
    private int sftpPort;
    /**
     * The Sftp user name.
     */
    private String sftpUserName;
    /**
     * The Sftp password.
     */
    private String sftpPassword;

    /**
     * Description: Get sftp host <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getSftpHost() {
        return sftpHost;
    }

    /**
     * Description: Set sftp host <br>
     *
     * @param sftpHost sftp host
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    /**
     * Description: Get sftp port <br>
     *
     * @return int
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public int getSftpPort() {
        return sftpPort;
    }

    /**
     * Description: Set sftp port <br>
     *
     * @param sftpPort sftp port
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setSftpPort(int sftpPort) {
        this.sftpPort = sftpPort;
    }

    /**
     * Description: Get sftp user name <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getSftpUserName() {
        return sftpUserName;
    }

    /**
     * Description: Set sftp user name <br>
     *
     * @param sftpUserName sftp user name
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setSftpUserName(String sftpUserName) {
        this.sftpUserName = sftpUserName;
    }

    /**
     * Description: Get sftp password <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getSftpPassword() {
        return sftpPassword;
    }

    /**
     * Description: Set sftp password <br>
     *
     * @param sftpPassword sftp password
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }
}

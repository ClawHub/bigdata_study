package com.clawhub.ftp;

/**
 * The type Ftp config.
 */
public class FtpConfig {
    /**
     * The Server name.
     */
    private String serverName;
    /**
     * The User name.
     */
    private String userName;
    /**
     * The Password.
     */
    private String password;
    /**
     * The Prot.
     */
    private int prot;
    /**
     * The Connection time.
     */
    private int connectionTime;
    /**
     * The Read time.
     */
    private int readTime;
    /**
     * The Mode.
     */
    private String mode;

    /**
     * Instantiates a new Ftp config.
     */
    public FtpConfig() {
    }

    /**
     * Description: Get server name <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Description: Set server name <br>
     *
     * @param newServerName new server name
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setServerName(String newServerName) {
        this.serverName = newServerName;
    }

    /**
     * Description: Get user name <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Description: Set user name <br>
     *
     * @param newUserName new user name
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    /**
     * Description: Get password <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Description: Set password <br>
     *
     * @param newPassword new password
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Description: Get prot <br>
     *
     * @return int
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public int getProt() {
        return this.prot;
    }

    /**
     * Description: Set prot <br>
     *
     * @param newProt new prot
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setProt(int newProt) {
        this.prot = newProt;
    }

    /**
     * Description: Get connection time <br>
     *
     * @return int
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public int getConnectionTime() {
        return this.connectionTime;
    }

    /**
     * Description: Set connection time <br>
     *
     * @param newConnectionTime new connection time
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setConnectionTime(int newConnectionTime) {
        this.connectionTime = newConnectionTime;
    }

    /**
     * Description: Get read time <br>
     *
     * @return int
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public int getReadTime() {
        return this.readTime;
    }

    /**
     * Description: Set read time <br>
     *
     * @param newReadTime new read time
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setReadTime(int newReadTime) {
        this.readTime = newReadTime;
    }

    /**
     * Description: Get mode <br>
     *
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String getMode() {
        return mode;
    }

    /**
     * Description: Set mode <br>
     *
     * @param mode mode
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}

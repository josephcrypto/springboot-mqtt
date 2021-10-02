package cn.coding.com.springbootmqtt.core;


/**
 * MQTT Configuration
 */
public abstract class MQTTConfig {

    protected String ip = "127.0.0.1";

    /**
     * qos0 For the client, there is one and only publish the package, for the broker, there is one and only one publish, in short, it is to send the package only once, regardless of whether it is received or not, suitable for those that are not very important data.
     * The qos1 interaction is the effect of one more ack, but there will be a problem. Although we can ensure that we must receive the message from the client or server through confirmation, we cannot guarantee that the message will only be once.
     * That is, when the client does not receive the puback of the service or the service does not receive the puback of the client, the publisher will always be sent
     * qos2 can accept only one message, its main principle (for publisher),
     * Publisher and broker are cached, where the publisher caches the message and msgID, and the broker caches the msgID, and both parties make records so that the message is not repeated,
     * But because the record needs to be deleted, the deletion process is also doubled
     */

    protected int qos = 2;

    protected Boolean  hasSSL = false; /* By default SSL is disable */

    protected Integer port = 1883; /* Default port */

    protected String username = "admin"; //username is account

    protected String password = "password";  //password is password

    protected String TCP = "tcp://";

    protected String SSL = "ssl://";

    /**
     * Custom Configuration
     */
    protected abstract void config(String ip, Integer port, Boolean ssl, Boolean withUserNamePass);

    /**
     * Default Configuration
     */
    protected abstract void config();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public Boolean getHasSSL() {
        return hasSSL;
    }

    public void setHasSSL(Boolean hasSSL) {
        this.hasSSL = hasSSL;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTCP() {
        return TCP;
    }

    public void setTCP(String TCP) {
        this.TCP = TCP;
    }

    public String getSSL() {
        return SSL;
    }

    public void setSSL(String SSL) {
        this.SSL = SSL;
    }
}

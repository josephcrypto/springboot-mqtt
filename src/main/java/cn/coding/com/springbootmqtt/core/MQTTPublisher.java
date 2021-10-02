package cn.coding.com.springbootmqtt.core;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MQTTPublisher extends MQTTConfig implements MqttCallback, IMQTTPublisher{

    private String ipUrl = null;

    final private String colon = ":"; //Colon separator
    final private String clientId = "mqtt_server_pub"; //Client ID can be defined arbitrarily here

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;

    private static final Logger logger = LoggerFactory.getLogger(MQTTPublisher.class);

    /**
     * Private Default constructor
     */
    private MQTTPublisher() {
        this.config();
    }

    /**
     * Private constructor
     * @param ip
     * @param port
     * @param ssl
     * @param withUserNamePass
     */
    private MQTTPublisher(String ip, Integer port, Boolean ssl, Boolean withUserNamePass) {
        this.config(ip, port, ssl, withUserNamePass);
    }

    /**
     *Factory method to get an instance of MQTTPublisher
     * @return MQTTPublisher
     */
    public static MQTTPublisher getInstance() {
        return new MQTTPublisher();
    }

    /**
     * Factory method to get an instance of MQTTPublisher
     * @param ip
     * @param port
     * @param ssl
     * @param withUserNamePass
     * @return
     */
    public static MQTTPublisher getInstance(String ip, Integer port, Boolean ssl, Boolean withUserNamePass) {
        return new MQTTPublisher(ip, port, ssl, withUserNamePass);
    }

    protected void config() {

        this.ipUrl = this.TCP + this.ip + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(ipUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    protected void config(String ip, Integer port, Boolean ssl, Boolean withUserNamePass) {
        String protocal = this.TCP;
        if (ssl) {
            protocal = this.SSL;
        }
        this.ipUrl = protocal + this.ip + colon + port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();

        try {
            this.mqttClient = new MqttClient(ipUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            if (withUserNamePass) {
                if (password != null) {
                    this.connectionOptions.setPassword(this.password.toCharArray());
                }
                if (username != null) {
                    this.connectionOptions.setUserName(this.username);
                }
            }
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @Override
    public void publishMessage(String topic, String message) {
            try {
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(this.qos);
                this.mqttClient.publish(topic, mqttMessage);
            } catch (MqttException me) {
                logger.error("ERROR", me);
            }
    }

    @Override
    public void disconnect() {
        try {
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("Connection Lost");
    }

    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        //Leave it blank for Publisher
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        logger.info("delivery completed");
    }
}

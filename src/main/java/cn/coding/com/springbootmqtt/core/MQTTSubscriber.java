package cn.coding.com.springbootmqtt.core;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MQTTSubscriber extends MQTTConfig implements MqttCallback, IMQTTSubscriber {


    private String brokerUrl = null;
    final private  String colon = ":"; //colon separator
    final private String clientId = "mqtt_server_sub"; //Client ID can be free defined here

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectOptions = null;
    private MemoryPersistence persistence = null;

    public static final Logger logger = LoggerFactory.getLogger(MQTTSubscriber.class);

    public MQTTSubscriber() {
        this.config();
    }

    @Override
    public void subscribeMessage(String topic) {
        try {
            this.mqttClient.subscribe(topic, this.qos);
        } catch (MqttException me) {
            me.printStackTrace();
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
    protected void config(String ip, Integer port, Boolean ssl, Boolean withUserNamePass) {
        String protocal = this.TCP;
        if (ssl) {
            protocal = this.SSL;
        }
        this.brokerUrl = protocal + this.ip + colon + port;
        this.persistence = new MemoryPersistence();
        this.connectOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectOptions.setCleanSession(true);
            if (withUserNamePass) {
                if (password != null) {
                    this.connectOptions.setPassword(this.password.toCharArray());
                }
                if (username != null) {
                    this.connectOptions.setUserName(this.username);
                }
            }
            this.mqttClient.connect(this.connectOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    @Override
    protected void config() {
        this.brokerUrl = this.TCP + this.ip + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectOptions.setCleanSession(true);
            this.mqttClient.connect(this.connectOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("Connection Lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // Called when a message arrives from the server that matches any subscription made by the client
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println();
        System.out.println("********************************************************************");
        System.out.println("Message arrival time: " + time + " Topic: " + topic + " Message: "
                            + new String(message.getPayload()));
        System.out.println("*********************************************************************");
        System.out.println();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //Leave it blank for subscriber
    }
}

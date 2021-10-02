package cn.coding.com.springbootmqtt.core;

public interface IMQTTPublisher {

    /**
     *
     * @param topic
     * @param message
     */

    public void publishMessage(String topic, String message);

    /**
     * Disconnect the MQTT client
     */
    public void disconnect();

}

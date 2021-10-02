package cn.coding.com.springbootmqtt.core;


public interface IMQTTSubscriber {

    /**
     *Subscribe to news
     * @param topic
     */
    public void subscribeMessage(String topic);

    /**
     * Disconnect MQTT client
     */
    public void disconnect();

}

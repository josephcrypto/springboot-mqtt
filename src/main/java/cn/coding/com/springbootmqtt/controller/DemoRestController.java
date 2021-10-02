package cn.coding.com.springbootmqtt.controller;

import cn.coding.com.springbootmqtt.core.IMQTTPublisher;
import cn.coding.com.springbootmqtt.core.IMQTTSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class DemoRestController {

    public static String TOPIC_LOOP_TEST = "mqtt/loop/message";

    @Autowired
    IMQTTPublisher publisher;

    @Autowired
    IMQTTSubscriber subscriber;


    /**
     * The method decorated by @PostConstruct will run when the server loads the Servlet, and will only be executed once by the server
     * PostConstruct is executed after the constructor and before the init() method. The PreDestroy() method is executed after destroy() method is intelligible
     * Initially subscribe to a topic here
     */
    @PostConstruct
    public void init() {
        subscriber.subscribeMessage(TOPIC_LOOP_TEST);
    }

    /**
     * Send a message to the specified topic
     * @param data
     * @return response
     */
    @RequestMapping(value = "/mqtt/loop/message", method = RequestMethod.POST)
    public String index(@RequestBody String data) {
        publisher.publishMessage(TOPIC_LOOP_TEST, data);
        return "Success";
    }
}

package com.  .activity.qna.messaging;

import java.util.Properties;
import java.util.UUID;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.  .prof_utilities.config.MedscapeAppConfig;

public class KafkaMessageProducer {

	private static Logger log = LoggerFactory.getLogger(KafkaMessageProducer.class);
	private final Producer<String, String> qnaMessageProducer;
	
	public KafkaMessageProducer(){
		
		Properties props = new Properties();
        props.setProperty("metadata.broker.list", MedscapeAppConfig.getString("//pars.kafkaBrokers"));
        props.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        props.setProperty("request.required.acks", MedscapeAppConfig.getString("//pars.producer.acks"));
        props.setProperty("producer.type", "sync");
        props.setProperty("request.timeout.ms", MedscapeAppConfig.getString("//pars.producer.requestTimeout"));
        props.setProperty("message.send.max.retries", MedscapeAppConfig.getString("//pars.producer.retries"));
        qnaMessageProducer = new Producer<String, String>(new ProducerConfig(props));
	}
	
	/**
	 * Sends message to pars queue in kafka to send any MOC credits to ABIM
	 * @param topic
	 * @param message
	 */
	public void sendMessage(String topic, String message) {

		log.debug("PRODUCING MESSAGE " + message);

        if (message != null) {
            KeyedMessage<String, String> msg = new KeyedMessage<>(topic, UUID.randomUUID().toString(), message);
            try {
            	qnaMessageProducer.send(msg);

            } catch (Exception e) {
	    		//Log fatal exception
	        	log.error("Exception producing message in regservice for topic " + topic + ".  Message: " + message + ". Exception was: " + e.getMessage());
            } 
        }
	}
	
}

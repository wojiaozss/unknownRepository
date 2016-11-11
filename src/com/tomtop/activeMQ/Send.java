/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2016-2030 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.tomtop.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 
 * @Package: com.tomtop.activeMQ  
 * @ClassName: Send 
 * @Description: TODO
 *
 * @author: zss 
 * @date: 2016年10月17日 下午8:12:37 
 * @version V1.0 
 

 */
public class Send {

	private static final int SEND_NUMBER = 5;
	private static final Logger logger = LoggerFactory.getLogger(Send.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("创建连接...");
		//创建连接工厂，JMS用它连接
		ConnectionFactory connectionFactory;
		//provider的连接  session: 一个发送或接收消息的线程 
		Connection connection = null;
		//Destination: 消息的目的地，
		Session session;
		//MessageProducer: 消息的发送者
		Destination destination;
		MessageProducer producer;
		//构造ConnectionFactory示例对象，此处采用activeMQ的实现jar
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://127.0.0.1:61616"
				);
		try {
			//构造从工厂连接到对象
			connection = connectionFactory.createConnection();
			//启动
			connection.start();
			//获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("FirstQueue");  
            // 得到消息生成者【发送者】  
            producer = session.createProducer(destination);  
            // 设置不持久化，此处学习，实际根据项目决定  
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
            // 构造消息，此处写死，项目就是参数，或者方法获取  
            sendMessage(session, producer);  
            session.commit(); 
		} catch (Exception e) {
			logger.debug("错误信息是 : ",e);
		} finally {
			try{
				if(null != connection)
					connection.close();
			} catch (Throwable ignore){}
		}
	}

	public static void sendMessage(Session session, MessageProducer producer)  
	        throws Exception {  
	    for (int i = 1; i <= SEND_NUMBER; i++) {  
	        TextMessage message = session.createTextMessage("ActiveMq 发送的消息"  
	                + i);  
	        // 发送消息到目的地方  
	
	        System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);  
	        producer.send(message);  
	    }  
	}
	
	
}

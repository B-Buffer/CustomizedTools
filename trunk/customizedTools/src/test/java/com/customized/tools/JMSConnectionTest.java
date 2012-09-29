package com.customized.tools;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JMSConnectionTest {

	public Context getContext(){
		
		Properties props = new Properties();  
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");  
        props.setProperty("java.naming.provider.url", "localhost:1099");  
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        props.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        props.setProperty(Context.SECURITY_CREDENTIALS, "admin");
	
        Context ctx= null;
        
        try {
			ctx = new InitialContext(props);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("Create JNDI Local Context Error");
		}
        
        return ctx;
	}
	
	public static void main(String[] args) throws Exception {
		new JMSConnectionTest().test();
	}
	

	private void test() throws Exception {

		Context ctx = this.getContext();
		
		QueueConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		
		try {
			factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			conn = factory.createConnection();
			
			conn.start();

			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue temporaryQueue = session.createTemporaryQueue();
			MessageProducer producer = session.createProducer(temporaryQueue);
			TextMessage msg = session.createTextMessage(" Hello, JMSConnectionTester");
			producer.send(msg);
			MessageConsumer consumer = session.createConsumer(temporaryQueue);
			TextMessage message = (TextMessage)consumer.receive(5000);
			System.out.println(message.getText());
		

		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null) {
				session.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}

}

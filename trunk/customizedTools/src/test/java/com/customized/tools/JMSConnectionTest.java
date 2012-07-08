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

import com.customized.tools.startup.ToolsConsole;

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
			throw new RuntimeException("Create JNDI Local Context Error");
		}
        
        return ctx;
	}
	
	public static void main(String[] args) throws Exception {
		new JMSConnectionTest().test();
	}
	
	private ToolsConsole console = new ToolsConsole();

	private void test() throws Exception {

		Context ctx = this.getContext();
		
		QueueConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		
		try {
			factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			conn = factory.createConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("\n  Create Connection Success");
			sb.append(" ["); 
			sb.append( conn.getMetaData().getJMSProviderName() + " - " + conn.getMetaData().getProviderVersion());
			sb.append("]\n");
			
			console.prompt(sb.toString());
			
			conn.start();

			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue temporaryQueue = session.createTemporaryQueue();
			
			console.prompt("\n  Create Temporary Queue [" + temporaryQueue.getQueueName() + "] Success");
			
			MessageProducer producer = session.createProducer(temporaryQueue);
			
			TextMessage msg = session.createTextMessage("Hello, JMSConnectionTester");
			
			console.prompt("\n  Create Message: " + msg.getText());
			
			producer.send(msg);
			
			console.prompt("\n  Message was successfully sent to [" + temporaryQueue.getQueueName() + "]");
			
			MessageConsumer consumer = session.createConsumer(temporaryQueue);
			
			console.prompt("\n  Create Message Consumer on [" + temporaryQueue.getQueueName() + "]");
			
			TextMessage message = (TextMessage)consumer.receive(5000);
			
			console.prompt("\n  Recieve Message: " + message.getText());
			
			console.prompt("\n JMS Connection Test Success");

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

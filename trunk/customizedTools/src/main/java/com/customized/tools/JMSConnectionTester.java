package com.customized.tools;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.customized.tools.jmsconntest.JMSConnectionTestException;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class JMSConnectionTester extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(JarClassSearcher.class);

	public JMSConnectionTester(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		logger.info("JMS Connection Test Start");
		
		try {
			Context ctx = initialContext();
			
			testConnection(ctx);
		} catch (Exception e) {
			
			JMSConnectionTestException ex = new JMSConnectionTestException("JMS Connection Test Failed ", e);
			
			console.prompt("\n Test Failed, due to " + ex.getMessage()  + "\n");
			
			ex.printStackTrace();
			
			logger.error(ex);
		}
	}

	private void testConnection(Context ctx) throws Exception{

		QueueConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		
		try {
			factory = (QueueConnectionFactory) ctx.lookup(props.getProperty("tester.jms.factory.jndiName", true));
			conn = factory.createConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("\n  Create Connection Success");
			sb.append(" ["); 
			sb.append( conn.getMetaData().getJMSProviderName() + " - " + conn.getMetaData().getProviderVersion());
			sb.append("]");
			
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
			
			console.prompt("\n  Recieve Message From [" + temporaryQueue.getQueueName() + "], Message Context: " + message.getText());
			
			console.prompt("\n  JMS Connection Test Success");

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

	private Context initialContext() throws NamingException {

		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, props.getProperty("tester.jms.factory", true));
		env.put(Context.PROVIDER_URL, props.getProperty("tester.jms.url", true));
		env.put(Context.URL_PKG_PREFIXES, props.getProperty("tester.jms.url.pkgs", true));
		env.put(Context.SECURITY_PRINCIPAL, props.getProperty("tester.jms.principle", false));
		env.put(Context.SECURITY_CREDENTIALS, props.getProperty("tester.jms.credentials", false));
		Context context = new InitialContext(env);
		
		logger.info("Create JNDI Local Context " + props.getProperty("tester.jms.url"));
		
		return context;
	}

	
}

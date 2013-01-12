package com.customized.tools.jmstester;

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

import com.customized.tools.cli.WizardConsole;
import com.customized.tools.po.JMSTester;

public class JMSConnectionTester {
	
	private static final Logger logger = Logger.getLogger(JMSConnectionTester.class);

	private JMSTester jmsTester ;
	
	private WizardConsole console ;
	
	public JMSConnectionTester(JMSTester jmsTester, WizardConsole console) {
		
		this.jmsTester = jmsTester;
		this.console = console ;
	}

	public void execute() {
		
		logger.info("JMS Connection Test Start");
		
		try {
			if(console.readFromCli("JMSConnectionTester")) {
				JMSTesterWizard wizard = (JMSTesterWizard) console.popWizard(new JMSTesterWizard(jmsTester));
				jmsTester = wizard.getJMSTester();
			}
			
			console.prompt("JMSConnectionTester JNDI Content: " + jmsTester);
			
			Context ctx = initialContext();
			
			testConnection(ctx);
		} catch (Exception e) {
			JMSConnectionTestException ex = new JMSConnectionTestException("JMS Connection Test Failed ", e);
			console.prompt("Test Failed, due to " + ex.getMessage() );
			logger.error("", ex);
			throw ex;
		}
	}

	private void testConnection(Context ctx) throws Exception{

		QueueConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		
		try {
			factory = (QueueConnectionFactory) ctx.lookup(jmsTester.getFactoryJNDIName());
			conn = factory.createConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("Create Connection Success");
			sb.append(" ["); 
			sb.append( conn.getMetaData().getJMSProviderName() + " - " + conn.getMetaData().getProviderVersion());
			sb.append("]");
			
			console.prompt(sb.toString());
			
			conn.start();

			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue temporaryQueue = session.createTemporaryQueue();
			
			console.prompt("Create Temporary Queue [" + temporaryQueue.getQueueName() + "] Success");
			
			MessageProducer producer = session.createProducer(temporaryQueue);
			
			TextMessage msg = session.createTextMessage("Hello, JMSConnectionTester");
			
			console.prompt("Create Message: " + msg.getText());
			
			producer.send(msg);
			
			console.prompt("Message was successfully sent to [" + temporaryQueue.getQueueName() + "]");
			
			MessageConsumer consumer = session.createConsumer(temporaryQueue);
			
			console.prompt("Create Message Consumer on [" + temporaryQueue.getQueueName() + "]");
			
			TextMessage message = (TextMessage)consumer.receive(5000);
			
			console.prompt("Recieve Message From [" + temporaryQueue.getQueueName() + "], Message Context: " + message.getText());
			
			console.prompt("JMS Connection Test Success");

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
		env.put(Context.INITIAL_CONTEXT_FACTORY, jmsTester.getFactoryClassName());
		env.put(Context.PROVIDER_URL, jmsTester.getUrl());
		env.put(Context.URL_PKG_PREFIXES, jmsTester.getPkgs());
		env.put(Context.SECURITY_PRINCIPAL, jmsTester.getPrinciple());
		env.put(Context.SECURITY_CREDENTIALS, jmsTester.getCredentials());
		Context context = new InitialContext(env);
		
		logger.info("Create JNDI Local Context " + jmsTester.getUrl());
		
		return context;
	}

	
}

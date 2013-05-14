package com.customized.tools.test.cst06;

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

import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;


public class CST06Reproduction {

	public static void main(String[] args) throws Exception {
				
//		reproduction(args[0]);
		
		ClassLoader classLoader = CST06Reproduction.class.getClassLoader();
		
//		org.jboss.modules.ModuleClassLoader mclassloader = (ModuleClassLoader) classLoader;
		
//		ModuleClassLoader classloader = (ModuleClassLoader) CST06Reproduction.class.getClassLoader();
//		Module module = (Module) CST06Reproduction.class.getClassLoader().getClass().getMethod("getModule").invoke(classLoader);
		System.out.println(CST06Reproduction.class.getClassLoader().getClass().getMethod("getModule").invoke(classLoader));
	}
	
	private static void reproduction(String libPath) throws Exception {

		System.out.println("Before Load JNDI Client libraries: " + Thread.currentThread().getContextClassLoader());
		
		MyURLClassLoader classLoader = new MyURLClassLoader(Thread.currentThread().getContextClassLoader());
		classLoader.loadDependencyJars(libPath);
		Thread.currentThread().setContextClassLoader(classLoader);
		
		System.out.println("After Load JNDI Client libraries: " + Thread.currentThread().getContextClassLoader());
		
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		env.put(Context.SECURITY_PRINCIPAL, "admin");
		env.put(Context.SECURITY_CREDENTIALS, "admin");
		Context ctx = new InitialContext(env);
		
		testConnection(ctx);
	}
	
	private static void testConnection(Context ctx) throws Exception{

		QueueConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		
		try {
			factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			conn = factory.createConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("Create Connection Success");
			sb.append(" ["); 
			sb.append( conn.getMetaData().getJMSProviderName() + " - " + conn.getMetaData().getProviderVersion());
			sb.append("]");
			
			System.out.println(sb.toString());
			
			conn.start();

			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue temporaryQueue = session.createTemporaryQueue();
			
			System.out.println("Create Temporary Queue [" + temporaryQueue.getQueueName() + "] Success");
			
			MessageProducer producer = session.createProducer(temporaryQueue);
			
			TextMessage msg = session.createTextMessage("Hello, JMSConnectionTester");
			
			System.out.println("Create Message: " + msg.getText());
			
			producer.send(msg);
			
			System.out.println("Message was successfully sent to [" + temporaryQueue.getQueueName() + "]");
			
			MessageConsumer consumer = session.createConsumer(temporaryQueue);
			
			System.out.println("Create Message Consumer on [" + temporaryQueue.getQueueName() + "]");
			
			TextMessage message = (TextMessage)consumer.receive(5000);
			
			System.out.println("Recieve Message From [" + temporaryQueue.getQueueName() + "], Message Context: " + message.getText());
			
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

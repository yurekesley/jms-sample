package br.yurekesley.jmssample.receiver;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TopicConsumerComercial {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		connection.start();

		boolean transacted = false;
		Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
		Topic topic = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topic, "subscriber01");

		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;

				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});

		new Scanner(System.in).nextLine(); // deixa programa em execução;
		connection.close();
		context.close();
	}

}

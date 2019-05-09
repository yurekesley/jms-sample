package br.yurekesley.jmssample.receiver;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class DLQConsumer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		boolean transacted = false;

		Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("DLQ");
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				System.out.println(message);
			}

		});

		new Scanner(System.in).nextLine();
		connection.close();
		context.close();
	}

}

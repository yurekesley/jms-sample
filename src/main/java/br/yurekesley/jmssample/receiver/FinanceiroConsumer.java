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
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FinanceiroConsumer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		boolean transacted = true;
		Session session = connection.createSession(transacted, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;

				try {
					session.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		new Scanner(System.in).nextLine();
		connection.close();
		context.close();
	}

	public static void simulaErro(String msg) throws Exception {
		System.out.println(msg);
		throw new Exception("Erro Não esperado");
	}
}

package br.yurekesley.jmssample.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class FinanceiroProducer {
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		boolean transacted = false;

		Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageProducer producer = session.createProducer(fila);

		Message message = session.createTextMessage("<pedido><id>13</id></pedido>");
		producer.send(message);

	}
}

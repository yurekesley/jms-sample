package br.yurekesley.jmssample.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.yurekesley.jmssample.model.Pedido;
import br.yurekesley.jmssample.model.PedidoFactory;

public class ProdutoProducer {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();

		boolean transacted = false;
		Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);

		Destination topico = (Destination) context.lookup("loja");

		MessageProducer producer = session.createProducer(topico);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();

		/**
			StringWriter writer = new StringWriter();
			JAXB.marshal(pedido, writer);
			String xml = writer.toString();
			Message message = session.createTextMessage(xml);
		**/
		
		Message message = session.createObjectMessage(pedido);		
		producer.send(message);

		session.close();
		connection.close();
		context.close();

	}

}

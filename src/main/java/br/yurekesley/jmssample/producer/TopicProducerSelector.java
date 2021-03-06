package br.yurekesley.jmssample.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TopicProducerSelector {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();	
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");	
		
		Connection connection = factory.createConnection();
		connection.start();
		
		boolean transacted = false;
        Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);


        Destination topico = (Destination) context.lookup("loja");
        
        
        MessageProducer producer = session.createProducer(topico);

        Message message = session.createTextMessage("<pedido><id>333</id></pedido>");
        
        message.setBooleanProperty("ebook", false);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
        
	}

}

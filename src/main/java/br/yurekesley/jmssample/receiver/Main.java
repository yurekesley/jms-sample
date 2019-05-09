package br.yurekesley.jmssample.receiver;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Essa classe faz a conexão com um Broker Active MQ porém.
 * Recebe uma mensagem por vez, baseada na fila de mensagem
 * 
 * */

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		//criando conexao com Active MQ a partir da fabrica
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		
		// criando um Consumer a partir da sessao Active MQ
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);

		//recebendo a mensagem
		Message message = consumer.receive();
		
		System.out.print("Mensagem JMS: "+ message);
		
		

		new Scanner(System.in).nextLine(); // deixa programa em execução;
		connection.close();
		context.close();
	}

}

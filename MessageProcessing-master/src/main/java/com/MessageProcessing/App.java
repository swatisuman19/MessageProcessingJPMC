package com.MessageProcessing;

import java.util.LinkedList;
import java.util.Queue;

import com.MessageProcessing.Interface.MessageConsumer;
import com.MessageProcessing.Interface.MessagePublisher;
import com.MessageProcessing.impl.ConsumerImpl;
import com.MessageProcessing.impl.PublisherImpl;

/**
 * 
 * App is the begin of the application.
 * Creates Message Queue & Consume/process Message Queue.
 *
 */
public class App 
{
	public static void main(String[] args) {
		Queue<String> messageQ = new LinkedList<String>();
		
		MessagePublisher publisher = new PublisherImpl();
		MessageConsumer consumer = new ConsumerImpl();
		String inputFile = null;

		if (args.length != 0) {
			inputFile = args[0];
		} else {
			System.out.println("*** No input file provided, loading default input file ***");
			inputFile = "inputMessages.txt";
		}

		messageQ = publisher.publishMessage(inputFile);

		consumer.consumeMessage(messageQ);

	}
}

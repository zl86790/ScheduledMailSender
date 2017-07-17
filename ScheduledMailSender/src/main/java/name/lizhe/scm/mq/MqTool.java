package name.lizhe.scm.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.MessageProperties;

public class MqTool {
    private final static String routingKey = "schmail_key";
    private final static String exchangeName = "schmail_echname";
    private final static String queueName = "sch_q";
    
    public static Channel getChannel() throws IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.28.128.4");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct");
        channel.queueDeclare(queueName, false, false, true, null);
        channel.queueBind(queueName, exchangeName, routingKey);  
        return channel;
    }
    
    public static void producer(String message) throws IOException, TimeoutException{
    	Channel channel = getChannel();
		channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		System.out.println("Producer Send +'" + message + "'");
		channel.close();
		channel.getConnection().close();
    }
    
    public static void consume() throws IOException, TimeoutException{
    	Channel channel = getChannel();
    	GetResponse response = channel.basicGet(queueName, true);
    	if(response!=null){
    		System.out.println("dummy send mail here ::: "+new String(response.getBody()));
    	}
    	
    	channel.close();
    	channel.getConnection().close();
    }

}

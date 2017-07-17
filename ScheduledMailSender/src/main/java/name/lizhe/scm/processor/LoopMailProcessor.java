package name.lizhe.scm.processor;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import name.lizhe.scm.factory.JedisFactory;
import name.lizhe.scm.mq.MqTool;
import redis.clients.jedis.Jedis;

public class LoopMailProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		System.out.println("Checking...");
		Jedis jedis = JedisFactory.getJedis();
		
		Calendar now = Calendar.getInstance();
		Calendar before1mins = Calendar.getInstance();
		before1mins.add(Calendar.MINUTE, -1);
		
		Set<String> results = jedis.zrangeByScore("schmails", before1mins.getTimeInMillis(), now.getTimeInMillis());
		jedis.zremrangeByScore("schmails", before1mins.getTimeInMillis(), now.getTimeInMillis());
		
		for(String res:results){
			System.out.println("Loop main:"+res);
			JSONObject obj = new JSONObject(res);
			String title = obj.get("title").toString();
			String content = obj.getString("content");
			JSONArray receivers = (JSONArray)obj.get("receivers");
			for(int i=0;i<receivers.length();i++){
				
				String receiver = receivers.get(i).toString();
				
				JSONObject jo = new JSONObject();
				jo.put("title",title);
				jo.put("content", content);
				jo.put("receiver",receiver);
				MqTool.producer(jo.toString());
			}
		}
	}

}

package name.lizhe.scm.mail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import name.lizhe.scm.factory.JedisFactory;
import redis.clients.jedis.Jedis;

public class CreateSchMail {

	public static void main(String[] args) {
		Jedis jedis = JedisFactory.getJedis();
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 2);
		Long after2mins = now.getTimeInMillis();
		
		List<String> receivers = new ArrayList<String>();
		receivers.add("zhe.li@test.com");
		receivers.add("test@test.com");
		
		String message = populateMailJsonText("Test Mail Subject", "this is a test content", receivers);
		
		jedis.zadd("schmails", after2mins, message);
	}
	
	private static String populateMailJsonText(String title,String content,List<String> receivers){
		
		JSONObject result = new JSONObject();
		try {
			result.put("title", title);
			result.put("content", content);
			result.put("receivers", receivers);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

}

import name.lizhe.scm.factory.JedisFactory;

public class Test {
	public static void main(String args[]){
		  System.out.println(JedisFactory.getJedis().zrangeByScore("schmails", 0, Long.MAX_VALUE));
//		  System.out.println(JedisFactory.getJedis().get("schmails"));
	}
}

package name.lizhe.scm.factory;

import redis.clients.jedis.Jedis;

public class JedisFactory {
	public static Jedis getJedis() {
		Jedis jedis = new Jedis("172.28.128.4");
		jedis.auth("lz12345");
		System.out.println("result: "+jedis.ping());
		return jedis;
	}
}

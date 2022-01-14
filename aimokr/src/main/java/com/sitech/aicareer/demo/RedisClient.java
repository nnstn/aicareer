package com.sitech.aicareer.demo;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis客户端
 */
public class RedisClient {



	public static void main(String[] args) {
		cluster();

	}

	public void singleton(){
		Jedis jedis = new Jedis("172.18.194.66", 6386);
		jedis.auth("foobared");
		for (int i=0; i<100; i++){
			jedis.set("hello-"+i,"world+"+i);

		}
		jedis.close();
	}
	public static void cluster() {

		//172.21.10.182-185
		//JedisPool
		int DEFAULT_TIMEOUT = 2000;
		int DEFAULT_REDIRECTIONS = 5;
		JedisPoolConfig DEFAULT_CONFIG = new JedisPoolConfig();
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("172.18.194.66", 6386));
		jedisClusterNodes.add(new HostAndPort("172.18.194.67", 6386));
		jedisClusterNodes.add(new HostAndPort("172.18.194.68", 6386));
		jedisClusterNodes.add(new HostAndPort("172.18.194.66", 6387));
		jedisClusterNodes.add(new HostAndPort("172.18.194.67", 6387));
		jedisClusterNodes.add(new HostAndPort("172.18.194.68", 6387));
		//JedisCluster jc = new JedisCluster(jedisClusterNodes);
		JedisCluster jc =  new JedisCluster(jedisClusterNodes, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_REDIRECTIONS, "foobared", DEFAULT_CONFIG);

		for (int i=0; i<10000; i++){
			System.out.println(i);
			try {
				jc.set("hello-"+i,"world+"+i);
				Thread.sleep(1000);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		String s = jc.set("abc", "def");
		System.out.println("jc set result = " + s);
		s = jc.set("abc", "def2");
		System.out.println("jc set result = " + s);
		long start = System.currentTimeMillis();
		long l = jc.del("abc");
		System.out.println("jc del result = " + l);
		l = jc.del("abc2");
		System.out.println("jc del result = " + l);
		for (int i = 0; i < 10; i++) {
			Long result = jc.hset("abc","sn" + i, getRandomString(10));
			System.out.println(i +" " + result);
			String result2 = jc.hget("abc","sn" + i);
			System.out.println(i +" " + result2);
		}
		for (int i = 0; i < 10; i++) {
			Long result = jc.hset("abc","sn" + i, getRandomString(10));
			String result2 = jc.hget("abc","sn" + i);
			System.out.println(i +" =>" + result2);
		}
		
		jc.del("abc");
		for (int i = 0; i < 20; i++) {
			String result = jc.hget("abc","sn" + i);
			System.out.println(i +" " + result);
		}
		
		for (int i = 0; i < 20; i++) {
			long result = jc.hdel("abc","sn" + i);
			System.out.println(i +" " + result);
		}
		long end = System.currentTimeMillis();
		System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0)
				+ " seconds");
		
		jc.set("wangjn", "1111111111111111111");
		System.out.println(jc.get("wangjn"));
		//jc.close();
		try {
			jc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(base.length());
	        sb.append(base.charAt(number));
	    }
	    return sb.toString();
	 }
}

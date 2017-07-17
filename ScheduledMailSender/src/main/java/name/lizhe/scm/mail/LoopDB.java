package name.lizhe.scm.mail;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import name.lizhe.scm.processor.LoopMailProcessor;

public class LoopDB {
	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("timer://foo?fixedRate=true&period=60000").process(new LoopMailProcessor());
			}
		});
		context.start();
		while(true){
			Thread.sleep(Long.MAX_VALUE);  
		}
	}
}

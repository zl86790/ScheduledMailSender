package name.lizhe.scm.mq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class LoopMq {
	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("timer://foo?fixedRate=true&period=30000").process(new Processor(){

					public void process(Exchange arg0) throws Exception {
						MqTool.consume();
					}
					
				});
			}
		});
		context.start();
		while(true){
			Thread.sleep(Long.MAX_VALUE);  
		}
	}
}

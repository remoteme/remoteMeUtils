package org.remoteme.utils.test;

import org.junit.Test;
import org.remoteme.utils.com.arLite.rest.v1.RemoteMeConnector;
import org.remoteme.utils.com.arLite.rest.v1.dto.HelloDto;

public class RestTest {


	@Test
	public void hello(){
		HelloDto hello = new HelloDto();
		hello.setUserName("XXX");
		hello.setHelloResponse("XXXXX");

		RemoteMeConnector feignConnector = new RemoteMeConnector();

		System.out.println(feignConnector.getHelloRest().getHelloDto());
		System.out.println(feignConnector.getHelloRest().getWithName("maciek"));



		System.out.println(feignConnector.getHelloRest().modify(hello));
	}

}

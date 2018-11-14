package org.remoteme.utils.test;

import org.junit.Test;
import org.remoteme.client.api.HellorestApi;
import org.remoteme.client.invoker.ApiException;
import org.remoteme.utils.com.arLite.rest.v1.RemoteMeConnector;
import org.remoteme.utils.com.arLite.rest.v1.dto.HelloDto;

public class Rest2Test {


	@Test
	public void hello() throws ApiException {
		HellorestApi api = new HellorestApi();


		HelloDto hello = new HelloDto();
		hello.setUserName("XXX");
		hello.setHelloResponse("XXXXX");


		System.out.println(api.getHelloDtoUsingGET1("asds","asds","asdds"));
		System.out.println(api.getWithNameUsingGET1("maciek","asds","asds","asdds"));


		org.remoteme.client.model.HelloDto helloX = new org.remoteme.client.model.HelloDto();
		helloX.setHelloResponse("asdasd");
		helloX.setUserName("XXXX");
		System.out.println(api.modifyUsingPOST1(helloX,"asds","asds","asdds"));
	}

}

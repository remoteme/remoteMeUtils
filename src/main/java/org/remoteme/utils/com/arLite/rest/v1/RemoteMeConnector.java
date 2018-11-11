package org.remoteme.utils.com.arLite.rest.v1;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.remoteme.utils.com.arLite.rest.v1.IHelloRest;
import org.remoteme.utils.com.connector.JacksonDecoder;
import org.remoteme.utils.com.connector.JacksonEncoder;

public class RemoteMeConnector {
	final IHelloRest helloRest;


	public RemoteMeConnector() {

		RequestInterceptor requestInterceptor=new RequestInterceptor(){

			@Override
			public void apply(RequestTemplate template) {
				template.header("for alter","For later");
				//template.header(AndroidToken.tokenU,LoginInformation.getInstance().getUserName());
				//template.header(AndroidToken.tokenP,AndroidToken.encode(LoginInformation.getInstance().getMd5Password()));
			}
		};

		helloRest = Feign.builder()

				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(IHelloRest.class, "http://127.0.0.1:8082/arLite/rest/v1");


	}

	public IHelloRest getHelloRest() {
		return helloRest;
	}
}

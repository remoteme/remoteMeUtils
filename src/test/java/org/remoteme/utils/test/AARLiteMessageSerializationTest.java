package org.remoteme.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.arLite.AARLiteMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.PingMessage;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AARLiteMessageSerializationTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
	}

	@Test
	public void pingMessage(){
		PingMessage userMessage = new PingMessage();

		assertThat(userMessage, reflectEquals(serializeDeserialize(userMessage)));


	}




	public AARLiteMessage serializeDeserialize(AARLiteMessage message){
		String str = JacksonHelper.serialize(message);
		return JacksonHelper.deserialize(str,AARLiteMessage.class);




	}
}

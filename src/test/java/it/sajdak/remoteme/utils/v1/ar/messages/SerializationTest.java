package it.sajdak.remoteme.utils.v1.ar.messages;

import it.sajdak.remoteme.utils.jackson.JacksonHelper;
import it.sajdak.remoteme.utils.v1.enums.UserMessageSettings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SerializationTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
	}

	@Test
	public void testSerialization(){
		String str="{\n" +
				"  \"type\" : \".SyncUserMessageDto\",\n" +
				"  \"receiverDeviceId\" : 1,\n" +
				"  \"messageId\" : 183562609012991,\n" +
				"  \"message\" : [ 1, 2, 3, 5, 8 ],\n" +
				"  \"senderDeviceId\" : 0\n" +
				"}";

		ARemoteMeMessageDto deserialize = JacksonHelper.deserialize(str, ARemoteMeMessageDto.class);
		System.out.println(JacksonHelper.serialize(deserialize));
	}


	@Test
	public void testSync2(){
		UserMessageDto userMessage = new UserMessageDto();
		userMessage.setMessageSettings(UserMessageSettings.NO_RENEWAL);
		userMessage.setSenderDeviceId(22);
		userMessage.setReceiveDeviceId(5);
		userMessage.setMessageId(123123);
		userMessage.setMessage(Arrays.asList(1,2,3,5,8,13,21));



		String serialize = JacksonHelper.serialize(userMessage);

		ARemoteMeMessageDto deserialize = JacksonHelper.deserialize(serialize, ARemoteMeMessageDto.class);
		System.out.println(serialize);
		Assert.assertTrue(deserialize instanceof UserMessageDto);
	}

	@Test
	public void testSync3(){
		PingMessageDto userMessage = new PingMessageDto();



		String serialize = JacksonHelper.serialize(userMessage);

		AMessageDto deserialize = JacksonHelper.deserialize(serialize, AMessageDto.class);
		System.out.println(serialize);
		Assert.assertTrue(deserialize instanceof PingMessageDto);
	}
}

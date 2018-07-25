package it.sajdak.remoteme.utils.test;

import it.sajdak.remoteme.utils.jackson.JacksonHelper;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

		JacksonHelper.deserialize(str,ARemoteMeMessageDto.class);
	}
}

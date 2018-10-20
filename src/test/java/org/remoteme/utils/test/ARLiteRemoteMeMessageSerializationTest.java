package org.remoteme.utils.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.AMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.CreateVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.NotifyAboutVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRemoveMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRenameMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.util.ArrayList;
import java.util.List;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.remoteme.utils.test.ARemoteMeMessageSerializationTest.reflectArrays;

@Slf4j
public class ARLiteRemoteMeMessageSerializationTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
	}





	@Test
	public void notifyAboutVariablesMessageTest(){


		NotifyAboutVariablesMessage um = new NotifyAboutVariablesMessage();
		List<VariableIdentifier> identifiers = new ArrayList<>();
		identifiers.add(new VariableIdentifier("asd", VariableType.BOOLEAN));
		identifiers.add(new VariableIdentifier("asd", VariableType.TEXT_2));
		identifiers.add(new VariableIdentifier("asd", VariableType.SMALL_INTEGER_2_TEXT_2));
		um.setIdentifiers(identifiers);




		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserializeJson(um),"identifiers"));



		reflectArrays(um.getIdentifiers(),  ((NotifyAboutVariablesMessage)serializeDeserializeJson(um)).getIdentifiers());

	}

	@Test
	public void renameVariableTest(){


		VariableRenameMessage um = new VariableRenameMessage(1234,"someOldName","someNewName", VariableType.BOOLEAN);

		System.out.println(JacksonHelper.serialize(um));

		assertThat(um, reflectEquals(serializeDeserializeJson(um),"data"));
		assertThat(um.getData(), reflectEquals(((VariableRenameMessage)serializeDeserializeJson(um)).getData()));


	}




	@Test
	public void removeVariableTest(){

		VariableRemoveMessage um = new VariableRemoveMessage(12534,"someName", VariableType.TEXT_2);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserializeJson(um),"data"));
		assertThat(um.getData(), reflectEquals(((VariableRemoveMessage)serializeDeserializeJson(um)).getData()));


	}

	@Test
	public void SmallInteger2Text2Test(){

		VariableRemoveMessage um = new VariableRemoveMessage(12534,"someName", VariableType.SMALL_INTEGER_2_TEXT_2);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserializeJson(um),"data"));
		assertThat(um.getData(), reflectEquals(((VariableRemoveMessage)serializeDeserializeJson(um)).getData()));


	}
	@Test
	public void cretateVariableMessage(){

		List<CreateVariablesMessage.VariableDetails> pam = new ArrayList<>();
		pam.add(new CreateVariablesMessage.VariableDetails("gamepad", VariableType.TEXT_2,false,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("gamepad", VariableType.SMALL_INTEGER_2_TEXT_2,false,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("button1", VariableType.BOOLEAN,true,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("button2", VariableType.BOOLEAN,true,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("button3", VariableType.BOOLEAN,true,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("relay1", VariableType.BOOLEAN,true,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("relay2", VariableType.BOOLEAN,true,false,false));
		pam.add(new CreateVariablesMessage.VariableDetails("temperature", VariableType.DOUBLE,true,true,false));

		CreateVariablesMessage um = new CreateVariablesMessage(pam);
		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserializeJson(um),"variables"));
		reflectArrays(um.getVariables(),  ((CreateVariablesMessage)serializeDeserializeJson(um)).getVariables());

	}


	public AMessage serializeDeserializeJson(AMessage message){
		String str = JacksonHelper.serialize(message);
		return JacksonHelper.deserialize(str,AMessage.class);


	}

}

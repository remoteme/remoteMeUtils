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
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.AddDataMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterLeafDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncResponseMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncUserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableObserveMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.AVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.BooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.DoubleVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerBooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger3VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.Text2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.TextVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.LeafDeviceType;
import org.remoteme.utils.messages.v1.enums.NetworkDeviceType;
import org.remoteme.utils.messages.v1.enums.SyncMessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;
import org.remoteme.utils.messages.v1.enums.VariableType;

import java.util.ArrayList;
import java.util.Arrays;
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
		um.setIdentifiers(identifiers);




		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserializeJson(um),"identifiers"));



		reflectArrays(um.getIdentifiers(),  ((NotifyAboutVariablesMessage)serializeDeserializeJson(um)).getIdentifiers());

	}

	@Test
	public void renameVariableTest(){


		VariableRenameMessage um = new VariableRenameMessage(1234,"someOldName","someNewName", VariableType.BOOLEAN);

		assertThat(um, reflectEquals(serializeDeserializeJson(um)));

	}




	@Test
	public void removeVariableTest(){

		VariableRemoveMessage um = new VariableRemoveMessage(12534,"someName", VariableType.TEXT_2);
		assertThat(um, reflectEquals(serializeDeserializeJson(um)));


	}

	@Test
	public void cretateVariableMessage(){

		CreateVariablesMessage um = new CreateVariablesMessage("someName", VariableType.TEXT_2,true,false);
		assertThat(um, reflectEquals(serializeDeserializeJson(um)));


	}


	public AMessage serializeDeserializeJson(AMessage message){
		String str = JacksonHelper.serialize(message);
		return JacksonHelper.deserialize(str,AMessage.class);


	}

}

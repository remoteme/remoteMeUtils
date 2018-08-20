package org.remoteme.utils.test;




import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.observers.AObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.BooleanObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.DoubleObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.IntegerBooleanObserverState;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.observers.ObserverIdentifier;
import org.remoteme.utils.messages.v1.core.messages.observers.IntegerObserverState;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRegisterMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRemoveMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRenameMessage;
import org.remoteme.utils.messages.v1.core.messages.observers.SmallInteger2ObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.SmallInteger3ObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.Text2ObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.TextObserverState;
import org.remoteme.utils.messages.v1.core.messages.observers.VariableOberverType;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.AddDataMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterLeafDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncResponseMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncUserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.enums.LeafDeviceType;
import org.remoteme.utils.messages.v1.enums.NetworkDeviceType;
import org.remoteme.utils.messages.v1.enums.SyncMessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ARemoteMeMessageSerializationTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
	}

	@Test
	public void userMessage(){
		UserMessage userMessage = new UserMessage(UserMessageSettings.NO_RENEWAL, 123,345,678,Arrays.asList(1,2,3,4,5));

		assertThat(userMessage, reflectEquals(serializeDeserialize(userMessage)));
	}

	@Test
	public void userSyncMessage(){
		SyncUserMessage userMessage = new SyncUserMessage(123,345,"01020304");

		assertThat(userMessage, reflectEquals(serializeDeserialize(userMessage)));

		String serialize = JacksonHelper.serialize(userMessage);
		Assert.assertTrue(serialize.indexOf("sentFromGui")==-1);
		System.out.println(serialize);

	}
	@Test
	public void UserMessageTest(){
		UserMessage um = new UserMessage(UserMessageSettings.NO_RENEWAL, 1, 2, 3, Arrays.asList(1, 2, 3, 4, 6));

		assertThat(um, reflectEquals(serializeDeserialize(um)));


		um = new UserMessage(UserMessageSettings.NO_RENEWAL, 5, 2, 3, Arrays.asList(1, 2, 3, 4, 6,9));
		assertThat(um, reflectEquals(serializeDeserialize(um)));

	}

	@Test
	public void AddDataMessageTest(){
		AddDataMessage um = new AddDataMessage(45678l, 12, 56.0);


		List<AddDataMessage.DataSeries> dataSeries = ((AddDataMessage) serializeDeserialize(um)).getDataSeries();
		reflectArrays(um.getDataSeries(), dataSeries);

	}

	private <T> void reflectArrays(List<T> dataSeries, List<T> listMatcher) {
		Assert.assertEquals(dataSeries.size(),listMatcher.size());
		for(int i=0;i<dataSeries.size();i++){
			assertThat(dataSeries.get(i), reflectEquals(listMatcher.get(i)));
		}

	}

	@Test
	public void RegisterChildDeviceMessageTest(){
		RegisterLeafDeviceMessage um = new RegisterLeafDeviceMessage(34,234,"maciek1", LeafDeviceType.OTHER_SOCKET,false);
		assertThat(um, reflectEquals(serializeDeserialize(um)));
	}

	@Test
	public void RegisterDeviceMessageTest(){
		RegisterDeviceMessage um = new RegisterDeviceMessage(34,"anna",  NetworkDeviceType.ARDUINO);
		assertThat(um, reflectEquals(serializeDeserialize(um)));
	}


	@Test
	public void SyncMessage(){

		SyncMessage um = new SyncMessage(34, SyncMessageType.GET_WEBRTC_CONENCTED_DEVICE_ID, "maciek1".getBytes());
		assertThat(um, reflectEquals(serializeDeserialize(um)));
	}

	@Test
	public void SyncUserMessage(){

		SyncUserMessage um = new SyncUserMessage(123,456,678975566,"maciek1".getBytes(),false);
		assertThat(um, reflectEquals(serializeDeserialize(um)));
	}

	@Test
	public void SyncResponseMessage(){

		SyncResponseMessage um = new SyncResponseMessage(12332123, "maciek1".getBytes());
		assertThat(um, reflectEquals(serializeDeserialize(um)));
	}


	@Test
	public void changeMessage(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new BooleanObserverState("pam1",true));
		states.add(new IntegerObserverState("pam2",123));
		states.add(new IntegerObserverState("pam3",123));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void integerObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new IntegerObserverState("pam2",123));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void textObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new TextObserverState("pam2","maciekółś"));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void smallInteger3ObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new SmallInteger3ObserverState("pam2",new SmallInteger3ObserverState.SmallInteger3(12,345,-6789)));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void smallInteger2ObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new SmallInteger2ObserverState("pam2",new SmallInteger2ObserverState.SmallInteger2(12,-6789)));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void integerBooleanObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new IntegerBooleanObserverState("pam2",new IntegerBooleanObserverState.IntegerBoolean(1234,true)));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void doubleObserverState(){

		List<AObserverState> states = new ArrayList<>();
		states.add(new DoubleObserverState("pam2",-1234.5677));

		ObserverChangeMessage um = new ObserverChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void changePropagateMessage(){

		List<AObserverState<?>> states = new ArrayList<>();
		states.add(new BooleanObserverState("pam1",true));
		states.add(new DoubleObserverState("pam2",123.456));
		states.add(new IntegerBooleanObserverState("pam3",123,true));
		states.add(new IntegerObserverState("pam3",-1345));
		states.add(new SmallInteger2ObserverState("pam3",123,-4567));
		states.add(new SmallInteger3ObserverState("pam3",123,-12345,6543));
		states.add(new Text2ObserverState("pam3","text1","text2"));
		states.add(new TextObserverState("pam3","text"));




		ObserverChangePropagateMessage um = new ObserverChangePropagateMessage(123,456,states);

		System.out.println(JacksonHelper.serialize(um));

		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((ObserverChangePropagateMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void RegisterObserverMessage(){

		List<ObserverIdentifier> states = new ArrayList<>();
		states.add(new ObserverIdentifier("motor",VariableOberverType.INTEGER));
		states.add(new ObserverIdentifier("led",VariableOberverType.BOOLEAN));

		ObserverRegisterMessage um = new ObserverRegisterMessage(456,states);



		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"observers"));
		reflectArrays(um.getObservers(),  ((ObserverRegisterMessage)serializeDeserialize(um)).getObservers());

	}


	@Test
	public void renameObserverTest(){


		ObserverRenameMessage um = new ObserverRenameMessage(1234,"someOldName","someNewName",VariableOberverType.BOOLEAN);




		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um)));


	}

	@Test
	public void removeObserverTest(){


		ObserverRemoveMessage um = new ObserverRemoveMessage(12534,"someName",VariableOberverType.TEXT_2);




		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um)));


	}


	public ARemoteMeMessage serializeDeserialize(ARemoteMeMessage message){
		String str = JacksonHelper.serialize(message);
		message=JacksonHelper.deserialize(str,ARemoteMeMessage.class);


		return ARemoteMeMessage.decode( message.toByteBuffer());


	}


}

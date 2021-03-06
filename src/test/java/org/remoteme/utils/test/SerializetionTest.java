package org.remoteme.utils.test;




import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.remoteme.utils.exceptions.CannotParseVariableValue;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.AMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableObserveMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.AVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.BooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.DoubleVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerBooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger2Text2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger3VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.Text2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.TextVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.values.AVariableValue;
import org.remoteme.utils.messages.v1.core.messages.variables.values.DoubleVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.IntegerBooleanVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger2VV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger3VV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;
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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.nitorcreations.Matchers.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;


public class SerializetionTest {

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

	public static <T> void reflectArrays(List<T> dataSeries, List<T> listMatcher) {
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

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new BooleanVariableState("pam1",true));
		states.add(new IntegerVariableState("pam2",123));
		states.add(new IntegerVariableState("pam3",123));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void integerVariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new IntegerVariableState("pam2",123));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		String serialize = JacksonHelper.serialize(um);
		VariableChangeMessage deserialize = JacksonHelper.deserialize(serialize, VariableChangeMessage.class);
		System.out.println(deserialize);
		System.out.println(serialize);
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void textVariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new TextVariableState("pam2","maciekółś"));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void smallInteger3VariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new SmallInteger3VariableState("pam2",new SmallInteger3VV(12,345,-6789)));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void smallInteger2VariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new SmallInteger2VariableState("pam2",new SmallInteger2VV(12,-6789)));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void integerBooleanVariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new IntegerBooleanVariableState("pam2",new IntegerBooleanVV(1234,true)));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void doubleVariableState(){

		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new DoubleVariableState("pam2",-1234.5677));

		VariableChangeMessage um = new VariableChangeMessage(123,Arrays.asList(1,2,3,4),states);

		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangeMessage)serializeDeserialize(um)).getStates());

	}

	@Test
	public void changePropagateMessage(){

		List<AVariableState<?>> states = getStates();

		VariableChangePropagateMessage um = new VariableChangePropagateMessage(123,456,states);

		assertThat(um, reflectEquals(serializeDeserialize(um),"states"));
		reflectArrays(um.getStates(),  ((VariableChangePropagateMessage)serializeDeserialize(um)).getStates());
	}

	@Test
	public void StateSerializeTest(){

		List<AVariableState<?>> states = getStates();

		for (AVariableState<?> state : states) {
			String serialize = AVariableState.serialize(state);
			AVariableState<?> deserialize = AVariableState.deserialize(serialize);
			Assert.assertEquals(state,deserialize);


			AVariableState fromConstructor = AVariableState.get(deserialize.getIdentifier().getName(),deserialize.getData());
			Assert.assertEquals(state,fromConstructor);

			AVariableState fromByteBuffer = AVariableState.get(fromConstructor.getIdentifier().getName(),fromConstructor.getData());
			Assert.assertEquals(state,fromByteBuffer);



			AVariableState<?> deserializeFinal = AVariableState.deserialize(AVariableState.serialize(fromByteBuffer));
			Assert.assertEquals(state,deserializeFinal);
			Assert.assertEquals(serialize, AVariableState.serialize(deserializeFinal));

		}
	}


	@Test
	public void ValueSerializeTest() throws CannotParseVariableValue {

		List<AVariableValue> states = getStates().stream().map(x->x.getData()).collect(Collectors.toList());

		for (AVariableValue value : states) {
			String serialize = AVariableValue.serialize(value);
			AVariableValue deserialize = AVariableValue.deserialize(serialize,value.getClass());
			Assert.assertEquals(value,deserialize);


			AVariableValue fromRendered = AVariableValue.get(deserialize.toString(),deserialize.getType());
			Assert.assertEquals(value,fromRendered);


			ByteBuffer bb= ByteBuffer.allocate(fromRendered.getDataSize());
			fromRendered.serializeData(bb);
			bb.rewind();

			AVariableValue fromByteBuffer = AVariableValue.get(bb,fromRendered.getType());
			Assert.assertEquals(value,fromByteBuffer);



			AVariableValue deserializeFinal = AVariableValue.deserialize( AVariableValue.serialize(fromByteBuffer),fromByteBuffer.getClass());
			Assert.assertEquals(value,deserializeFinal);

		}
	}


	private List<AVariableState<?>> getStates() {
		List<AVariableState<?>> states = new ArrayList<>();
		states.add(new BooleanVariableState("pam1",true));
		states.add(new DoubleVariableState("pam2",123.456));
		states.add(new IntegerBooleanVariableState("pam3",123,true));
		states.add(new IntegerVariableState("pam3",-1345));
		states.add(new SmallInteger2VariableState("pam3",123,-4567));
		states.add(new SmallInteger3VariableState("pam3",123,-12345,6543));
		states.add(new Text2VariableState("pam3","text1","text2"));
		states.add(new TextVariableState("pam3","text"));
		states.add(new SmallInteger2Text2VariableState("pam3",123,456,"124","343"));
		return states;
	}

	@Test
	public void RegisterVariableMessage(){

		List<VariableIdentifier> states = new ArrayList<>();
		states.add(new VariableIdentifier("motor", VariableType.INTEGER));
		states.add(new VariableIdentifier("button1", VariableType.BOOLEAN));
		states.add(new VariableIdentifier("button2", VariableType.BOOLEAN));
		states.add(new VariableIdentifier("button3", VariableType.BOOLEAN));
		states.add(new VariableIdentifier("relay1", VariableType.BOOLEAN));
		states.add(new VariableIdentifier("relay2", VariableType.BOOLEAN));

		VariableObserveMessage um = new VariableObserveMessage(456,states);



		System.out.println(JacksonHelper.serialize(um));
		assertThat(um, reflectEquals(serializeDeserialize(um),"variables"));
		reflectArrays(um.getVariables(),  ((VariableObserveMessage)serializeDeserialize(um)).getVariables());

	}




	public ARemoteMeMessage serializeDeserialize(ARemoteMeMessage message){
		String str = JacksonHelper.serialize(message);
		message=(ARemoteMeMessage)JacksonHelper.deserialize(str,AMessage.class);


		return ARemoteMeMessage.decode( message.toByteBuffer());


	}

}

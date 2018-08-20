package org.remoteme.utils.messages.v1.core.messages.observerStates;

import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Getter
public class ObserverIdentifier implements Serializable {


	String name;
	VariableOberverType type;

	protected ObserverIdentifier(){

	}

	public ObserverIdentifier(String name, VariableOberverType type){
		this.name=name;
		this.type=type;
	}






	public ObserverIdentifier(ByteBuffer output){
		type = VariableOberverType.getById(Short.toUnsignedInt(output.getShort()));
		name =ByteBufferUtils.readString(output);

	}

	protected  void serialize(ByteBuffer output){
		output.putShort((short) getType().getId());
		output.put(getName().getBytes(StandardCharsets.UTF_8));
		output.put((byte)0);

	}

}

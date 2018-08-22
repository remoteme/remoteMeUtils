package org.remoteme.utils.messages.v1.core.messages.observers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.VariableOberverType;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Getter
@EqualsAndHashCode
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

	public  void serialize(ByteBuffer output){
		output.putShort((short) getType().getId());
		output.put(getName().getBytes(StandardCharsets.UTF_8));
		output.put((byte)0);

	}

}

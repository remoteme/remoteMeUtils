package org.remoteme.utils.messages.v1.core.messages.variables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Getter
@EqualsAndHashCode
public class VariableIdentifier implements Serializable {


	String name;
	VariableType type;

	protected VariableIdentifier(){

	}

	public VariableIdentifier(String name, VariableType type){
		this.name=name;
		this.type=type;
	}


	@Override
	public String toString() {
		return "VI{" +
				"name='" + name + '\'' +
				", type=" + type +
				'}';
	}

	public VariableIdentifier(ByteBuffer output){
		type = VariableType.getById(Short.toUnsignedInt(output.getShort()));
		name =ByteBufferUtils.readString(output);

	}

	public  void serialize(ByteBuffer output){
		output.putShort((short) getType().getId());
		output.put(getName().getBytes(StandardCharsets.UTF_8));
		output.put((byte)0);

	}

}

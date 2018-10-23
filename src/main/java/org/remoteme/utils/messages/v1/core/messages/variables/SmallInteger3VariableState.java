package org.remoteme.utils.messages.v1.core.messages.variables;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger3VV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SmallInteger3VariableState extends AVariableState<SmallInteger3VV> {



	protected SmallInteger3VariableState() {
	}

	public SmallInteger3VariableState(String name, int v1,int v2,int v3) {
		this(name, new SmallInteger3VV(v1, v2, v3));
	}

	public SmallInteger3VariableState(String name, SmallInteger3VV data) {
		super(name, data);
	}

	public SmallInteger3VariableState(ByteBuffer output) {
		super(output);
	}



}

package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.VariableType;

import java.io.Serializable;
import java.util.List;


//Support only in websocet
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateVariablesMessage extends AARLiteMessage {

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class VariableDetails implements Serializable {
		String name;
		VariableType type;
		boolean persistent;
		boolean history;
	}

	List<VariableDetails> variables;

}

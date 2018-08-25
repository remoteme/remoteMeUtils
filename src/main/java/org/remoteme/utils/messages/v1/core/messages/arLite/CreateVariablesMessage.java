package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.VariableType;

import java.util.List;


//Support only in websocet
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateVariablesMessage extends AARLiteMessage {

	String name;
	VariableType type;
	boolean persistent;
	boolean history;


}

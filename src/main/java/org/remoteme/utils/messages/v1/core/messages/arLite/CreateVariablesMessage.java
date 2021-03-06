package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.util.List;


//Support only in websocet
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateVariablesMessage extends AARLiteMessage {


	List<VariableDetails> variables;

}

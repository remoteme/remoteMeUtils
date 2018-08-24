package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;

import java.util.List;


//Support only in websocet
@Getter
@Setter
public class NotifyAboutVariablesMessage extends AARLiteMessage {

	List<VariableIdentifier> identifiers;


}

package org.remoteme.utils.com.arLite.rest.v1.dto.variables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class VariableIdentifierDto implements Serializable {
	String name;
	VariableType type;


}

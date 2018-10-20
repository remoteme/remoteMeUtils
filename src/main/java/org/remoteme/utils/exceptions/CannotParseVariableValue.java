package org.remoteme.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

@AllArgsConstructor
@Getter
public class CannotParseVariableValue extends Exception {
	String rendered;
	VariableType type;

}

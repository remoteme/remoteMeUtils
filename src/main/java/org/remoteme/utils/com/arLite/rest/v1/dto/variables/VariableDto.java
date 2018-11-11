package org.remoteme.utils.com.arLite.rest.v1.dto.variables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

@Getter
@Setter
@NoArgsConstructor

public class VariableDto extends VariableIdentifier {


	String name;
	VariableType type;
	boolean persistent;
	boolean scheduled;
	boolean history;

	public VariableDto(String name,	VariableType type,boolean persistent,	boolean scheduled,	boolean history) {
		this.name=name;
		this.type=type;
		this.persistent=persistent;
		this.scheduled=scheduled;
		this.history=history;
	}

}

package org.remoteme.utils.messages.v1.core.messages.arLite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VariableDetails implements Serializable {
	String name;
	VariableType type;
	boolean persistent;
	boolean history;
	boolean scheduled;
}

package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;


@Getter
@Setter
public class VariableRemoveMessage extends AARLiteMessage {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class VariableRemoveMessageData implements Serializable {
		String name;
		VariableType type;
	}
	int deviceId;

	VariableRemoveMessageData data;





	public VariableRemoveMessage(int deviceId, String name, VariableType type) {
		this.deviceId=deviceId;
		this.data=new VariableRemoveMessageData(name, type);


	}


	protected VariableRemoveMessage() {
	}










}

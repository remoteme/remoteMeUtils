package org.remoteme.utils.messages.v1.core.messages.arLite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;

@Getter
@Setter
public class VariableRenameMessage extends AARLiteMessage {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class VariableRenameMessageData implements Serializable {
		String oldName;
		String newName;
		VariableType type;
	}


	int deviceId;
	VariableRenameMessageData data;


	public VariableRenameMessage(int deviceId,	String oldName,	String newName,	VariableType type){
		this.deviceId=deviceId;
		this.data=new VariableRenameMessageData(oldName, newName, type);

	}


	protected VariableRenameMessage() {
	}








	

}
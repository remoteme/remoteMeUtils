package org.remoteme.utils.messages.v1.core.messages.arLite;

import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.VariableType;

@Getter
@Setter
public class VariableRenameMessage extends AARLiteMessage {


	int deviceId;
	String oldName;
	String newName;
	VariableType type;


	public VariableRenameMessage(int deviceId,	String oldName,	String newName,	VariableType type){
		this.deviceId=deviceId;
		this.oldName=oldName;
		this.newName=newName;
		this.type=type;

	}


	protected VariableRenameMessage() {
	}








	

}
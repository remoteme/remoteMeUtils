package org.remoteme.utils.messages.v1.enums.variables;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

public enum VariableMode  implements Id_Enum<VariableRemoteMode> {
	LOCAL(0), REMOTE(1);

	private final int id;


	VariableMode(int id) {
		this.id = id;


	}



	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<VariableRemoteMode> array = new SparseArrayIdEnum(VariableMode.class);


	public static VariableRemoteMode getById(int id) {
		return Id_Enum.getListInner(id, array);
	}

}
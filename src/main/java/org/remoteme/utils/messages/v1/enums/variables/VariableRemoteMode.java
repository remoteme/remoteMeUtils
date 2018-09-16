package org.remoteme.utils.messages.v1.enums.variables;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

public enum VariableRemoteMode implements Id_Enum<VariableRemoteMode> {
	YOUTUBE_VIEWS_COUNT(0), YOUTUBE_SUBSCRIBERS_COUNT(1),

	WEATHER_NOW(101);

	private final int id;


	VariableRemoteMode(int id) {
		this.id = id;


	}



	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<VariableRemoteMode> array = new SparseArrayIdEnum(VariableRemoteMode.class);


	public static VariableRemoteMode getById(int id) {
		return Id_Enum.getListInner(id, array);
	}

}
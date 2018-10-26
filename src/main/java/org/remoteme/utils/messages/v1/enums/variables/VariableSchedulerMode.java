package org.remoteme.utils.messages.v1.enums.variables;

import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

public enum VariableSchedulerMode implements Id_Enum<VariableSchedulerMode> {
	CRON(0), TIME(1);
	private final int id;

	VariableSchedulerMode(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<VariableSchedulerMode> array = new SparseArrayIdEnum(VariableSchedulerMode.class);

	public static VariableSchedulerMode getById(int id) {
		return Id_Enum.getListInner(id, array);
	}
}

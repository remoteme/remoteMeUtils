package org.remoteme.utils.messages.v1.enums.variables;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

public enum VariableType implements Id_Enum<VariableType> {
	BOOLEAN(0, "Boolean", "B"), INTEGER(1, "Integer", "I"), TEXT(2, "Text", "T"),
	SMALL_INTEGER_3(3, "SmallInteger3", "SI3"), SMALL_INTEGER_2(4, "SmallInteger2", "SI2"),
	INTEGER_BOOLEAN(5, "IntegerBoolean", "IB"), DOUBLE(6, "Double", "D"), TEXT_2(7, "Text2", "T2")
	, SMALL_INTEGER_2_TEXT_2(8, "SmallInteger2Text2", "T2");


	private final int id;
	private final String niceName;
	private final String niceShortName;

	VariableType(int id, String niceName, String niceShortName) {
		this.id = id;
		this.niceName = niceName;
		this.niceShortName = niceShortName;
	}

	public String getNiceName() {
		return niceName;
	}

	public String getNiceShortName() {
		return niceShortName;
	}

	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<VariableType> array = new SparseArrayIdEnum(VariableType.class);


	public static VariableType getById(int id) {
		return Id_Enum.getListInner(id, array);
	}

	public boolean isSingle() {
		return in(BOOLEAN,INTEGER,DOUBLE);
	}
}
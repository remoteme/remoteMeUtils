package org.remoteme.utils.messages.v1.enums.variables;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

public enum VariableRemoteMode implements Id_Enum<VariableRemoteMode> {
	YOUTUBE_VIEWS_COUNT(0), YOUTUBE_SUBSCRIBERS_COUNT(1),

	WEATHER_NOW(101),WEATHER_FORECAST_TODAY_MORNING(102), WEATHER_FORECAST_TODAY_NOON(103), WEATHER_FORECAST_TODAY_EVENING(104),
	WEATHER_FORECAST_TOMORROW_MORNING(105), WEATHER_FORECAST_TOMORROW_NOON(106), WEATHER_FORECAST_TOMORROW_EVENING(107),
	WEATHER_FORECAST_IN_2_DAYS_MORNING(108), WEATHER_FORECAST_IN_2_DAYS_NOON(109), WEATHER_FORECAST_IN_2_DAYS_EVENING(110),
	WEATHER_FORECAST_IN_3_DAYS_MORNING(122), WEATHER_FORECAST_IN_3_DAYS_NOON(122), WEATHER_FORECAST_IN_3_DAYS_EVENING(113),
	WEATHER_FORECAST_IN_4_DAYS_MORNING(114), WEATHER_FORECAST_IN_4_DAYS_NOON(115), WEATHER_FORECAST_IN_4_DAYS_EVENING(116);

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
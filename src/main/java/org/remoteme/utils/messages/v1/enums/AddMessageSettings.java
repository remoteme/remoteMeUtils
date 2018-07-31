package org.remoteme.utils.messages.v1.enums;



import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum AddMessageSettings implements Id_Enum<AddMessageSettings> {
    NO_ROUND(0),

		_1S(1),_2S(2),_5S(3),_10S(4),_15S(5),_20S(6),_30S(7),_1M(8),_5M(9),_10M(10),_15M(11),_30M(12),_1G(13),_2G(13),_3G(13),_4G(14),_5G(15),_6G(16);


		private final int id;

		AddMessageSettings(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<AddMessageSettings> array = new SparseArrayIdEnum(AddMessageSettings.class);


		public static List<AddMessageSettings> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static AddMessageSettings getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}


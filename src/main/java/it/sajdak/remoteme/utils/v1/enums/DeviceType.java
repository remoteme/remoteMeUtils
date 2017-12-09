package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum DeviceType implements Id_Enum<DeviceType> {

		NETWORK(1),SMARTPHONE(2),WEBPAGE(3), JSSCRIPT(4);

		private final int id;


		DeviceType(int id ) {
			this.id = id;

		}
		public int getId() {
			return id;
		}


		static SparseArrayIdEnum<DeviceType> array = new SparseArrayIdEnum(DeviceType.class);

		public static List<DeviceType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static DeviceType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}


	}
package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum NetworkDeviceType implements Id_Enum<NetworkDeviceType> {

	OTHER(0),RASPBERRY_PI(1),ARDUINO(2);

		private final int id;


		NetworkDeviceType(int id ) {
			this.id = id;

		}
		public int getId() {
			return id;
		}


		static SparseArrayIdEnum<NetworkDeviceType> array = new SparseArrayIdEnum(NetworkDeviceType.class);

		public static List<NetworkDeviceType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static NetworkDeviceType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}


	}
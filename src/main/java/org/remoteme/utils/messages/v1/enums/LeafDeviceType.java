package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum LeafDeviceType implements Id_Enum<LeafDeviceType> {

	OTHER_SOCKET(1),EXTERNAL_SCRIPT(2);

		private final int id;


		LeafDeviceType(int id ) {
			this.id = id;

		}
		public int getId() {
			return id;
		}


		public boolean isConnectable(){
			return this==OTHER_SOCKET || this==EXTERNAL_SCRIPT;
		}

		static SparseArrayIdEnum<LeafDeviceType> array = new SparseArrayIdEnum(LeafDeviceType.class);

		public static List<LeafDeviceType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static LeafDeviceType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}


	}
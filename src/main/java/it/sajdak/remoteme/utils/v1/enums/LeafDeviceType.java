package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum LeafDeviceType implements Id_Enum<LeafDeviceType> {

		OTHER(1),EXTERNAL_SCRIPT(2),SERIAL(3),NRF24(4),WEB_SOCKET(5);

		private final int id;


		LeafDeviceType(int id ) {
			this.id = id;

		}
		public int getId() {
			return id;
		}


		static SparseArrayIdEnum<LeafDeviceType> array = new SparseArrayIdEnum(LeafDeviceType.class);

		public static List<LeafDeviceType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static LeafDeviceType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}


	}
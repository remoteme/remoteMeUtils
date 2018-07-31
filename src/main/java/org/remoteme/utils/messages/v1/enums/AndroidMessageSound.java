package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum AndroidMessageSound  implements Id_Enum<AndroidMessageSound> {

		DEFAULT_SOUND(1,"default");

		private final int id;
    private final String name;

		AndroidMessageSound(int id,String name) {
			this.id = id;
			this.name=name;
		}
		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		static SparseArrayIdEnum<AndroidMessageSound> array = new SparseArrayIdEnum(AndroidMessageSound.class);


		public static List<AndroidMessageSound> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static AndroidMessageSound getById(int id) {
			if (id==-1){
				return DEFAULT_SOUND;
			}else {
				return Id_Enum.getListInner(id, array);
			}
		}
	}
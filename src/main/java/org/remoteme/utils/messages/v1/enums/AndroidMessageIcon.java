package org.remoteme.utils.messages.v1.enums;



import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum AndroidMessageIcon  implements Id_Enum<AndroidMessageIcon> {

		DEFAULT_ICON(1,"ic_remoteme"),PERSON_ICON(2,"ic_walk"),THIEF_ICON(3,"ic_thief"),WINDOW_OPEN_ICON(4,"ic_windowopen"),BUNNY_ICON(5,"ic_bunny");

		private final int id;
    private final String name;

		AndroidMessageIcon(int id,String name) {
			this.id = id;
			this.name=name;
		}
		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		static SparseArrayIdEnum<AndroidMessageIcon> array = new SparseArrayIdEnum(AndroidMessageIcon.class);


		public static List<AndroidMessageIcon> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static AndroidMessageIcon getById(int id) {
			if (id==-1){
				return DEFAULT_ICON;
			}else {
				return Id_Enum.getListInner(id, array);
			}

		}


	}
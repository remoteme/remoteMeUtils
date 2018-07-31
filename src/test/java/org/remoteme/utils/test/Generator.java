package org.remoteme.utils.test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.reflections.Reflections;
import org.remoteme.utils.messages.v1.core.messages.AMessage;
import org.remoteme.utils.messages.v1.core.messages.change.ChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;

import java.lang.reflect.Modifier;
import java.util.Set;

public class Generator {



	public static void main(String[] args) {
		Reflections r = new Reflections("org.remoteme");
		Set<Class<? extends AMessage>> subTypesOf = r.getSubTypesOf(AMessage.class);
		for (Class<? extends AMessage> aClass : subTypesOf) {
			if (!Modifier.isAbstract( aClass.getModifiers() )) {

				String str = "@JsonSubTypes.Type(value = " + aClass.getSimpleName() + ".class, name = \"" + aClass.getSimpleName() + "\"),";
				System.out.println(str);
			}
		}

	}
}

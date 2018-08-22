package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.observers.ObserverIdentifier;

import java.util.List;


//Support only in websocet
@Getter
@Setter
public class NotifyAboutObserversMessage extends AARLiteMessage {

	List<ObserverIdentifier> identifiers;


}

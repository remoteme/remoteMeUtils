package org.remoteme.utils.messages.v1.core.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.remoteme.utils.messages.v1.core.messages.arLite.PingMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRegisterMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRemoveMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ObserverRenameMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.AddDataMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.LogMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterLeafDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncResponseMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncUserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SystemMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.WebRRCConnectionStatusChangeMessage;

import java.io.Serializable;

@Getter
@Setter
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = UserMessage.class, name = "UserMessage"),
		@JsonSubTypes.Type(value = AddDataMessage.class, name = "AddDataMessage"),
		@JsonSubTypes.Type(value = SyncUserMessage.class, name = "SyncUserMessage"),
		@JsonSubTypes.Type(value = SyncMessage.class, name = "SyncMessage"),
		@JsonSubTypes.Type(value = SyncResponseMessage.class, name = "SyncResponseMessage"),
		@JsonSubTypes.Type(value = RegisterDeviceMessage.class, name = "RegisterDeviceMessage"),
		@JsonSubTypes.Type(value = SystemMessage.class, name = "SystemMessage"),
		@JsonSubTypes.Type(value = PingMessage.class, name = "PingMessage"),
		@JsonSubTypes.Type(value = LogMessage.class, name = "LogMessage"),
		@JsonSubTypes.Type(value = RegisterLeafDeviceMessage.class, name = "RegisterLeafDeviceMessage"),
		@JsonSubTypes.Type(value = WebRRCConnectionStatusChangeMessage.class, name = "WebRRCConnectionStatusChangeMessage"),
		@JsonSubTypes.Type(value = ObserverChangeMessage.class, name = "ObserverChangeMessage"),
		@JsonSubTypes.Type(value = ObserverRegisterMessage.class, name = "ObserverRegisterMessage"),
		@JsonSubTypes.Type(value = ObserverChangePropagateMessage.class, name = "ObserverChangePropagateMessage"),
		@JsonSubTypes.Type(value = ObserverRenameMessage.class, name = "ObserverRenameMessage"),
		@JsonSubTypes.Type(value = ObserverRemoveMessage.class, name = "ObserverRemoveMessage"),

})
@ApiModel(value="SuperModel", discriminator = "foo")
@Slf4j
public abstract class AMessage implements Serializable {


}

package org.remoteme.utils.messages.v1.core.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.remoteme.utils.messages.v1.core.messages.arLite.NotifyAboutVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.CreateVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRenameMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.DeviceConnectionChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.PingMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableObserveMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRemoveMessage;

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
		@JsonSubTypes.Type(value = VariableChangeMessage.class, name = "VariableChangeMessage"),
		@JsonSubTypes.Type(value = VariableObserveMessage.class, name = "VariableObserveMessage"),
		@JsonSubTypes.Type(value = VariableChangePropagateMessage.class, name = "VariableChangePropagateMessage"),


		@JsonSubTypes.Type(value = VariableRenameMessage.class, name = "VariableRenameMessage"),
		@JsonSubTypes.Type(value = VariableRemoveMessage.class, name = "VariableRemoveMessage"),
		@JsonSubTypes.Type(value = NotifyAboutVariablesMessage.class, name = "NotifyAboutVariablesMessage"),
		@JsonSubTypes.Type(value = CreateVariablesMessage.class, name = "CreateVariablesMessage"),
		@JsonSubTypes.Type(value = DeviceConnectionChangeMessage.class, name = "DeviceConnectionChangeMessage"),
})
public abstract class AMessage implements Serializable {


}

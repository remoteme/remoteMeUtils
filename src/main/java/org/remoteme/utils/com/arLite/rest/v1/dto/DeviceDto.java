package org.remoteme.utils.com.arLite.rest.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeviceDto{
	boolean active;
	String name;
	DeviceTypeDto type;
	int deviceId;
	DeviceConnectionStateDto connected;
	List<DeviceDto> childs;
}

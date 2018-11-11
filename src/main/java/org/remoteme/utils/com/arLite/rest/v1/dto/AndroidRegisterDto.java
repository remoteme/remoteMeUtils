package org.remoteme.utils.com.arLite.rest.v1.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AndroidRegisterDto implements Serializable {
	@ApiModelProperty(notes = "Unique deviceId")
	private String smartPhoneId;
	@ApiModelProperty(notes = "Nice name will be display in device manager")
	private String niceName;


}

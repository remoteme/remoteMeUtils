package org.remoteme.utils.com.arLite.rest.v1.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AndroidRegisterDto implements Serializable {
	private String smartPhoneId;
	private String niceName;


}

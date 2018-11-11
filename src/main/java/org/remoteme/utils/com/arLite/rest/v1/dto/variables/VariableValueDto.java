package org.remoteme.utils.com.arLite.rest.v1.dto.variables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariableValueDto implements Serializable {

	String value;
	Date lastSetTime;

}

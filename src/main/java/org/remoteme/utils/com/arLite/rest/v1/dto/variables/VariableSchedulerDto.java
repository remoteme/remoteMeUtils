package org.remoteme.utils.com.arLite.rest.v1.dto.variables;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableSchedulerMode;

import java.util.List;

@Getter
@Setter
public class VariableSchedulerDto {


	private int variableSchedulerId;
	@ApiModelProperty(notes = "M H D Y check user interface to more help")
	private String cron;
	private String time;
	private VariableSchedulerMode mode;
	@ApiModelProperty(notes = "Its null if variable is non active")
	private long nextTimeRun;
	private List<String> values;



}

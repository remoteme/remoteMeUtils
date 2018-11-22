package org.remoteme.utils.com.arLite.rest.v1.dto.variables;


import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableSchedulerMode;

import java.util.List;

@Getter
@Setter
public class VariableSchedulerDto {


	private int variableSchedulerId;
	private String cron;
	private String time;
	private VariableSchedulerMode mode;
	private long nextTimeRun;
	private List<String> values;



}

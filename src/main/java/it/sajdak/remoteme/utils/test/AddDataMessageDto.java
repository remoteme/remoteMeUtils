package it.sajdak.remoteme.utils.test;

import io.swagger.annotations.ApiModelProperty;
import it.sajdak.remoteme.utils.v1.enums.AddMessageSettings;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.struct.AddDataMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AddDataMessageDto extends ARemoteMeMessageDto {


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DataSeriesDto implements Serializable {
		int seriesId;
		double value;

	}


	@ApiModelProperty(notes = "Time in miliseconds since 01.01.1970 put 0 if You want to create data for now", required = true)

	long time;
	@ApiModelProperty(notes = "Settings for now jsut time roundness", required = true)

	AddMessageSettings settings;
	List<DataSeriesDto> dataSeries;


	@Override
	public ARemoteMeMessage getRemoteMeMessage() {
		List<AddDataMessage.DataSeries> dataSeriesTemp = dataSeries.stream().map(x -> new AddDataMessage.DataSeries(x.getSeriesId(), x.getValue())).collect(Collectors.toList());

		return new AddDataMessage(this.time, AddMessageSettings.getById(this.settings.getId()), dataSeriesTemp);
	}


}

package org.remoteme.utils.messages.v1.core.messages.remoteMe;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.AddMessageSettings;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@EqualsAndHashCode
public class AddDataMessage extends ARemoteMeMessage {

	private static final int DATA_SERIES_SIZE=10;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DataSeries implements Serializable{
		int seriesId;
		double value;

	}



	long time;//2

	AddMessageSettings settings;//1
	List<DataSeries> dataSeries;



	protected AddDataMessage() {

	}

	public AddDataMessage(long time, int seriesId, double value) {
		this(time, AddMessageSettings.NO_ROUND,seriesId, value);
	}

	public AddDataMessage(long time,AddMessageSettings settings, int seriesId, double value) {
		this(time,settings,Collections.singletonList(new  DataSeries(seriesId, value)));

	}

	public AddDataMessage(long time,AddMessageSettings settings, List<DataSeries> dataSeries) {
		this.settings = settings;
		this.dataSeries= new ArrayList<>(dataSeries.size());
		this.dataSeries.addAll(dataSeries);
		this.time = time;

	}

	public AddDataMessage(ByteBuffer payload) {
		int size = payload.getShort();//taking size



		time= payload.getLong();

		settings = AddMessageSettings.getById(Byte.toUnsignedInt(payload.get()));


		size-=8+1;//round and time

		size/=DATA_SERIES_SIZE;//seriesId and value

		dataSeries = new ArrayList<>(size);
		for(int i=0;i<size;i++){
			int dataSeriesId = payload.getShort();
			double value =BigDecimal.valueOf( payload.getDouble()).round(new MathContext(12)).doubleValue();;
			dataSeries.add(new DataSeries(dataSeriesId,value));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {



		int size=8+1+ DATA_SERIES_SIZE *dataSeries.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);
		byteBuffer.putLong(time);
		byteBuffer.put((byte)settings.getId());

		for (DataSeries ds : dataSeries) {
			byteBuffer.putShort((short)ds.getSeriesId());
			byteBuffer.putDouble(ds.getValue());
		}




		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.ADD_DATA;
	}

}

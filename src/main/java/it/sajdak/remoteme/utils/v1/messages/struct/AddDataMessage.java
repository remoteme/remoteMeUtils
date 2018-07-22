package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.AddMessageSettings;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.general.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
		this.settings = settings;
		dataSeries= new ArrayList<>(1);
		dataSeries.add(new DataSeries(seriesId, value));
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

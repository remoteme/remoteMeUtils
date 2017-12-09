package it.sajdak.remoteme.utils.v1.messages;


import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferUtils {

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static List<Integer> hexStringToListInteger(String s) {
		byte[] b=hexStringToByteArray(s);
		List<Integer> ret = new ArrayList<>(b.length);
		for (byte b1 : b) {
			ret.add(Integer.valueOf(b1));
		}
		return ret;
	}



	public static String readString(ByteBuffer is)  {

		ByteArrayOutputStream bb =new ByteArrayOutputStream(100);
		byte read1;
		while((read1 = is.get())!=0){
			bb.write(read1);
		}

		try {
			return new String(bb.toByteArray(), StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	public static byte[] writeString(String str)  {
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		writeString(ret,str);

		return ret.toByteArray();

	}

	public static String readColor(ByteBuffer is) throws IOException {
		byte[] buff = new byte[3];
		is.get(buff);
		return "#"+ DatatypeConverter.printHexBinary(buff).toUpperCase();

	}

	public static void writeString(ByteArrayOutputStream bb, String title) {
		try {
			bb.write(title.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		bb.write(0);
	}

	public static byte[] readRest(ByteBuffer payload) {
		byte[] ret = new byte[payload.remaining()];
		payload.get(ret);
		return ret;
	}




}

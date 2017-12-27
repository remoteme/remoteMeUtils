package it.sajdak.remoteme.utils.v1.messages;


import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import it.sajdak.remoteme.utils.general.Pair;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class ByteBufferUtils {
	public static final int maxUncompressedSize = 256 * 230;

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

	private static void shovelInToOut(InputStream in, OutputStream out)
			throws IOException
	{
		byte[] buffer = new byte[1000];
		int len;
		while((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
	}
	public static byte[] compress(byte[] data) throws IOException  {


		InputStream in = new ByteInputStream(data,data.length);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		OutputStream out =new DeflaterOutputStream(os);
		shovelInToOut(in, out);
		in.close();
		out.close();

		return os.toByteArray();

	}

	 public static byte[] decompress(byte[] data) throws IOException, DataFormatException {

		Inflater inflater = new Inflater();

		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		byte[] buffer = new byte[data.length*100];

		while (!inflater.finished()) {

			int count = inflater.inflate(buffer);

			outputStream.write(buffer, 0, count);

		}
		outputStream.close();

		byte[] output = outputStream.toByteArray();

		return output;

	}




	public static List<Pair<Integer,byte[]>> splitAndCompress(String content ){

		return splitAndCompress(content, maxUncompressedSize);
	}

	public static List<Pair<Integer,byte[]>> splitAndCompress(String content,int maxUncompressedSize){
		List<Pair<Integer,byte[]>> ret = new ArrayList<>();
		byte[] bytesToSave = content.getBytes(StandardCharsets.UTF_8);


		for(int index = 0; index*maxUncompressedSize<=bytesToSave.length; index++){
			int uncompressedSize=Math.min(maxUncompressedSize,bytesToSave.length-maxUncompressedSize*index);

			byte[] subArray= Arrays.copyOfRange(bytesToSave,index*maxUncompressedSize,index*maxUncompressedSize+uncompressedSize);
			try {

				ret.add(new Pair<>(uncompressedSize,compress(subArray)));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return ret;
	}

}

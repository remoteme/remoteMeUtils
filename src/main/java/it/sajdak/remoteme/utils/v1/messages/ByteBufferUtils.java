package it.sajdak.remoteme.utils.v1.messages;


import it.sajdak.remoteme.utils.general.Pair;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
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

	public static String byteToHex(byte[] buff) {
		return DatatypeConverter.printHexBinary(buff).toUpperCase();
	}

	public static List<Integer> hexStringToListInteger(String s) {
		byte[] b = hexStringToByteArray(s);
		List<Integer> ret = new ArrayList<>(b.length);
		for (byte b1 : b) {
			ret.add(Integer.valueOf(b1));
		}
		return ret;
	}


	public static byte[] readByteArray(ByteBuffer bb){
		byte[] arr = new byte[bb.remaining()];
		bb.get(arr);
		return arr;
	}
	public static String readString(ByteBuffer is) {

		ByteArrayOutputStream bb = new ByteArrayOutputStream(100);
		byte read1;
		while ((read1 = is.get()) != 0) {
			bb.write(read1);
		}

		try {
			return new String(bb.toByteArray(), StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	public static byte[] writeString(String str) {
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		writeString(ret, str);

		return ret.toByteArray();

	}

	public static String readColor(ByteBuffer is) throws IOException {
		byte[] buff = new byte[3];
		is.get(buff);
		return "#" + DatatypeConverter.printHexBinary(buff).toUpperCase();

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

	/*public static byte[] compress(byte[] uncompressedData) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);
		CompressorOutputStream gzipOS = new BlockLZ4CompressorOutputStream(bos);
		gzipOS.write(uncompressedData);
		gzipOS.close();
		return bos.toByteArray();


	}

	public static byte[] decompress(byte[] compressedData) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CompressorInputStream gzipIS = new BlockLZ4CompressorInputStream(bis);

		byte[] buffer = new byte[1024];
		int len;
		while ((len = gzipIS.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		return bos.toByteArray();


	}*/

	public static byte[] compress(byte[] data) throws IOException {
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated code... index
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();

		return output;
	}
	public static byte[] decompress(byte[] data) throws IOException {
		try {

			Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = 0;
				count = inflater.inflate(buffer);

			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();

		return output;
		} catch (DataFormatException e) {
			throw new IOException();
		}
	}

	public static List<Pair<Integer, byte[]>> splitAndCompress(String content) {

		return splitAndCompress(content, maxUncompressedSize);
	}

	public static List<Pair<Integer, byte[]>> splitAndCompress(byte[] content) {
		return splitAndCompress(content, maxUncompressedSize);
	}

	public static List<Pair<Integer, byte[]>> splitAndCompress(byte[] bytesToSave, int maxUncompressedSize) {
		List<Pair<Integer, byte[]>> ret = new ArrayList<>();



		for (int index = 0; index * maxUncompressedSize <= bytesToSave.length; index++) {
			int uncompressedSize = Math.min(maxUncompressedSize, bytesToSave.length - maxUncompressedSize * index);

			byte[] subArray = Arrays.copyOfRange(bytesToSave, index * maxUncompressedSize, index * maxUncompressedSize + uncompressedSize);
			try {

				ret.add(new Pair<>(uncompressedSize, compress(subArray)));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return ret;
	}
	public static List<Pair<Integer, byte[]>> splitAndCompress(String content, int maxUncompressedSize) {
		byte[] bytesToSave = content.getBytes(StandardCharsets.UTF_8);
		return splitAndCompress(bytesToSave, maxUncompressedSize);
	}

	public static byte[] getByteArray(List<Integer> data) {
		byte[] ret = new byte[data.size()];

		for (int i = 0; i < data.size(); i++) {
			ret[i] = data.get(i).byteValue();
		}

		return ret;
	}

	public static List<Integer> toIntList(byte[] bytes) {
		List<Integer> ret = new ArrayList<>(bytes.length);
		for (byte aByte : bytes) {
			ret.add(Byte.toUnsignedInt(aByte));
		}
		return ret;
	}
}

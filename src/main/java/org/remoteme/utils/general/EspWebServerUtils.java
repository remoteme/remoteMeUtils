package org.remoteme.utils.general;


import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.utils.ByteUtils;
import org.remoteme.utils.exceptions.HashEncryptException;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class EspWebServerUtils {
	private static final String beginPostfix=" START";
	private static final String endPostfix=" END";
	public static final String commentStart="/* ";
	private static final String commentEnd=" */";

	public static byte[] getContent(String fileName,byte[] content){

		List<String> strings = ByteBufferUtils.readLines(content);
		List<String> ret = new ArrayList<>();
		String start = getBegin(fileName);
		String end = getEnd(fileName);

		boolean contentBlock=false;
		for (String line : strings) {
			if (contentBlock){
				if (line.equals(end)){
					break;
				}
				ret.add(line);
			}else{
				if (line.equals(start)){
					contentBlock=true;
				}
			}
		}
		return ByteBufferUtils.writeLines(ret);

	}

	public static String getBegin(String fileName){
		return commentStart+fileName+beginPostfix+commentEnd;
	}
	public static String getEnd(String fileName){
		return commentStart+fileName+endPostfix+commentEnd;
	}

	public static byte[] removeContent(String fileName,byte[] content){
		List<String> strings = ByteBufferUtils.readLines(content);
		List<String> ret = new ArrayList<>();
		String start = getBegin(fileName);
		String end = getEnd(fileName);

		boolean contentBlock=false;
		for (String line : strings) {
			if (!contentBlock){
				if (line.equals(start)){
					contentBlock=true;
				}else{
					ret.add(line);
				}
			}else{
				if (line.equals(end)){
					contentBlock=false;
				}
			}
		}
		return ByteBufferUtils.writeLines(ret);
	}
	public static byte[] setContent(String fileName,byte[] content,byte[] toAdd){
		if (toAdd==null || toAdd.length==0){
			return removeContent(fileName, content);
		}
		List<String> strings = ByteBufferUtils.readLines(content);
		List<String> ret = new ArrayList<>();
		String start = getBegin(fileName);
		String end = getEnd(fileName);

		boolean added=false;
		boolean contentBlock=false;
		for (String line : strings) {
			if (line.equals(end)){
				contentBlock=false;
			}

			if (!contentBlock){
				ret.add(line);
			}

			if (line.equals(start)){
				contentBlock=true;
				added=true;
				for (String readLine : ByteBufferUtils.readLines(toAdd)) {
					ret.add(readLine);
				}
			}

		}
		if (!added){
			ret.add(start);
			for (String readLine : ByteBufferUtils.readLines(toAdd)) {
				ret.add(readLine);
			}
			ret.add(end);
		}

		return ByteBufferUtils.writeLines(ret);
	}

	public static byte[] renamePackage(String oldPackageName,String newPackageName,byte[] content ) {
		List<String> toReturn = new ArrayList<>();

		for (String line : ByteBufferUtils.readLines(content)) {
			if (line.startsWith(EspWebServerUtils.commentStart + oldPackageName + "/") &&
					(line.endsWith(EspWebServerUtils.beginPostfix+commentEnd) || line.endsWith(EspWebServerUtils.endPostfix+commentEnd) )) {
				line = line.replaceFirst(oldPackageName + "/", newPackageName + "/");
			}
			toReturn.add(line);
		}

		return ByteBufferUtils.writeLines(toReturn);
	}

	public static byte[] renameFile(byte[] content, String oldFileName, String newFileName) {
		List<String> toReturn = new ArrayList<>();
		String begin = getBegin(oldFileName);
		String end = getEnd(oldFileName);
		for (String line : ByteBufferUtils.readLines(content)) {
			if (line.equals(begin)) {
				line =  getBegin(newFileName);
			}
			if (line.equals(end)) {
				line =  getEnd(newFileName);
			}
			toReturn.add(line);
		}

		return ByteBufferUtils.writeLines(toReturn);

	}
}

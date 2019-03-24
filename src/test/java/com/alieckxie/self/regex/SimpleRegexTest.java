package com.alieckxie.self.regex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class SimpleRegexTest {
	
	public static final String REGEX_SAMPLE = 
			"Test:windows3.1\n" +
			"Test:windows95\n" +
			"Test:windows98\n" +
			"Test:windows2000\n" +
			"Test:windowsMe\n" +
			"Test:windowsXp\n" +
			"Test:windowsVista\n" +
			"Test:windows7\n" +
			"Test:windows8\n" +
			"Test:windows8.1\n" +
			"Test:windows10\n" +
			"Test:3.1windows\n" +
			"Test:95windows\n" +
			"Test:98windows\n" +
			"Test:2000windows\n" +
			"Test:Mewindows\n" +
			"Test:Xpwindows\n" +
			"Test:Vistawindows\n" +
			"Test:7windows\n" +
			"Test:8windows\n" +
			"Test:8.1windows\n" +
			"Test:10windows\n";

	@Test
	public void testSimpleRegex() {
		Pattern compile = Pattern.compile(".{5,10}$", Pattern.MULTILINE);
		Matcher matcher = compile.matcher(REGEX_SAMPLE);
		matcher.find();
		String group = matcher.group();
		System.out.println(group);
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
//		matcher.find();
		group = matcher.group();
		System.out.println(group);
//		matcher.find();
		group = matcher.group();
		System.out.println(group);
//		matcher.find();
		group = matcher.group();
		System.out.println(group);
	}
	
//	@Test
//	public void tempConvertFile() throws IOException {
//		File file = new File("/Users/alieckxie/Documents/misc-doc/观棋-万古仙穹.txt");
//		if (file.exists()) {
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
//			File outFile = new File("/Users/alieckxie/Documents/misc-doc/观棋-万古仙穹-utf_8.txt");
//			if (outFile.exists()) {
//				outFile.delete();
//			}
//			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
//			String line = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				bufferedWriter.write(line);
//				bufferedWriter.write("\n");
//			}
//			bufferedReader.close();
//			bufferedWriter.close();
//		}
//	}
}

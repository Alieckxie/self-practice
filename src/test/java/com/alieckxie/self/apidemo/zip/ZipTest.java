package com.alieckxie.self.apidemo.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class ZipTest {

	@Test
	public void testZipFiles() throws IOException{
		String path1 = "C:/Users/Alieckxie/Desktop/zipFile1.txt";
		String path2 = "C:/Users/Alieckxie/Desktop/zipFile2.txt";
		ZipEntry entry1 = new ZipEntry(path1);
		ZipEntry entry2 = new ZipEntry(path2);
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("C:/Users/Alieckxie/Desktop/zipFiles.zip"));
		BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(path1));
		BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(path2));
		zipOutputStream.putNextEntry(new ZipEntry("asd/zipFile1.txt"));
		int count, bufferLen = 65536;  
        byte data[] = new byte[bufferLen];
		while ((count = bis1.read(data, 0, bufferLen)) != -1) {
			zipOutputStream.write(data, 0, count);
		}
		zipOutputStream.closeEntry();
		zipOutputStream.putNextEntry(new ZipEntry("zxc/zipFile2.txt"));
		while ((count = bis2.read(data, 0, bufferLen)) != -1) {
			zipOutputStream.write(data, 0, count);
		}
		zipOutputStream.closeEntry();
		zipOutputStream.putNextEntry(new ZipEntry("Alieckxie/"));
		zipOutputStream.closeEntry();
		zipOutputStream.flush();
		bis1.close();
		bis2.close();
		zipOutputStream.close();
	}
	
	@Test
	public void testUnzipFiles() throws IOException{
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("C:/Users/Alieckxie/Desktop/zipFiles.zip"), Charset.forName("UTF-8"));
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		System.out.println(System.currentTimeMillis());
		int count, bufferLen = 65536;
		byte data[] = new byte[bufferLen];
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while ((count = zipInputStream.read(data, 0, bufferLen)) != -1) {
			System.out.println(System.currentTimeMillis());
//			System.out.println("--"+Arrays.toString(data));
			System.out.println(System.currentTimeMillis());
			byteArrayOutputStream.write(data, 0, count);
			System.out.println(System.currentTimeMillis());
		}
		String string = byteArrayOutputStream.toString("GBK");
		System.out.println(System.currentTimeMillis());
		byte[] extra = zipEntry.getExtra();
		System.out.println(System.currentTimeMillis());
		System.out.println(extra);
		System.out.println(string);
		String name = zipEntry.getName();
		System.out.println(name);
		System.out.println(System.currentTimeMillis());
		zipInputStream.close();
	}
	
}

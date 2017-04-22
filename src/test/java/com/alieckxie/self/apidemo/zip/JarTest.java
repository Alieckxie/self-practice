package com.alieckxie.self.apidemo.zip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.junit.Test;

public class JarTest {

	@Test
	public void testReadJar() throws IOException {
		JarFile jarFile = new JarFile("C:/Users/Alieckxie/Desktop/comparePom-0.1.0.0---A.jar");
		Enumeration<JarEntry> entries = jarFile.entries();
		List<byte[]> list = new ArrayList<byte[]>();
		List<JarEntry> entryList = new ArrayList<JarEntry>();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while (entries.hasMoreElements()) {
			int count, bufferLen = 65536;
			byte data[] = new byte[bufferLen];
			JarEntry jarEntry = entries.nextElement();
			if (jarEntry.getName() != null && !jarEntry.getName().matches(".*\\.class")) {
				continue;
			}
			entryList.add(jarEntry);
			InputStream inputStream = jarFile.getInputStream(jarEntry);
			while ((count = inputStream.read(data, 0, bufferLen)) != -1) {
				byteArrayOutputStream.write(data, 0, count);
			}
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.reset();
			list.add(byteArray);
		}
		
		
		JarOutputStream jarOutputStream = new JarOutputStream(new BufferedOutputStream(
				new FileOutputStream("C:/Users/Alieckxie/Desktop/comparePom-0.1.0.0---A.jar")));
		for (int i = 0; i < list.size(); i++) {
			int count, bufferLen = 65536;
			byte data[] = new byte[bufferLen];
			JarEntry jarEntry = entryList.get(i);
			byte[] b = list.get(i);
			jarOutputStream.putNextEntry(jarEntry);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
			while ((count = byteArrayInputStream.read(data, 0, bufferLen)) != -1) {
				jarOutputStream.write(data, 0, count);
			}
			jarOutputStream.closeEntry();
		}
		jarOutputStream.close();
		jarFile.close();
	}

	@Test
	public void testNative(){
		goOut();
	}
	
	private static native void goOut();
	
}

package com.alieckxie.self.functiondetail;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import org.junit.Test;

/**
 * 文件目录是典型的树形结构，遍历的方式应该同样地有深度优先遍历和广度优先遍历。
 * 只不过如果不是二叉树，那就只有先根遍历和后根遍历，也就是对应二叉树的先序遍历和中序遍历。
 * 显然可以先把目录树给构造成二叉树的“左孩子又兄弟”的形式。
 * 此外还可以使用队列来对目录来进行层次遍历。
 * @author alieckxie
 *
 */
public class FileSearchTest {

	String path = "/Users/alieckxie/Documents";
	Integer counts = 0;

	@Test
	public void testFileSearch() {
		LinkedList<File> listAsStack = new LinkedList<File>();
		File file = new File(path);
		listAsStack.push(file);
		File[] listFiles = file.listFiles();
		for (File listFile : listFiles) {
			if (listFile.isDirectory()) {
				listAsStack.push(listFile);
			}
		}
		System.out.println(listAsStack);
		int size = listAsStack.size();
		for (int i = 0; i < size; i++) {
			File popFile = listAsStack.pop();
			File[] commonFiles = popFile.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return !pathname.isDirectory();
				}
			});
			System.out.println("+++++++++++++++++++++");
			System.out.println(popFile);
			System.out.println("---------------------");
			for (File file2 : commonFiles) {
				System.out.println(file2);
			}
			System.out.println("+++++++++++++++++++++\n");
		}
	}

	@Test
	public void testListFileRecursion() {
		File file = new File(path);
		listFilesRecursion(file);
		System.out.println(counts);
	}

	/**
	 * 看起来这种递归是深度优先的遍历方式
	 * @param file
	 */
	private void listFilesRecursion(File file) {
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			counts++;
			if (file2.isDirectory()) {
				listFilesRecursion(file2);
			} else {
				System.out.println(file2);
			}

		}
	}
}

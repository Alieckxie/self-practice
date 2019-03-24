package com.alieckxie.self.algorithm.search;

import org.junit.Test;

public class Riddle15Test {

	@Test
	public void testLCSearchRiddle() throws CloneNotSupportedException {
		search();
		// r.test();
		return;
	}

	public class A implements Cloneable { // 棋盘的抽象类

		private int[][] a;// 棋盘数组
		private int x;// 空格所在x坐标
		private int y;// 空格所在y坐标

		A() {
			int[][] b = { // 棋盘
					{ 5, 1, 2, 4 }, { 0, 6, 3, 8 }, { 9, 10, 7, 11 }, { 13, 14, 15, 12 } };
			a = b;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (a[i][j] == 0) {
						x = i;
						y = j;
					}
				}
			}
		}

		public void show() {// 打印棋盘
			System.out.println("---------------------------------");
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (a[i][j] == 0)
						System.out.print("|" + " " + "\t");
					else
						System.out.print("|" + a[i][j] + "\t");
				}
				System.out.println("|");
				System.out.println("---------------------------------");
			}
		}

		public void set(int x, int y) {// 移动空格
			if (Math.abs(x) > 1 || Math.abs(y) > 1) {
				System.out.println("-1");
				return;
			}
			x = this.x + x;
			y = this.y + y;
			if (x < 0 || x >= 4 || y < 0 || y >= 4) {// 检测能否朝某个方面移动
				return;
			} else {// 移动
				a[this.x][this.y] = a[x][y];
				a[x][y] = 0;
				this.x = x;
				this.y = y;
			}
		}

		public Boolean test() {// 开局判断棋盘是否可解
			int x = (this.x + this.y) % 2;// 空格初始位置决定
			int count = 0;
			a[this.x][this.y] = 16;// 对空格需要做一定的处理Position[空格] = 16
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					// if(a[i][j] == 0)//排除空格这一异常点
					// continue L1;
					for (int k = j + 1; k < 4; k++) {// 处理本行未完元素
						if (a[i][k] < a[i][j])
							count++;
					}
					for (int k = i + 1; k < 4; k++) {// 处理接下来的几行
						for (int l = 0; l < 4; l++) {
							if (a[k][l] < a[i][j])
								count++;
						}
					}
				}
			}
			a[this.x][this.y] = 0;
			if ((count + x) % 2 == 1) {
				System.out.println("目标不可达！" + (count + x));
				return false;
			}
			// System.out.println("test");
			return true;
		}

		public int g() {// 估值函数
			int g = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (a[i][j] != i * 4 + j + 1)
						g++;
				}
			}
			return g - 1;
		}

		public Object clone() throws CloneNotSupportedException {// 数组需要深拷贝
			A a = (A) super.clone();
			// b.show();
			a.a = new int[4][4];
			for (int i = 0; i < 4; i++) {// 数组克隆在每一行都要操作
				a.a[i] = this.a[i].clone();// 注意此处要用到this指针
			}
			return a;
		}
	}

	public void test() throws CloneNotSupportedException {// 测试深度拷贝是否成功
		A a = new A();
		a.show();
		A b = (A) a.clone();
		b.show();
		a.set(1, 0);
		b.show();

	}

	public void search() throws CloneNotSupportedException {// 检索函数
		A a = new A();// 用于存放E节点(当前处理节点)
		if (!a.test()) {
			return;
		}
		A aa = new A();// 用于存放极优节点
		A aaa = new A();// 用与存放父节点
		aa = (A) a.clone();
		int f = 0;// c = f(x) + g(x);
		int g = 1000;
		int c = 0;
		int min = 10000;
		while (g != 0) {
			// System.out.println("aa[][]极优节点：g="+g );
			// aa.show();
			int[] row = { -1, 0, 0, 1 };
			int[] column = { 0, -1, 1, 0 };
			min = 1000;
			f++;
			aaa = (A) a.clone();
			// System.out.println("原棋盘：" );
			aaa.show();
			System.out.println();
			for (int k = 0; k < 4; k++) {//
				a.set(row[k], column[k]);
				g = a.g();
				c = f + g;
				// System.out.println("a[][]当前处理节点:");
				// a.show();
				if (c < min) {
					min = c;
					aa = (A) a.clone();
				}

				a = (A) aaa.clone();
				// System.out.println("a[][]原始棋盘:");
				// aaa.show();
				// a.show();
			}
			a = (A) aa.clone();
		}
		aa.show();
		System.out.println("ok");
	}

}

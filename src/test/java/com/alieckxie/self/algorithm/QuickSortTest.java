package com.alieckxie.self.algorithm;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testQuickSort() {
		int[] array = generateRandomArray();
		System.out.println(Arrays.toString(array));
		Node quickSort = quickSort(array);
		System.out.println(quickSort);
	}

	@Test
	public void testQuickSortException() {
		int[] array = new int[] { 65, 34, 18, 68, 74, 59, 76, 36, 35, 27, 78, 17, 66, 35, 32, 39, 77, 36, 60, 99, 32,
				16, 66, 18, 71, 78, 2, 10, 86, 91, 87, 96, 94, 81, 3, 99, 80, 11, 53, 61, 25, 42, 30, 83, 61, 89, 18, 4,
				49, 67, 27, 50, 57, 51, 64, 77, 10, 90, 31, 69, 14, 49, 51, 15, 62, 70, 41, 97, 89, 56, 55, 43, 15, 8,
				22, 57, 27, 99, 5, 30, 87, 30, 54, 75, 43, 81, 7, 18, 73, 3, 89 };
		System.out.println(Arrays.toString(array));
		Node quickSort = quickSort(array);
		System.out.println(quickSort);
	}

	public Node quickSort(int[] array) {
		Node topNode = new Node(array[0]);
		for (int i = 1; i < array.length; i++) {
			Node node = new Node(array[i]);
			if (topNode.getValue() >= array[i]) {
				topNode.setLeftNode(node);
			} else if (topNode.getValue() < array[i]) {
				topNode.setRightNode(node);
			} else {
				System.err.println("怎么会出现这样的异常分支呢！！！");
			}
		}
		return topNode;
	}

	public static int[] generateRandomArray() {
		Random random = new Random();
		int length = 100000;
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = random.nextInt(10000000);
		}
		return array;
	}

	private static class Node {
		private int value;
		private Node leftNode;
		private Node rightNode;

		public Node(int value) {
			super();
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public Node getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(Node leftNode) {
			if (this.leftNode == null) {
				this.leftNode = leftNode;
			} else if (leftNode.getValue() <= this.leftNode.getValue()) {
				this.leftNode.setLeftNode(leftNode);
			} else if (leftNode.getValue() > this.leftNode.getValue()) {
				this.leftNode.setRightNode(leftNode);
			} else {
				System.err.println("设置左节点的时候怎么能出现这样的分支呢！！！");
			}
		}

		public Node getRightNode() {
			return rightNode;
		}

		public void setRightNode(Node rightNode) {
			if (this.rightNode == null) {
				this.rightNode = rightNode;
			} else if (rightNode.getValue() <= this.rightNode.getValue()) {
				this.rightNode.setLeftNode(rightNode);
			} else if (rightNode.getValue() > this.rightNode.getValue()) {
				this.rightNode.setRightNode(rightNode);
			} else {
				System.err.println("设置右节点的时候怎么能出现这样的分支呢！！！");
			}
		}

		private StringBuilder printNode(Node node, StringBuilder sb) {
			if (node.getLeftNode() != null) {
				printNode(node.getLeftNode(), sb);
			}
			sb.append(node.getValue()).append(", ");
			if (node.getRightNode() != null) {
				printNode(node.getRightNode(), sb);
			}
			return sb;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("[");
			sb = printNode(this, sb);
			sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1).append("]");
			return sb.toString();
		}

	}

}

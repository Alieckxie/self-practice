package com.alieckxie.self.bean;

import java.util.LinkedList;

public class GeneralizedList<T> {

	// public static final boolean ATOM = true;
	// public static final boolean LIST = false;

	private CoreDataNode coreDataNode;
	private ElementTag elementTag;
	private LinkedList<GeneralizedList<T>> elementList;
	private T element;

	/**
	 * @return the coreDataNode
	 */
	protected CoreDataNode getCoreDataNode() {
		return coreDataNode;
	}

	/**
	 * @param coreDataNode
	 *            the coreDataNode to set
	 */
	protected void setCoreDataNode(CoreDataNode coreDataNode) {
		this.coreDataNode = coreDataNode;
	}

	/**
	 * @return the elementTag
	 */
	public ElementTag getElementTag() {
		return elementTag;
	}

	/**
	 * @param elementTag
	 *            the elementTag to set
	 */
	public void setElementTag(ElementTag elementTag) {
		this.elementTag = elementTag;
	}

	/**
	 * @return the ElementList
	 */
	public LinkedList<GeneralizedList<T>> getElementList() {
		return elementList;
	}

	/**
	 * @param ElementList
	 *            the ElementList to set
	 */
	public void setElementList(LinkedList<GeneralizedList<T>> elementList) {
		this.elementList = elementList;
	}

	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}

	/**
	 * @param element
	 *            the element to set
	 */
	public void setElement(T element) {
		this.element = element;
	}

	public void getValue() {

	}

	public void addValue(T value, ElementTag elementTag) {
		GeneralizedList<T> list = new GeneralizedList<T>();
		if (elementList == null) {
			this.elementList = new LinkedList<GeneralizedList<T>>();
			this.elementTag = ElementTag.LIST;
		}
		this.elementList.add(list);
		switch (elementTag) {
			case ATOM:
				list.setElementTag(ElementTag.ATOM);
				list.setElement(value);
				break;
			case LIST:
				list.setElementTag(ElementTag.LIST);
				list.addValue(value, ElementTag.ATOM);
				break;

			default:
				break;
		}
	}

	public void addValue(LinkedList<GeneralizedList<T>> value) {

	}

	private static class CoreDataNode {

		private int elementsCounts;
	}

	public static enum ElementTag {
		ATOM, LIST;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		switch (this.elementTag) {
			case ATOM:
				sb.append(element);
				break;
			case LIST:
				sb.append("[");
				for (GeneralizedList<T> generalizedList : elementList) {
					switch (generalizedList.getElementTag()) {
						case ATOM:
							wrapQuote(sb, generalizedList);
							break;
						case LIST:
							sb.append(generalizedList).append(",");
							break;

						default:
							break;
					}
				}
				sb.deleteCharAt(sb.length() - 1).append("]");
				break;

			default:
				break;
		}
		return sb.toString();
	}

	private void wrapQuote(StringBuilder sb, Object target) {
		sb.append("\"").append(target).append("\"").append(",");
	}
}

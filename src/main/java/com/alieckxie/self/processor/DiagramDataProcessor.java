package com.alieckxie.self.processor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DiagramDataProcessor {

	private static final long DAY_MILLIS = 24 * 3600 * 1000L;
	private static final long HOUR_MILLIS = 3600 * 1000L;
	private static final long MINUTE_MILLIS = 60 * 1000L;
	private String processorName;
	private String processorId;
	private OrderRule orderRule;
	private int size;
	private DataNode rootNode = new DataNode();

	public DiagramDataProcessor(String processorName, String processorId) {
		super();
		this.processorName = processorName;
		this.processorId = processorId;
	}

	public void addRawData(List<Map<String, Object>> rawData) {
		// 假定传入进来的都是从数据库中查询出来的原始数据
		DataNode nextNode = rootNode;
		for (Map<String, Object> map : rawData) {
			BigDecimal ltst = (BigDecimal) map.get("ltst");
			BigDecimal vol = (BigDecimal) map.get("vol");
			Date mktUpTm = (Date) map.get("mktUpTm");
			nextNode = nextNode.setData(mktUpTm, ltst, vol);
		}
		System.out.println("size:"+size);
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getProcessorId() {
		return processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	public List<Map<String, Object>> getDataAsList() {
		// 用于将DataNode的链式数据转换成List返回出去
		return null; // TODO 记得修改
	}

	private class DataNode {
		private Date time;
		private BigDecimal price; // 加权平均价
		private BigDecimal vol; // 累计成交量
		private DataNode next;
		private DataNode prev;

		public DataNode setData(Date time, BigDecimal price, BigDecimal vol) {
			DataNode targetNode = this;
			if (this.time == null) {
				this.time = time;
				this.price = price;
				this.vol = vol;
			} else {
				int diffTime = getMinuteDiff(time, this.time);
				if (diffTime > 0) { // 传入时间在本节点时间之后【查询时多为时间正序排序，推送时为倒序排序】
					for (int i = 0; i < diffTime; i++) {
						if (targetNode.getNext() == null) {
							DataNode emptyMinuteNode = generateEmptyMinuteNode(this.time, i + 1);
							targetNode.setNext(emptyMinuteNode);
							size++;
						}
						targetNode = targetNode.getNext();
					}
					BigDecimal vol2 = targetNode.getVol() == null ? BigDecimal.ZERO : targetNode.getVol();
					BigDecimal price2 = targetNode.getPrice() == null ? BigDecimal.ZERO : targetNode.getPrice();
					BigDecimal newVol = vol2.add(vol);
					BigDecimal newPrice = price2.multiply(vol2).add(price.multiply(vol))
							.divide(newVol, 4, BigDecimal.ROUND_HALF_UP);
					targetNode.setPrice(newPrice);
					targetNode.setVol(newVol);
				} else if (diffTime == 0) {
					// 在这里计算加权平均价
					BigDecimal newVol = this.vol.add(vol);
					BigDecimal newPrice = this.price.multiply(this.vol).add(price.multiply(vol))
							.divide(newVol, 4, BigDecimal.ROUND_HALF_UP);
					this.setPrice(newPrice);
					this.setVol(newVol);
				} else {
					for (int i = 0; i < -diffTime; i++) {
						if (targetNode.getPrev() == null) {
							DataNode emptyMinuteNode = generateEmptyMinuteNode(this.time, i + 1);
							targetNode.setPrev(emptyMinuteNode);
							size++;
						}
						targetNode = targetNode.getPrev();
					}
					BigDecimal vol2 = targetNode.getVol() == null ? BigDecimal.ZERO : targetNode.getVol();
					BigDecimal price2 = targetNode.getPrice() == null ? BigDecimal.ZERO : targetNode.getPrice();
					BigDecimal newVol = vol2.add(vol);
					BigDecimal newPrice = price2.multiply(vol2).add(price.multiply(vol))
							.divide(newVol, 4, BigDecimal.ROUND_HALF_UP);
					targetNode.setPrice(newPrice);
					targetNode.setVol(newVol);
				}
			}
			return targetNode;

		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getVol() {
			return vol;
		}

		public void setVol(BigDecimal vol) {
			this.vol = vol;
		}

		public DataNode getNext() {
			return next;
		}

		public void setNext(DataNode next) {
			this.next = next;
		}

		public DataNode getPrev() {
			return prev;
		}

		public void setPrev(DataNode prev) {
			this.prev = prev;
		}

		private DataNode generateEmptyMinuteNode(Date time, int timeOffset) {
			DataNode newNode = new DataNode();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.MINUTE, timeOffset);
			newNode.setTime(calendar.getTime());
			return newNode;
		}

		private int getHourDiff(Date date1, Date date2) {
			if (date1 == null || date2 == null) {
				throw new IllegalArgumentException("The date must not be null");
			}
			if (Math.abs(date1.getTime() - date2.getTime()) > DAY_MILLIS) {
				throw new IllegalArgumentException("The time difference must in one day");
			}
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) != 0) {
				cal1.add(Calendar.DATE, 1);
				cal2.add(Calendar.DATE, 1);
			}
			return (cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR)) * 24
					+ (cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY));
		}

		private int getMinuteDiff(Date currentDate, Date baseDate) {
			if (currentDate == null || baseDate == null) {
				throw new IllegalArgumentException("The date must not be null");
			}
			if (Math.abs(currentDate.getTime() - baseDate.getTime()) > DAY_MILLIS) {
				throw new IllegalArgumentException("The time difference must in one day");
			}
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(currentDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(baseDate);
			if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) != 0) {
				cal1.add(Calendar.DATE, 1);
				cal2.add(Calendar.DATE, 1);
			}
			return (cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR)) * 24 * 60
					+ ((cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY)) * 60
							+ (cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE)));
		}

	}

	private enum OrderRule {
		ASC, DESC
	}

}

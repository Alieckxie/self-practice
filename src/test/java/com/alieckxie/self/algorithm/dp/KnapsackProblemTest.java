package com.alieckxie.self.algorithm.dp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.alieckxie.self.algorithm.util.Pair;
import com.google.gson.Gson;

public class KnapsackProblemTest {

	/**
	 * 作为Key的tuple，第一个是元素下标，第二个是问题规模。<br>
	 * 作为Value的tuple，第一个是对应问题的dp解，第二个是该dp解的一个元素组合（记录的是下标）
	 */
	private Map<Pair<Integer, BigDecimal>, Pair<BigDecimal, List<Integer>>> cache4Recursion = new HashMap<Pair<Integer, BigDecimal>, Pair<BigDecimal, List<Integer>>>();
	private int count4Recursion = 0;
	private int count4Circulation = 0;
	private int count4BackTrace = 0;
	private int count4BackTrace2 = 0;
	private int btTempC1 = 0;
	private int btTempC2 = 0;
	private static Gson gson = new Gson();
	private LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> institutionMap = new LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>>();

	@Test
	public void testKnapsack() {
		List<Item> list = getItemListAllInteger1();
//		List<Item> list = getItemList();
		List<Integer> itemCombineList4Recursion = new LinkedList<Integer>();
		List<Integer> itemCombineList4Circulation = new LinkedList<Integer>();
		List<Integer> itemCombineList4BackTrace = new LinkedList<Integer>();
		List<Integer> itemCombineList4BackTrace2 = new LinkedList<Integer>();
		// 目标重量
		BigDecimal targetWeight = BigDecimal.valueOf(110);
		// 自顶向下备忘递归求解
		long start = System.nanoTime();
		BigDecimal dpRecurResult = dynamicProgrammingRecursion(list.size() - 1, list, targetWeight, itemCombineList4Recursion);
		long end = System.nanoTime();
		System.out.println(dpRecurResult);
		System.out.println("size of cache: " + cache4Recursion.size());
		System.out.println("counts of access cache: " + count4Recursion);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms");
		System.out.println();
		
		System.out.println(gson.toJson(itemCombineList4Recursion));
		BigDecimal[] sum = new BigDecimal[2];
		checkListSum(list, itemCombineList4Recursion, sum);
		BigDecimal sumWeight = sum[0], sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight)); // 这里要引入hamcrest-library才可以用这个类！一般需要显式引用，记得scope是test！
		Assert.assertEquals(dpRecurResult, sumValue);
		System.out.println();
		
		// 自底向上序偶优选求解
		start = System.nanoTime();
		BigDecimal dpCirculResult = dynamicProgrammingCirculation(list, targetWeight, itemCombineList4Circulation);
		end = System.nanoTime();
		System.out.println("counts of circulation: " + count4Circulation);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms");
		List<List<Integer>> listOfItemCombineList4Circulation = null;
		for (Entry<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> entry : institutionMap.entrySet()) {
			if (entry.getValue().getX().compareTo(dpCirculResult) == 0) {
				listOfItemCombineList4Circulation = entry.getValue().getY();
			}
		}
		for (List<Integer> itemCombineList : listOfItemCombineList4Circulation) {
			System.out.println(gson.toJson(itemCombineList));
			checkListSum(list, itemCombineList, sum);
			sumWeight = sum[0];
			sumValue = sum[1];
			Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight)); // 这里要引入hamcrest-library才可以用这个类！一般需要显式引用，记得scope是test！
			Assert.assertEquals(dpRecurResult, sumValue);
			System.out.println();
		}
		Assert.assertEquals(dpRecurResult, dpCirculResult);
		System.out.println();
		
		// 回溯限界剪枝求解1
		start = System.nanoTime();
		@SuppressWarnings("unchecked")
		List<Item> newList = (List<Item>) ((ArrayList<Item>) list).clone();
		BigDecimal dpBackTrace = dynamicProgrammingBackTrace(newList, targetWeight, itemCombineList4BackTrace);
		end = System.nanoTime();
		System.out.println("counts of circulation: " + count4BackTrace);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms");
		System.out.println(gson.toJson(itemCombineList4BackTrace));
		checkListSum(newList, itemCombineList4BackTrace, sum);
		sumWeight = sum[0];
		sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight));
		Assert.assertEquals(dpRecurResult, dpBackTrace);
		System.out.println();

		// 回溯限界剪枝求解2
		start = System.nanoTime();
		@SuppressWarnings("unchecked")
		List<Item> newList2 = (List<Item>) ((ArrayList<Item>) list).clone();
		BigDecimal dpBackTrace2 = dynamicProgrammingBackTrace2(newList2, targetWeight, itemCombineList4BackTrace2);
		end = System.nanoTime();
		System.out.println("counts of circulation: " + count4BackTrace2);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms");
		System.out.println(gson.toJson(itemCombineList4BackTrace2));
		checkListSum(newList2, itemCombineList4BackTrace2, sum);
		sumWeight = sum[0];
		sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight));
		Assert.assertEquals(dpRecurResult, dpBackTrace2);
	}

	private void checkListSum(List<Item> list, List<Integer> itemCombineList, BigDecimal[] sum) {
		BigDecimal sumWeight = BigDecimal.ZERO, sumValue = BigDecimal.ZERO;
		for (Integer itemIndex : itemCombineList) {
			sumWeight = sumWeight.add(list.get(itemIndex).getWeight());
			sumValue = sumValue.add(list.get(itemIndex).getValue());
		}
		System.out.println("sumWeight: " + sumWeight + " sumValue: " + sumValue);
		if (sum != null) {
			sum[0] = sumWeight;
			sum[1] = sumValue;
		}
	}

	/**
	 * 自顶向下动态规划求解0/1背包的问题，同时还求出最优解的一个组合
	 * @param itemIndex 问题层级
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4Recursion 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值<br>
	 * 但是其实还有一个隐含的、通过引用传递最优解集的一个返回值->itemCombineList，<br>
	 * 工程中最好不要这么玩，就算真要这么做，注释一定要有三层厚！
	 */
	public BigDecimal dynamicProgrammingRecursion(int itemIndex, List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4Recursion) {
		if (itemIndex == -1) { // 递归至最底层时，直接返回0
			return BigDecimal.ZERO;
		}
		BigDecimal result;
		// 访问缓存
		Pair<Integer, BigDecimal> cacheKey = new Pair<Integer, BigDecimal>(itemIndex, targetWeight);
		Pair<BigDecimal, List<Integer>> pair = cache4Recursion.get(cacheKey);
		if (pair != null) {
			result = pair.getX();
			count4Recursion++;
			itemCombineList4Recursion.addAll(pair.getY()); // 同理从缓存返回前要把路径信息挂在钩子上钩出去
			return result;
		}
		// 无缓存，开始正式计算
		Item currentItem = itemList.get(itemIndex);
		BigDecimal currentWeight = currentItem.getWeight();// 获取数据
		BigDecimal currentValue = currentItem.getValue();// 获取数据
		List<Integer> selectedList = new LinkedList<Integer>();// 若选该item的组合集合
		List<Integer> nonSelectList = new LinkedList<Integer>();// 不选的组合集合
		if (currentWeight.compareTo(targetWeight) > 0) { // 当前重量大于目标重量，必定不选，无需比较，直接进入下一层不选状态的问题求解
			result = dynamicProgrammingRecursion(itemIndex - 1, itemList, targetWeight, nonSelectList);
			// 虽然不选，但是要把下层钩上来的路径信息继续传递给上层，容易忽略！TODO 这里曾发生过由于itemCombineList的引用传递而导致的BUG！
			itemCombineList4Recursion.addAll(nonSelectList);
			return result;
		}
		// 正常进行递归求解
		BigDecimal selectedResult = dynamicProgrammingRecursion(itemIndex - 1, itemList, targetWeight.subtract(currentWeight), selectedList).add(currentValue);
		BigDecimal nonSelectResult = dynamicProgrammingRecursion(itemIndex - 1, itemList, targetWeight, nonSelectList);
		List<Integer> resultList = null; // 只能把这一层的组合选择存入缓存中，故需要一个临时变量
		if (selectedResult.compareTo(nonSelectResult) > 0) { // 比较求解结果，确定该itemIndex和targetWeight下的问题最优解
			result = selectedResult;
			selectedList.add(itemIndex); // 选该item，则将其加入解集
			resultList = selectedList;
			itemCombineList4Recursion.addAll(selectedList);// 合并至上一层的解集中
		} else {
			result = nonSelectResult;
			resultList = nonSelectList;
			itemCombineList4Recursion.addAll(nonSelectList);// 合并至上一层的解集中
		}
		cache4Recursion.put(cacheKey, new Pair<BigDecimal, List<Integer>>(result, resultList)); // TODO 不能把itemCombineList给存入缓存了，这是要返回给上一层的！该存入缓存的是resultList。
		return result;
	}

	/**
	 * 自底向上动态规划求解0/1背包的问题，同时还求出最优解的所有组合。<br>
	 * <b>自底向上动态规划求解，还是以序偶的形式能更容易理解，这是对暴力求解的过程进行优选所得到的结果。</b><br>
	 * <b>算法描述</b><br>
	 * 1、暴力求解：从第一个物品开始向后依次计算，每次计算都是在前一次计算结果（选或不选的计算结果）的基础上进行计算，并将结果存入列表。
	 * 每次都遍历前次结果，对每种结果都进行（选或不选）的计算并同样将结果存入列表，
	 * 因此列表长度按2、4、8、16的2^n指数形式增长，如果出现超出targetWeight的情况则长度会稍稍缩短。当然，最终一定能求得解。<br>
	 * 2、序偶优选：有若干个物品，我们不妨设前3个物品重量分别为1、2、3，若(1, 1, 0)即前两个都选第三个不选，重量为3，若(0, 0, 1)即前两个不选第三个选入，重量也为3。
	 * 如果targetWeight > 3，此时我们得到了两个重量为3的结果，根据最优性原理，当将第4个物品纳入考虑时，在遍历到重量为3的前次计算结果时，我们只需取价值最大的那个情况。
	 * 因此，我们在每轮计算时应进行<i><b>优选</b></i>，计算结果的weight值不存在则直接放入集合，若存在则比较，集合中只保存该weight下value最大的结果。<br>
	 * 3、获取结果：从最终的集合中，找到value最大的序偶，即为最终结果。
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4Circulation 当前规模最优解的所有组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingCirculation(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4Circulation) {
		// Pair<Weight, Value>
		Item firstItem = itemList.get(0);
		LinkedList<List<Integer>> listOfFirstNonSelectList = getEmptyListOfList();
		institutionMap.put(BigDecimal.ZERO, new Pair<>(BigDecimal.ZERO, listOfFirstNonSelectList));
		
		LinkedList<List<Integer>> listOfFirstSelectList = getEmptyListOfList();
		listOfFirstSelectList.getFirst().add(0);
		institutionMap.put(firstItem.getWeight(), new Pair<>(firstItem.getValue(), listOfFirstSelectList));
		
		for (int i = 1; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			BigDecimal weight = item.getWeight();
			BigDecimal value = item.getValue();
			LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> currentHashMap = new LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>>();
			for (Entry<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> entry : institutionMap.entrySet()) {
				count4Circulation++;
				BigDecimal preWeight = entry.getKey();
				Pair<BigDecimal, List<List<Integer>>> pair = entry.getValue();
				BigDecimal preValue = pair.getX();
				List<List<Integer>> preListOfList = pair.getY();
				
				BigDecimal weightAfterSelect = preWeight.add(weight);
				BigDecimal valueAfterSelect = preValue.add(value);
				if (weightAfterSelect.compareTo(targetWeight) <= 0) {
					Pair<BigDecimal, List<List<Integer>>> pairInMap = institutionMap.get(weightAfterSelect);
					if (pairInMap == null) { // 选择该节点加入之后，是未出现过的重量 --> 新增
						LinkedList<List<Integer>> newListOfList = genNewFromPre(i, preListOfList);
						currentHashMap.put(weightAfterSelect, new Pair<BigDecimal, List<List<Integer>>>(valueAfterSelect, newListOfList));
					} else { // 之前出现过该重量的组合
						BigDecimal valueInMap = pairInMap.getX();
						if (valueAfterSelect.compareTo(valueInMap) > 0) { // 选入之后比原先的value大，覆盖value，(覆盖)选入该item后的listOfList
							LinkedList<List<Integer>> newListOfList = genNewFromPre(i, preListOfList);
							currentHashMap.put(weightAfterSelect, new Pair<BigDecimal, List<List<Integer>>>(valueAfterSelect, newListOfList));
						} else if (valueAfterSelect.compareTo(valueInMap) == 0) { // 选入之后和原先的value相同，说明有多条路径
							LinkedList<List<Integer>> newListOfList = genNewFromPre(i, preListOfList);
							pairInMap.getY().addAll(newListOfList); // (合并)
						}
					}
				} else {
					continue;
				}
			}
			institutionMap.putAll(currentHashMap);
		}
		BigDecimal result = BigDecimal.ZERO, weight = BigDecimal.ZERO;
		long start = System.nanoTime();
		for (Entry<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> entry : institutionMap.entrySet()) {
			if (entry.getValue().getX().compareTo(result) >= 0) {
				weight = entry.getKey();
				result = entry.getValue().getX();
			}
		}
		long end = System.nanoTime();
		System.out.println("finding result costs time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms");
		System.out.println("weight: " + weight + " value: " + result + " institutionMap.size(): " + institutionMap.size());
		return result;
	}

	/**
	 * 根据前次计算结果生成的最优解组合list克隆一个新的list，并将item编号加到最后。
	 * 加入时会检查重复的情况以确保组合结果的正确性，因为这是获取所有的组合，故需要进行检查。
	 * 
	 * @param i 物品的编号
	 * @param preListOfList 前次计算结果所有可行的组合的list
	 * @return 当前新克隆的一份处理好的list
	 */
	private LinkedList<List<Integer>> genNewFromPre(int i, List<List<Integer>> preListOfList) {
		LinkedList<List<Integer>> newListOfList = new LinkedList<List<Integer>>();
		for (List<Integer> list : preListOfList) { // 将之前的listOfList的每个单独List都克隆一份，然后加上该item的index再加入到newListOfList中
			LinkedList<Integer> tempList = (LinkedList<Integer>) list;
			if (tempList.size() == 0 || tempList.getLast() != i) { // 如果出现重复的情况，这是不加入的！
				@SuppressWarnings("unchecked")
				LinkedList<Integer> cloneList = (LinkedList<Integer>) tempList.clone();
				cloneList.add(i);
				newListOfList.add(cloneList);
			}
		}
		return newListOfList;
	}

	private LinkedList<List<Integer>> getEmptyListOfList() {
		LinkedList<List<Integer>> listOfList = new LinkedList<List<Integer>>();
		listOfList.add(new LinkedList<Integer>());
		return listOfList;
	}

	/**
	 * 回溯法配合限界函数剪枝求解0/1背包的问题。<br>
	 * <b>核心还是在于限界函数的设计，好的限界函数有利于剪枝。<i>限界函数的设计思路还有待进一步研究</i>。</b><br>
	 * <b>算法描述</b><br>
	 * 1、排序，单位重量价值高的排前面，单位价值相同的，重量小的排前面。<br>
	 * 2、进行深度优先装入时，若探索到了叶节点得到一个可行解，则该解的value可以成为界（若该value是当前最优，则应将其指定为界）。<br>
	 * 3、进行深度优先装入时，若遇到要回溯时，判断回溯点的解上限（即该节点的贪心解），
	 * 若上限比当前的界还小，则该回溯点无需再深入，可以直接回溯到该回溯点的上一个回溯点（即继续向上回溯），以减少不必要的深入探索。<br>
	 * 4、直到回溯到根节点时，得到最终的解。
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4BackTrace 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingBackTrace(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4BackTrace) {
		Collections.sort(itemList, new Comparator<Item>() { // TODO itemList最好是一个新的对象，以免影响其他的方案测试

			@Override
			public int compare(Item o1, Item o2) {
				count4BackTrace++;
				int compareResult = o2.getValue().divide(o2.getWeight(), 3, BigDecimal.ROUND_HALF_UP)
						.compareTo(o1.getValue().divide(o1.getWeight(), 3, BigDecimal.ROUND_HALF_UP));
				if (compareResult == 0) {
					return o1.getWeight().compareTo(o2.getWeight());
				}
				return compareResult;
			}
		});

		BigDecimal resultValue = BigDecimal.ONE.negate(), currentWeight = BigDecimal.ZERO, currentValue = BigDecimal.ZERO;
		// i为当前考察的物品编号，n为物品数量
		int i = 0, itemAmount = itemList.size();
		LinkedList<Integer> stackList = new LinkedList<Integer>();
		while (true) { // 这个条件似乎已经没用了，因为在checkBound与resultValue比较时若已经回溯到头就直接return了
			while (i < itemAmount) {
				btTempC1++;
				Item currentItem = itemList.get(i);
				BigDecimal itemWeight = currentItem.getWeight();
				BigDecimal itemValue = currentItem.getValue();
				if (currentWeight.add(itemWeight).compareTo(targetWeight) <= 0) {
					currentWeight = currentWeight.add(itemWeight);
					currentValue = currentValue.add(itemValue);
					stackList.add(i);
				} else {
					break;
				}
				i++;
			}
			if (i >= itemAmount) {
				resultValue = currentValue;
				itemCombineList4BackTrace.clear();
				itemCombineList4BackTrace.addAll(stackList);
			}
			while (checkBound(currentWeight, currentValue, i, targetWeight, itemList).compareTo(resultValue) <= 0) {
				int stackSize = stackList.size();
				if (stackSize == 0) {
					return resultValue; // 算法在此结束
				}
				i = stackList.removeLast();
				Item item = itemList.get(i);
				currentWeight = currentWeight.subtract(item.getWeight());
				currentValue = currentValue.subtract(item.getValue());
			}
			i++;
			count4BackTrace += btTempC1 + btTempC2;
			btTempC1 = 0;
			btTempC2 = 0;
		}
	}
	
	private BigDecimal checkBound(BigDecimal currentWeight, BigDecimal currentValue, int itemIndex, BigDecimal targetWeight, List<Item> itemList) {
		for (int i = itemIndex + 1; i < itemList.size(); i++) {
			btTempC2++;
			Item item = itemList.get(i);
			BigDecimal itemWeight = item.getWeight();
			BigDecimal itemValue = item.getValue();
			currentWeight = currentWeight.add(itemWeight);
			if (currentWeight.compareTo(targetWeight) < 0) {
				currentValue = currentValue.add(itemList.get(i).getValue());
			} else {
				// (tw-(cw-iw))*iv/iw + cv
				BigDecimal bound = targetWeight.subtract(currentWeight.subtract(itemWeight)).multiply(itemValue.divide(itemWeight, 3, BigDecimal.ROUND_HALF_UP)).add(currentValue);
				return bound;
			}
		}
		return currentValue;
	}

	/**
	 * 回溯法配合限界函数剪枝求解0/1背包的问题。<br>
	 * <b>核心还是在于限界函数的设计，好的限界函数有利于剪枝。<i>限界函数的设计思路还有待进一步研究</i>。</b><br>
	 * <b>算法描述</b><br>
	 * 1、排序，单位重量价值高的排前面，单位价值相同的，重量小的排前面。<br>
	 * 2、进行深度优先装入时，若探索到了叶节点得到一个可行解，则该解的value可以成为<b>界</b>（若该value是当前最优，则应将其指定为界）。<br>
	 * 3、进行深度优先装入时，若遇到要回溯时，判断回溯点的解<b>上限</b>（即该节点的贪心解），
	 * 若上限比当前的界还小，则该回溯点无需再深入，可以直接回溯到该回溯点的上一个回溯点（即继续向上回溯），以减少不必要的深入探索。<br>
	 * 4、直到回溯到根节点时，得到最终的解。<br>
	 * <b><i>注意厘清上限和上界在该算法描述中的概念区别</i></b>
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4BackTrace 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingBackTrace2(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4BackTrace) {
		Collections.sort(itemList, new Comparator<Item>() { // TODO itemList最好是一个新的对象，以免影响其他的方案测试

			@Override
			public int compare(Item o1, Item o2) {
				count4BackTrace2++;
				int compareResult = o2.getValue().divide(o2.getWeight(), 3, BigDecimal.ROUND_HALF_UP)
						.compareTo(o1.getValue().divide(o1.getWeight(), 3, BigDecimal.ROUND_HALF_UP));
				if (compareResult == 0) {
					return o1.getWeight().compareTo(o2.getWeight());
				}
				return compareResult;
			}
		});
		
		int itemAmount = itemList.size();
		BigDecimal currentWeight = BigDecimal.ZERO, currentValue = BigDecimal.ZERO, resultValue = BigDecimal.ONE.negate();
		LinkedList<Integer> stackList = new LinkedList<Integer>();
		int skipPoint = -1, i = 0; // 初始起点或回溯后的起点（-1的下一个或使超重的物品的下一个物品，即第0号物品或下一个物品），是前一个不合适的物品
		while (true) { // 栈空则循环结束
			while (true) { // 判断上限值是否比界大
				BigDecimal boundValue = currentValue; // 上限值
				for (i = skipPoint + 1; i < itemAmount; i++) {
					count4BackTrace2++;
					Item currentItem = itemList.get(i);
					BigDecimal itemWeight = currentItem.getWeight();
					BigDecimal itemValue = currentItem.getValue();
					BigDecimal tmpWeight = currentWeight.add(itemWeight); // 重量累加
					if (tmpWeight.compareTo(targetWeight) > 0) { // 选入当前物品超重，则计算对于该物品的贪心解作为上限值
						boundValue = targetWeight.subtract(currentWeight).multiply(itemValue.divide(itemWeight, 3, BigDecimal.ROUND_HALF_UP)).add(currentValue);
						break;
					}
					currentWeight = tmpWeight;
					boundValue = currentValue = currentValue.add(itemValue); // 价值累加。能走到这一步说明没有超重，同时记录一个上限值
					stackList.add(i); // 当前物品入栈
				}

				if (boundValue.compareTo(resultValue) <= 0) { // 若上限值比当前的界要小或相等，则可以直接回溯了！剪枝在此处进行！（上限值可能是可行解的值，也可能是超重后的贪心解的值）
					// 求得的上限仍比上界小，说明爆表物品的前一个物品就不合适。由于会爆表的物品没有入栈，所以要将前一个物品出栈。
					if (stackList.size() == 0) { // 回溯到头了，则算法结束。
						return resultValue;
					}
					skipPoint = stackList.removeLast(); // 不合适物品的编号，在此处回溯剪枝
					Item lastItem = itemList.get(skipPoint);
					currentWeight = currentWeight.subtract(lastItem.getWeight());
					currentValue = currentValue.subtract(lastItem.getValue());
					continue;
				} else { // 上限值比界大，退出循环，检查是否是可行解
					skipPoint = i; // 下一次试验就从前次的中断点开始，无需回溯
					break;
				}
			}
			if (i >= itemAmount - 1) { // 若全部物品都遍历过，则设定上界。上一个判断可以确保该可行解的值比当前界的值要大，可以进行更新
				resultValue = currentValue;
				itemCombineList4BackTrace.clear();
				itemCombineList4BackTrace.addAll(stackList);
			}
		}
	}

	public List<Item> getItemList() {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item(3.1, 4.5));
		list.add(new Item(4.8, 5.3));
		list.add(new Item(5, 8.8));
		list.add(new Item(2, 3.2));
		list.add(new Item(9.3, 10.3));
		list.add(new Item(2.4, 3.9));
		list.add(new Item(6, 9));
		list.add(new Item(3.7, 4.8));
		list.add(new Item(3, 4));
		list.add(new Item(4.5, 5.8));
		list.add(new Item(9.1, 10.9));
		list.add(new Item(12.5, 15.4));
		list.add(new Item(4, 5));
		list.add(new Item(5, 8));
		list.add(new Item(9.3, 10.6));
		list.add(new Item(2.1, 3.2));
		list.add(new Item(6.7, 9.3));
		list.add(new Item(5.2, 8.3));
		list.add(new Item(11.5, 13));
		list.add(new Item(12, 15));
		return list;
	}
	
	public List<Item> getItemListAllInteger() {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item(3, 4));
		list.add(new Item(4, 5));
		list.add(new Item(5, 8));
		list.add(new Item(2, 3));
		list.add(new Item(9, 10));
		list.add(new Item(2, 3));
		list.add(new Item(6, 9));
		list.add(new Item(3, 4));
		list.add(new Item(3, 4));
		list.add(new Item(4, 5));
		list.add(new Item(9, 10));
		list.add(new Item(12, 15));
		list.add(new Item(4, 5));
		list.add(new Item(5, 8));
		list.add(new Item(9, 10));
		list.add(new Item(2, 3));
		list.add(new Item(6, 9));
		list.add(new Item(5, 8));
		list.add(new Item(11, 13));
		list.add(new Item(12, 15));
		return list;
	}
	
	public List<Item> getItemListAllInteger1() {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item(1, 11));
		list.add(new Item(11, 21));
		list.add(new Item(21, 31));
		list.add(new Item(23, 33));
		list.add(new Item(33, 43));
		list.add(new Item(43, 53));
		list.add(new Item(45, 55));
		list.add(new Item(55, 65));
		return list;
	}

	private static class Item {

		BigDecimal weight;
		BigDecimal value;

		/**
		 * @param weight
		 * @param value
		 */
		public Item(BigDecimal weight, BigDecimal value) {
			super();
			this.weight = weight;
			this.value = value;
		}

		public Item(int weight, int value) {
			this(BigDecimal.valueOf(weight), BigDecimal.valueOf(value));
		}
		
		public Item(Double weight, Double value) {
			this(BigDecimal.valueOf(weight), BigDecimal.valueOf(value));
		}
		
		public Item(Double weight, Integer value) {
			this(BigDecimal.valueOf(weight), BigDecimal.valueOf(value));
		}
		
		public Item(Integer weight, Double value) {
			this(BigDecimal.valueOf(weight), BigDecimal.valueOf(value));
		}

		/**
		 * @return the weight
		 */
		public BigDecimal getWeight() {
			return weight;
		}

		/**
		 * @return the value
		 */
		public BigDecimal getValue() {
			return value;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Item [weight=" + weight + ", value=" + value + "]";
		}

	}

	@Test
	public void testHashMap() {
		Pair<Integer, BigDecimal> dualTuple1 = new Pair<Integer, BigDecimal>(1, BigDecimal.valueOf(8.1));
		Pair<Integer, BigDecimal> dualTuple2 = new Pair<Integer, BigDecimal>(1, BigDecimal.valueOf(8.1));
		System.out.println(cache4Recursion.size());
		cache4Recursion.put(dualTuple1, new Pair<BigDecimal, List<Integer>>(BigDecimal.valueOf(1), null));
		System.out.println(cache4Recursion.size() + " " + cache4Recursion.get(dualTuple2).getX());
		cache4Recursion.put(dualTuple2, new Pair<BigDecimal, List<Integer>>(BigDecimal.valueOf(2), null));
		System.out.println(cache4Recursion.size() + " " + cache4Recursion.get(dualTuple1).getX());
	}
}

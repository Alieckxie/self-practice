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
import java.util.PriorityQueue;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

/**
 * 为探究0/1背包问题的一个测试类。
 * 
 * <p>本来一开始只是想试试带备忘的递归式动态规划的Java写法，
 * 后来发现0/1背包居然串联起算法设计和分析的大部分内容。
 * 索性就一撸到底，打造一个我心目中的算法代码架构。</p>
 * 
 * <p>主要内容为：1、自顶向下备忘递归求解；2、自底向上序偶优选求解；
 * 3.1、回溯限界剪枝求解1；3.2、回溯限界剪枝求解2；4、分支限界法求解。</p>
 * 
 * <p>目前测试出的现象给人这种感觉：整数规划的情况下，1、2的效率要比3、4的要高；非整数的情况下，3、4的效率要比1、2高。
 * 且若有重复物品，最后的物品组合有多种方式时，3、4的效率会急剧下降。可以参考代码中统计的循环次数。</p>
 * 
 * <p>这份代码是我准备复试时创建的，复试后才完成。<br>
 * 参考书目为：《计算机算法基础》（第三版），余祥宣 崔国华 邹海明</p>
 * 
 * @author Alieckxie
 * @date 2019年03月30日
 *
 */
public class KnapsackProblemTest {

	/**
	 * 作为Key的Pair，第一个是元素下标，第二个是问题规模。<br>
	 * 作为Value的Pair，第一个是对应问题的dp解，第二个是该dp解的一个元素组合（记录的是下标）。
	 */
	private Map<Pair<Integer, BigDecimal>, Pair<BigDecimal, List<Integer>>> cache4Recursion = new HashMap<Pair<Integer, BigDecimal>, Pair<BigDecimal, List<Integer>>>();
	/**
	 * 重量作为Key，作为Value的Pair中，第一个是对应这个重量的最大价值，第二个是达到这个重量和价值的所有物品组合方式。
	 */
	private LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>> institutionMap = new LinkedHashMap<BigDecimal, Pair<BigDecimal, List<List<Integer>>>>();
	private int count4Recursion = 0;
	private int count4Circulation = 0;
	private int count4BackTrace = 0;
	private int count4BackTraceRefactor = 0;
	private static int count4BranchBound = 0;
	private static Gson gson = new Gson();

	private void resetGlobalVariable() {
		cache4Recursion.clear();
		institutionMap.clear();
		count4Recursion = 0;
		count4Circulation = 0;
		count4BackTrace = 0;
		count4BackTraceRefactor = 0;
		count4BranchBound = 0;
		LcNode.instanceCount = 0;
		System.out.println("\n+++++++++++++++++++++\n+++++++++++++++++++++\n+++++++++++++++++++++\n");
	}

	@Test
	public void testKnapsack() {
		testKnapsackBase(getItemListBackTrackSample(), BigDecimal.valueOf(110));
		resetGlobalVariable();
		testKnapsackBase(getItemListBranchBoundSample(), BigDecimal.valueOf(15));
		resetGlobalVariable();
		testKnapsackBase(getItemListAllInteger(), BigDecimal.valueOf(97));
		resetGlobalVariable();
		testKnapsackBase(getItemList(), BigDecimal.valueOf(83.4));
	}

	public void testKnapsackBase(List<Item> list, BigDecimal targetWeight) {
		// List<Item> list = getItemListBackTrackSample();
		// List<Item> list = getItemListBranchBoundSample();
		// List<Item> list = getItemListAllInteger();
		// List<Item> list = getItemList();
		List<Integer> itemCombineList4Recursion = new LinkedList<Integer>();
		List<Integer> itemCombineList4Circulation = new LinkedList<Integer>();
		List<Integer> itemCombineList4BackTrace = new LinkedList<Integer>();
		List<Integer> itemCombineList4BackTrace2 = new LinkedList<Integer>();
		List<Integer> itemCombineList4BranchBound = new LinkedList<Integer>();
		// 目标重量
		// BigDecimal targetWeight = BigDecimal.valueOf(55.7);
		// 1、自顶向下备忘递归求解
		long start = System.nanoTime();
		BigDecimal dpRecurResult = dynamicProgrammingRecursion(list.size() - 1, list, targetWeight, itemCombineList4Recursion);
		long end = System.nanoTime();
		System.out.println(dpRecurResult);
		System.out.println("size of cache: " + cache4Recursion.size());
		System.out.println("counts of access cache: " + count4Recursion);
		System.out.println("counts of Recursion total access: " + (cache4Recursion.size() + count4Recursion));
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms\n");
		// 校验求得的物品组合中价值总和是否与算法求得的最优解相等
		System.out.println(gson.toJson(itemCombineList4Recursion));
		BigDecimal[] sum = new BigDecimal[2];
		checkListSum(list, itemCombineList4Recursion, sum);
		BigDecimal sumWeight = sum[0], sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight)); // 这里要引入hamcrest-library才可以用这个类！一般需要显式引用，记得scope是test！
		Assert.assertEquals(dpRecurResult, sumValue);
		System.out.println("=========================\n");

		// 2、自底向上序偶优选求解
		start = System.nanoTime();
		BigDecimal dpCirculResult = dynamicProgrammingCirculation(list, targetWeight, itemCombineList4Circulation);
		end = System.nanoTime();
		System.out.println("counts of circulation: " + count4Circulation);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms\n");
		// 校验求得的物品组合中价值总和是否与算法求得的最优解相等
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
		System.out.println("=========================\n");
		
		// 3.1、回溯限界剪枝求解1
		start = System.nanoTime();
		@SuppressWarnings("unchecked")
		List<Item> newList = (List<Item>) ((ArrayList<Item>) list).clone();
		BigDecimal dpBackTrace = dynamicProgrammingBackTrack(newList, targetWeight, itemCombineList4BackTrace);
		end = System.nanoTime();
		System.out.println("counts of BackTrace circulation: " + count4BackTrace);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms\n");
		// 校验求得的物品组合中价值总和是否与算法求得的最优解相等
		System.out.println(gson.toJson(itemCombineList4BackTrace));
		checkListSum(newList, itemCombineList4BackTrace, sum);
		sumWeight = sum[0];
		sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight));
		Assert.assertEquals(dpRecurResult, sumValue);
		Assert.assertEquals(dpRecurResult, dpBackTrace);
		System.out.println("=========================\n");

		// 3.2、回溯限界剪枝求解2
		start = System.nanoTime();
		@SuppressWarnings("unchecked")
		List<Item> newList4BtR = (List<Item>) ((ArrayList<Item>) list).clone();
		BigDecimal dpBackTraceRefactor = dynamicProgrammingBackTrackRefactor(newList4BtR, targetWeight, itemCombineList4BackTrace2);
		end = System.nanoTime();
		System.out.println("counts of BackTraceRefactor circulation: " + count4BackTraceRefactor);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms\n");
		// 校验求得的物品组合中价值总和是否与算法求得的最优解相等
		System.out.println(gson.toJson(itemCombineList4BackTrace2));
		checkListSum(newList4BtR, itemCombineList4BackTrace2, sum);
		sumWeight = sum[0];
		sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight));
		Assert.assertEquals(dpRecurResult, sumValue);
		Assert.assertEquals(dpRecurResult, dpBackTraceRefactor);
		System.out.println("=========================\n");
		
		// 4、分支限界法求解
		start = System.nanoTime();
		@SuppressWarnings("unchecked")
		List<Item> newList4BB = (List<Item>) ((ArrayList<Item>) list).clone();
		BigDecimal dpBranchBound = dynamicProgrammingBranchBound(newList4BB, targetWeight, itemCombineList4BranchBound);
		end = System.nanoTime();
		System.out.println("counts of BranchBound circulation: " + count4BranchBound);
		System.out.println("counts of LcNode.instance: " + LcNode.instanceCount);
		System.out.println("cost time: " + new BigDecimal(end - start).divide(BigDecimal.valueOf(1000000.0)) + "ms\n");
		// 校验求得的物品组合中价值总和是否与算法求得的最优解相等
		System.out.println(gson.toJson(itemCombineList4BranchBound));
		checkListSum(newList4BB, itemCombineList4BranchBound, sum);
		sumWeight = sum[0];
		sumValue = sum[1];
		Assert.assertThat(sumWeight, Matchers.lessThanOrEqualTo(targetWeight));
		Assert.assertEquals(dpRecurResult, sumValue);
		Assert.assertEquals(dpRecurResult, dpBranchBound);
	}

	/**
	 * 校验正确性，用于校验最后求得的物品组合的价值总和是否等于所求解价值
	 * 
	 * @param list 物品列表--若被排序过则要是排序后的列表
	 * @param itemCombineList 算法所求得的物品组合列表
	 * @param sum 保存求和结果的数组，第一个元素是重量的求和，第二个元素是价值的求和
	 */
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
	 * 自顶向下动态规划求解0/1背包的问题，同时还求出最优解的一个组合。<br>
	 * <b>自顶向下动态规划求解是状态转移方程的直观实现，类似于倒推法。
	 * 但是即使采用了备忘（缓存）的方式避免了重复子问题的计算，效率仍然较差，因为缺少“智能”。</b><br>
	 * 状态转移方程：dp(n, tw) = max{ dp(n-1, tw),  v[n]+dp(n-1, tw-w[n]) }，
	 * n为问题层级，tw为目标重量，v[n]和w[n]是n号物品的价值和重量。<br>
	 * <b>算法描述：</b><br>
	 * 1、以倒推法思考，则将初始问题层级为最高层级，即最终结果是最高层级的最优解。<br>
	 * 2、由状态转移方程可知，每一层级的最优解，都是下一层级在该层级“选”和“不选”的这两种状态下value最大的那个。
	 * 若“不选”，则为value和targetWeight都不变的状态；若“选入”，则为value增加而targetWeight减少的状态。<br>
	 * 3、当考察的问题层级到了第1层之下时直接返回0，因为最低问题层级为第1层（层级号为0），并不存在第1层之下（层级号为-1）的问题。<br>
	 * 4、每次计算时先访问缓存，若缓存中没有对应数据才开始计算。由状态转移方程可知，缓存的key为(n, tw)，即问题层级和目标重量的二元组。
	 * 
	 * @param itemIndex 问题层级（其实也是所考察物品的编号）
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4Recursion 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值<br>
	 * 但是其实还有一个隐含的、通过引用传递最优解集的一个返回值-&gt;itemCombineList，<br>
	 * 工程中最好不要这么玩，就算真要这么做，注释一定要有三层厚！
	 */
	public BigDecimal dynamicProgrammingRecursion(int itemIndex, List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4Recursion) {
		if (itemIndex == -1) { // 递归至最底层时，直接返回0
			return BigDecimal.ZERO;
		}
		BigDecimal result;
		// 访问缓存，cacheKey = (itemIndex, targetWeight)
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
			// 虽然不选，但是要把下层钩上来的路径信息继续传递给上层，容易忽略！XXX 这里曾发生过由于itemCombineList的引用传递而导致的BUG！
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
		cache4Recursion.put(cacheKey, new Pair<BigDecimal, List<Integer>>(result, resultList)); // XXX 不能把itemCombineList给存入缓存了，这是要返回给上一层的！该存入缓存的是resultList。
		return result;
	}

	/**
	 * 自底向上动态规划求解0/1背包的问题，同时还求出最优解的所有组合。<br>
	 * 动态规划的核心，还是在于列出状态转移方程。自底向上只不过是从底层开始逐步求解高层状态转移方程的结果。<br>
	 * <b>自底向上动态规划求解，还是以序偶的形式能更容易理解，这是对暴力求解的过程进行优选所得到的结果。</b><br>
	 * <b>算法描述：</b><br>
	 * 1、暴力求解：从第一个物品开始向后依次计算，每次计算都是在前一次计算结果（选或不选的计算结果）的基础上进行计算，并将结果存入列表。
	 * 每次都遍历前次结果，对每种结果都进行（选或不选）的计算并同样将结果存入列表，
	 * 因此列表长度按2、4、8、16的2^n指数形式增长，如果出现超出targetWeight的情况则长度会稍稍缩短。当然，最终一定能求得解。<br>
	 * 2、序偶优选：有若干个物品，我们不妨设前3个物品重量分别为1、2、3，若(1, 1, 0)即前两个都选第三个不选，重量为3，若(0, 0, 1)即前两个不选第三个选入，重量也为3。
	 * 如果targetWeight &gt; 3，此时我们得到了两个重量为3的结果，根据最优性原理，当将第4个物品纳入考虑时，在遍历到重量为3的前次计算结果时，我们只需取价值最大的那个情况。
	 * 因此，我们在每轮计算时应进行<i><b>优选</b></i>，计算结果的weight值不存在则直接放入集合，若存在则比较，集合中只保存该weight下value最大的结果。<br>
	 * 3、获取结果：从最终的集合中，找到value最大的序偶，即为最终结果。
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4Circulation 当前规模最优解的所有组合。<b>在这里并没有什么用！最优解的所有组合在institutionMap中！</b>
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
			// 重量, Pair(价值, 达成该重量的组合的列表)
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
				// 比如3和5号物品一模一样，设其重量为a。则在考察5号物品时，遍历到对应重量为0的前次情形，达到重量a就有两个组合即选3或选5，
				// 则在对应的listOfList对象中新增一个list，list含有一个5。于是当遍历到对应重量为a的前次情形时，达到重量为2a在物理上就有两个组合即(3,5)和(5,5)
				// 显然逻辑上(5,5)是不正确的，因此我们要检查这种情况并予以排除，这也就是要判断tempList.getLast() != i的原因。
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
	 * <b>算法描述：</b><br>
	 * 1、排序，单位重量价值高的排前面，单位价值相同的，重量小的排前面。<br>
	 * 2、进行深度优先装入时，若探索到了叶节点得到一个可行解，则该解的value可以成为<b>界</b>。<br>
	 * 3、进行深度优先装入时，若遇到要回溯时，判断回溯点的解<b>上限</b>（即该节点的贪心解），
	 * 若上限比当前的界还小，则该回溯点无需再深入，可以直接回溯到该回溯点的上一个回溯点（即继续向上回溯），以减少不必要的深入探索。<br>
	 * 4、直到回溯到根节点时，得到最终的解。<br>
	 * <b><i>注意厘清上限和上界在该算法描述中的概念区别！</i></b>
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4BackTrack 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingBackTrack(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4BackTrack) {
		Collections.sort(itemList, new Comparator<Item>() { // XXX itemList最好是一个新的对象，以免影响其他的方案测试

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
		// currentIndex为当前考察的物品编号，itemAmount为物品数量
		int currentIndex = 0, itemAmount = itemList.size();
		LinkedList<Integer> stackList = new LinkedList<Integer>(); // 用于回溯的从尾部添加物品编号的栈列表
		while (true) { // 循环进行深度搜索，因为在checkBound与resultValue比较时，若已经回溯到头就return，作为算法的出口
			while (currentIndex < itemAmount) { // 向下深度搜索可行解
				count4BackTrace++;
				Item currentItem = itemList.get(currentIndex);
				BigDecimal itemWeight = currentItem.getWeight();
				BigDecimal itemValue = currentItem.getValue();
				if (currentWeight.add(itemWeight).compareTo(targetWeight) <= 0) { // 若选入当前物品没有爆表，则累加重量和价值，并将编号入栈
					currentWeight = currentWeight.add(itemWeight);
					currentValue = currentValue.add(itemValue);
					stackList.add(currentIndex++); // 入栈后编号递增
				} else { // 爆表了，则中断累加循环，当前编号为爆表物品的编号
					break;
				}
			}
			// 若是因为已经访问了所有的元素而退出循环，则将该结果更新为最终结果，并作为界，同时将栈列表保存为解组合
			if (currentIndex >= itemAmount) {
				resultValue = currentValue;
				itemCombineList4BackTrack.clear();
				itemCombineList4BackTrack.addAll(stackList);
			}
			/*
			 * 若不选当前物品时，计算出的上限值不大于当前的上界值，说明上一个物品（即最后一个被选入的物品）的选入也不能取得更好的结果，
			 * 于是在解集中移除上一个物品（我个人倾向称之为回溯掉上一个物品，则上一个物品称为回溯点，并进入不选该物品的分支）。
			 * 已然回溯，则再一次判断不选被回溯物品时的上限值。
			 * 若大于上界值，说明可能存在更优的可行解，结束循环，进入下次深度搜索。
			 * 若不大于上界值，说明不存在更优的可行解（虽然可能存在相同的可行解），则继续回溯，并判断回溯后的上限值与上界值的大小。
			 */
			while (checkBound(currentWeight, currentValue, currentIndex, targetWeight, itemList).compareTo(resultValue) <= 0) {
				int stackSize = stackList.size();
				if (stackSize == 0) {
					return resultValue; // 算法在此结束
				}
				currentIndex = stackList.removeLast();
				Item item = itemList.get(currentIndex);
				currentWeight = currentWeight.subtract(item.getWeight());
				currentValue = currentValue.subtract(item.getValue());
			}
			currentIndex++; // 循环结束则可得到最后被回溯的物品编号，自增以跳过该物品，进入下一次深度搜索。
		}
	}

	/**
	 * 基于当前的重量和价值，以及当前物品的编号，计算出在不选择当前物品时的上限值。<br>
	 * 此时继续向后选入物品，计算出直到没有物品可选或重量爆表时的价值（爆表时计算出贪心解），作为上限值。<br>
	 * 该上限值相对精确，但计算代价会稍稍增大。
	 * 
	 * @param currentWeight 当前已选入物品的重量总和
	 * @param currentValue 当前已选入物品的价值总和
	 * @param itemIndex 物品编号--不希望被选入的物品的编号
	 * @param targetWeight 目标重量
	 * @param itemList 物品列表
	 * @return 上限值
	 */
	private BigDecimal checkBound(BigDecimal currentWeight, BigDecimal currentValue, int itemIndex, BigDecimal targetWeight, List<Item> itemList) {
		for (int i = itemIndex + 1; i < itemList.size(); i++) {
			count4BackTrace++;
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
	 * 回溯法配合限界函数剪枝求解0/1背包的问题。<b><i>一个重构的写法</i></b>。<br>
	 * <b>核心还是在于限界函数的设计，好的限界函数有利于剪枝。<i>限界函数的设计思路还有待进一步研究</i>。</b><br>
	 * <b>算法描述：</b><br>
	 * 1、排序，单位重量价值高的排前面，单位价值相同的，重量小的排前面。<br>
	 * 2、进行深度优先装入时，若探索到了叶节点得到一个可行解，则该解的value可以成为<b>界</b>。<br>
	 * 3、进行深度优先装入时，若遇到要回溯时，判断回溯点的解<b>上限</b>（即该节点的贪心解），
	 * 若上限比当前的界还小，则该回溯点无需再深入，可以直接回溯到该回溯点的上一个回溯点（即继续向上回溯），以减少不必要的深入探索。<br>
	 * 4、直到回溯到根节点时，得到最终的解。<br>
	 * <b><i>注意厘清上限和上界在该算法描述中的概念区别！</i></b>
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4BackTrackRefactor 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingBackTrackRefactor(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4BackTrackRefactor) {
		Collections.sort(itemList, new Comparator<Item>() { // XXX itemList最好是一个新的对象，以免影响其他的方案测试

			@Override
			public int compare(Item o1, Item o2) {
				count4BackTraceRefactor++;
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
		LinkedList<Integer> stackList = new LinkedList<Integer>(); // 用于回溯的从尾部添加物品编号的栈列表
		// 越点与当前物品编号。由于物品编号的初始值=越点+1，故越点初始化为-1。XXX 越点也可以理解为“被回溯的点”、最近的一个不适合的点。
		int skipPoint = -1, currentIndex = 0;
		while (true) { // 循环进行深度搜索，当栈列表为空即回溯到头时算法结束，return退出
			while (true) { // 循环判断上限值是否比界大
				BigDecimal boundValue = currentValue; // 上限值。上界值不可能比当前价值小，故初始化为当前价值。
				// 向下深度搜索可行解，同时也求得一个较粗糙的上限值
				for (currentIndex = skipPoint + 1; currentIndex < itemAmount; currentIndex++) {
					count4BackTraceRefactor++;
					Item currentItem = itemList.get(currentIndex);
					BigDecimal itemWeight = currentItem.getWeight();
					BigDecimal itemValue = currentItem.getValue();
					BigDecimal tmpWeight = currentWeight.add(itemWeight); // 重量累加
					if (tmpWeight.compareTo(targetWeight) > 0) { // 选入当前物品超重，则计算对于该物品的贪心解作为上限值
						boundValue = targetWeight.subtract(currentWeight).multiply(itemValue.divide(itemWeight, 3, BigDecimal.ROUND_HALF_UP)).add(currentValue);
						break;
					}
					currentWeight = tmpWeight;
					boundValue = currentValue = currentValue.add(itemValue); // 价值累加。能走到这一步说明没有超重，同时记录为一个上限值
					stackList.add(currentIndex); // 当前物品入栈
				}
				// 向下搜索结束了，判断上限值与上界的大小（大多数是因为爆表了结束搜索，少部分是因为搜索到头而结束搜索）
				if (boundValue.compareTo(resultValue) <= 0) {
					// 求得的上限不大于上界，说明越点之前的那个物品的选入是无法得到更优解的，越点之前的那个物品需要被回溯，并成为新的越点。
					// 由于在同步计算上限值时，辅助栈中可能存了多个越点之后的物品，因此新的越点不一定是辅助栈中最后一个元素，需要判断。XXX 这一点很重要！
					int btPoint = stackList.removeLast();
					while (btPoint >= skipPoint) { // 被回溯的点是越点之后的元素，则还需要往前回溯。
						count4BackTraceRefactor++;
						Item lastItem = itemList.get(btPoint);
						currentWeight = currentWeight.subtract(lastItem.getWeight());
						currentValue = currentValue.subtract(lastItem.getValue());
						btPoint = stackList.removeLast();
					}
					if (stackList.size() == 0) { // 回溯到头了，则算法结束。（即0号元素是越点，且需要回溯到0号元素之前，这种情况意味着已找到最优解）
						return resultValue;
					}
					skipPoint = btPoint; // 得到新的越点，且需要被回溯（对于最后一个物品被选入且小于界，
					Item lastItem = itemList.get(skipPoint);
					currentWeight = currentWeight.subtract(lastItem.getWeight());
					currentValue = currentValue.subtract(lastItem.getValue());
					continue; // 继续判断越过该点后的上限值是否大于上界
				} else { // 上限值比上界大，说明越过该点可能会有更好的可行解
					skipPoint = currentIndex; // 更新越点为当前物品编号
					// 注：越点可能会是最后一个物品的编号加1，此后会设定上界，且下一次必定把辅助栈最后一个元素给回溯了。
					// XXX 若辅助栈最后一个元素又恰好是物品列表的最后一个元素，则还会回溯一次，因为此时boundValue==currentValue==resultValue
					break; // 退出判断的循环，检查是否已经得到可行解了
				}
			}
			if (currentIndex >= itemAmount) { // 若全部物品都遍历过，则说明求得了一个可行解，可以设定为上界。
				resultValue = currentValue;
				itemCombineList4BackTrackRefactor.clear();
				itemCombineList4BackTrackRefactor.addAll(stackList);
			}
		}
	}

	/**
	 * 分支限界法求解0/1背包的问题。
	 * 
	 * <p>分支限界法的一般思路是：根据问题的相关参数初始化一个可扩展节点E，然后生成E的所有可行子节点并放入辅助空间中。
	 * 按照一定规则从辅助空间中取出下一个可扩展节点E'，重复上述操作直到下一个可扩展节点的上限小于全局的下界，或辅助空间中没有元素。
	 * 这里我采用LC分支限界法，即LeastCostBranchAndBound。虽然LC表示按照最低成本的规则，其实在这里可以理解为最大收益的规则。
	 * 这里的最大收益即当前节点状态的贪心解。</p>
	 * 
	 * <b>算法描述：</b><br>
	 * 1、排序，单位重量价值高的排前面，单位价值相同的，重量小的排前面。<br>
	 * 2、初始化一个节点并计算其上下限，以此为根扩展出子节点，将可继续扩展的子节点中加到优先级队列中。<br>
	 * 3、如下子节点是可继续扩展的：(1)重量没有爆表；(2)该节点的上限不小于全局下界；(3)搜索树的非叶节点。<br>
	 * 4、优先级队列的规则是：节点上限大的优先；上限相同的则下限大的优先。<br>
	 * 5、在叶节点处尝试更新全局下界：若该叶节点的下限值不小于全局下界值，则可以更新全局下界值，并将该叶节点赋值给答案节点。<br>
	 * 6、当优先级队列为空，或当取出节点的上限值小于全局下界时，算法结束。
	 * 
	 * @param itemList 原始数据的数组集合
	 * @param targetWeight 目标规模
	 * @param itemCombineList4BranchBound 当前规模最优解的一个组合，<b>也是一个返回值！</b>
	 * @return 返回最优解的值
	 */
	public BigDecimal dynamicProgrammingBranchBound(List<Item> itemList, BigDecimal targetWeight, List<Integer> itemCombineList4BranchBound) {
		Collections.sort(itemList, new Comparator<Item>() { // XXX itemList最好是一个新的对象，以免影响其他的方案测试

			@Override
			public int compare(Item o1, Item o2) {
				count4BackTraceRefactor++;
				int compareResult = o2.getValue().divide(o2.getWeight(), 3, BigDecimal.ROUND_HALF_UP)
						.compareTo(o1.getValue().divide(o1.getWeight(), 3, BigDecimal.ROUND_HALF_UP));
				if (compareResult == 0) {
					return o1.getWeight().compareTo(o2.getWeight());
				}
				return compareResult;
			}
		});
		// JDK直接提供了带有堆操作的队列，可以省点心。但是仍是要弄清其算法！
		PriorityQueue<LcNode> queue = new PriorityQueue<LcNode>(new Comparator<LcNode>() {

			@Override
			public int compare(LcNode o1, LcNode o2) {
				int res = o2.getUpperBound().compareTo(o1.getUpperBound());
				if (res == 0) {
					return o2.getLowerBound().compareTo(o1.getLowerBound());
				}
				return res;
			}
		});

		LcNode firstNode = LcNode.initAsFirstNode(-1, targetWeight, BigDecimal.ZERO, itemList), resultNode = null;
		BigDecimal globalLowerBound = firstNode.getLowerBound();
		queue.offer(firstNode);
		while (!queue.isEmpty()) { // 当优先级队列为空时，算法结束。
			LcNode node = queue.poll();
			if (node.getUpperBound().compareTo(globalLowerBound) < 0) {
				break; // 当取出节点的上限值小于全局下界时，算法结束。
			}
			for (int i =0; i <= 1; i++) { // 生成左右子节点
				count4BranchBound++;
				LcNode childNode = node.genNewNode(i, itemList);
				if (childNode == null || childNode.getUpperBound().compareTo(globalLowerBound) < 0) {
					continue; // 无该子节点或子节点的上限小于全局下界时，直接进入下一次循环。
				}
				if (childNode.getItemIndex() == (itemList.size() - 1)) {
					// 不小于时就更新，是为了解决背包容量足够大时答案节点未赋值的bug。
					if (childNode.getLowerBound().compareTo(globalLowerBound) >= 0) {
						globalLowerBound = childNode.getLowerBound();
						resultNode = childNode;
					}
					continue;
				}
				queue.offer(childNode); // 经过了以上判断，表明这是一个可扩展节点，加入优先级队列中。
			}
		}
		itemCombineList4BranchBound.addAll(resultNode.getTrack());
		return resultNode.getLowerBound(); // 叶节点的下界即为可行解。
	}

	/**
	 * 用于存放在优先级队列中节点类。
	 * 
	 * @author Alieckxie
	 * @date 2019年03月30日
	 *
	 */
	private static class LcNode implements Cloneable {
		
		private LcNode parent; // 父节点
		private int itemIndex; // 当前节点所考察物品的编号
		private boolean selected; // 是否纳入背包
		private BigDecimal upperBound; // 上限值
		private BigDecimal lowerBound; // 下限值
		private BigDecimal targetWeight; // 目标重量
		private BigDecimal currentValue; // 当前已累计价值

		public static int instanceCount = 0;

		public static final boolean SELECTED = true;
		public static final boolean NON_SELECTED = false;
		public static final int LEFT_CHILD = 0;
		public static final int RIGHT_CHILD = 1;
		
		private LcNode() {instanceCount++;}

		/**
		 * 根据相关参数，初始化放入优先级队列的第一个节点。
		 * <p>设计为静态方法，是为了更好地提现初始化的含义。</p>
		 * 
		 * @param itemIndex 用于初始化的物品编号。如果是要从第0号开始考察，则初始化编号填-1。也可理解为节点在解空间树中的层级。
		 * @param targetWeight 目标重量
		 * @param currentValue 当前累计价值
		 * @param itemList 物品列表
		 * @return 初始化后的节点
		 */
		public static LcNode initAsFirstNode(int itemIndex, BigDecimal targetWeight, BigDecimal currentValue, List<Item> itemList) {
			LcNode node = new LcNode();
			node.itemIndex = itemIndex;
			node.targetWeight = targetWeight;
			node.currentValue = currentValue;
			node.calcBound(itemList);
			return node;
		}

		/**
		 * 计算该节点的上下限值。
		 * 
		 * <p>设计为私有方法是因为有大量直接访问私有成员变量的操作。</p>
		 * 
		 * @param itemList 物品列表
		 */
		private void calcBound(List<Item> itemList) {
			BigDecimal tw = targetWeight; // 当前节点目标规模不应该被修改，故赋值到临时变量进行操作。
			lowerBound = currentValue; // 当前节点累计价值不应该被修改，故赋值到下限值进行累加。
			for (int i = itemIndex + 1; i < itemList.size(); i++) {
				count4BranchBound++;
				Item item = itemList.get(i);
				if (tw.compareTo(item.getWeight()) >= 0) {
					tw = tw.subtract(item.getWeight());
					upperBound = lowerBound = lowerBound.add(item.getValue()); // 上下限同步赋值，以避免背包容量足够大时上限变量未赋值的bug。
				} else {
					upperBound = lowerBound.add(tw.multiply(item.getValue().divide(item.getWeight(), 3, BigDecimal.ROUND_HALF_UP)));
					for (int j = i + 1; j < itemList.size(); j++) { // 一直找到可行解为止，作为下界
						count4BranchBound++;
						Item restItem = itemList.get(j);
						if (tw.compareTo(restItem.getWeight()) >= 0) {
							tw = tw.subtract(restItem.getWeight());
							lowerBound = lowerBound.add(restItem.getValue());
						}
					}
					return;
				}
			}
		}

		/**
		 * 根据子节点标记和物品列表，尝试生成当前节点的某个子节点。
		 * 
		 * <p>左孩子表示选入，故其上下限值与父节点相同，只需修改累计价值和目标重量。
		 * 右孩子表示不选入，故需根据物品列表重新计算上下限值。</p>
		 * 
		 * @param childSign 子节点的左右标记。0为生成左孩子，1为生成右孩子。
		 * @param itemList 物品列表
		 * @return 子节点对象。若无法生成该子节点对象则返回null。
		 */
		public LcNode genNewNode(int childSign, List<Item> itemList) {
			if (childSign == LEFT_CHILD && targetWeight.compareTo(itemList.get(itemIndex + 1).getWeight()) < 0) {
				return null; // 只有左孩子有爆表的可能
			}
			LcNode cloneNode = null;
			try {
				cloneNode = (LcNode) this.clone();
				instanceCount++;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
			cloneNode.parent = this;
			cloneNode.itemIndex++;
			Item item = itemList.get(cloneNode.itemIndex);
			if (childSign == RIGHT_CHILD) {
				cloneNode.selected = NON_SELECTED;
				cloneNode.calcBound(itemList);
			} else {
				cloneNode.currentValue = cloneNode.currentValue.add(item.getValue());
				cloneNode.targetWeight = cloneNode.targetWeight.subtract(item.getWeight());
				cloneNode.selected = SELECTED;
			}
			return cloneNode;
		}

		/**
		 * 从当前节点获取一条追溯到根节点的物品选入组合。
		 * 
		 * @return 选入组合的列表
		 */
		public List<Integer> getTrack() {
			LinkedList<Integer> list = new LinkedList<Integer>();
			LcNode lcNode = this;
			while (lcNode.parent != null) {
				if (lcNode.selected) {
					list.addFirst(lcNode.itemIndex);
				}
				lcNode = lcNode.parent;
			}
			return list;
		}

		public int getItemIndex() {
			return itemIndex;
		}

		public BigDecimal getUpperBound() {
			return upperBound;
		}

		public BigDecimal getLowerBound() {
			return lowerBound;
		}

	}

	List<Item> getItemList() {
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
	
	List<Item> getItemListAllInteger() {
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
	
	List<Item> getItemListBackTrackSample() {
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
	
	List<Item> getItemListBranchBoundSample() {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item(2, 10));
		list.add(new Item(4, 10));
		list.add(new Item(6, 12));
		list.add(new Item(9, 18));
		return list;
	}

	/**
	 * 
	 * @author Alieckxie
	 *
	 */
	private static class Item {

		BigDecimal weight;
		BigDecimal value;

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

		public BigDecimal getWeight() {
			return weight;
		}

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

	/**
	 * 自定义的序偶类（也可以称为二元组），以适应hashMap的相关操作。
	 * 
	 * @author Alieckxie
	 *
	 * @param <X> 第一个元素
	 * @param <Y> 第二个元素
	 */
	private static class Pair<X, Y> {

		X x;
		Y y;

		public Pair(X x, Y y) {
			super();
			this.x = x;
			this.y = y;
		}

		public X getX() {
			return x;
		}

		public Y getY() {
			return y;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<X, Y> other = (Pair<X, Y>) obj;
			if (y == null) {
				if (other.y != null)
					return false;
			} else if (!y.equals(other.y))
				return false;
			if (x == null) {
				if (other.x != null)
					return false;
			} else if (!x.equals(other.x))
				return false;
			return true;
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

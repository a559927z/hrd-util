package net.chinahrd.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang3.StringUtils;

/**
 * Collections工具集. Collection Factory 方便集合构建的工厂类
 * <p>
 * 省略泛型new的过程，同时在可能的情况下 尽可能使用更优越的实现类
 * 
 */
public class CollectionKit {

	/**
	 * 构建一个通用的 {@link HashMap} .
	 */
	public static <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> MapList<K, V> newMapList() {
		return new MapList<K, V>();
	}

	/**
	 * Constructs and returns a generic {@link java.util.HashSet} creating.
	 */
	public static <T> Set<T> newSet() {
		return new HashSet<T>();
	}

	/**
	 * Contructs a new {@link HashSet} and initializes it using the provided
	 * collection.
	 */
	public static <T, V extends T> Set<T> newSet(Collection<V> values) {
		return new HashSet<T>(values);
	}

	public static <T, V extends T> Set<T> newSet(V... values) {
		// Was a call to newSet(), but Sun JDK can't handle that. Fucking generics.
		return new HashSet<T>(Arrays.asList(values));
	}

	/**
	 * Constructs a new {@link java.util.HashMap} creating by copying an existing
	 * Map creating.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> Map<K, V> newMap(Map<? extends K, ? extends V> map) {
		Map ret = new HashMap<K, V>(map.size());
		ret.putAll(map);
		return ret;
	}

	/**
	 * Constructs a new concurrent map, which is safe to access via multiple
	 * threads.
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * Contructs and returns a new generic {@link java.util.ArrayList} creating.
	 */
	public static <T> List<T> newList() {
		return new ArrayList<T>();
	}

	/**
	 * Creates a new, fully modifiable list from an initial set of elements.
	 */
	public static <T, V extends T> List<T> newList(V... elements) {
		// Was call to newList(), but Sun JDK can't handle that.
		return new ArrayList<T>(Arrays.asList(elements));
	}

	/**
	 * Useful for queues.
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}

	/**
	 * Constructs and returns a new {@link java.util.ArrayList} as a copy of the
	 * provided collection.
	 */
	public static <T, V extends T> List<T> newList(Collection<V> list) {
		return new ArrayList<T>(list);
	}

	/**
	 * Constructs and returns a new
	 * {@link java.util.concurrent.CopyOnWriteArrayList}.
	 */
	public static <T> List<T> newThreadSafeList() {
		return new CopyOnWriteArrayList<T>();
	}

	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param keyPropertyName
	 *            要提取为Map中的Key值的属性名.
	 * @param valuePropertyName
	 *            要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName,
			final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 */
	public static <T> List<T> extractToList(final Collection collection, final String propertyName) {
		List<T> list = new ArrayList<T>(collection.size());

		try {
			for (Object obj : collection) {
				list.add((T) PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 判断集合对象中是否有指定属性值 by jxzhang on 2017-12-04
	 * 
	 * @param collection
	 *            来源集合
	 * @param propertyName
	 *            指定属性
	 * @param propertyValue
	 *            指定值
	 * @param separator
	 * @return
	 */
	public static boolean containsToKey(final Collection collection, final String propertyName,
			final Object propertyValue) {
		try {
			for (Object obj : collection) {
				Object simpleProperty = PropertyUtils.getSimpleProperty(obj, propertyName);
				if (simpleProperty != null) {
					System.out.println(simpleProperty);
					if (simpleProperty instanceof String) {
						if (simpleProperty.equals(propertyValue)) {
							return true;
						}
					} else {
						if (simpleProperty == propertyValue) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}
		return false;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String,
	 * 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 将以逗号分隔的字符串转换成集合
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str) {
		return strToList(str, ",");
	}

	/**
	 * 将以逗号分隔的字符串转换成集合
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str, String separator) {
		if (StringUtils.isEmpty(str)) {
			return Collections.emptyList();
		}
		return Arrays.asList(StringUtils.split(str, separator));
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Map map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null) && !(collection.isEmpty());
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}

	/**
	 * Map按值排序 by jxzhang on 2016-02-26
	 * 
	 * @param oriMap
	 * @return
	 */
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		if (oriMap != null && !oriMap.isEmpty()) {
			// 先将待排序oriMap中的所有元素置于一个列表中
			List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(oriMap.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
					int value1 = 0, value2 = 0;
					try {
						value1 = entry1.getValue();
						value2 = entry2.getValue();
					} catch (NumberFormatException e) {
						value1 = 0;
						value2 = 0;
					}
					return value2 - value1;
				}
			});

			Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
			Map.Entry<String, Integer> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}

	/**
	 * 拆分集合（指定有多少个集合） by jxzhang on 2016-03-06
	 * 
	 * @param <T>
	 * @param list
	 *            原集合
	 * @param itemSize
	 *            拆分集合各个拆合大小
	 * 
	 *            list.size = 10 itemSize = 3 <br/>
	 *            结果： <br/>
	 *            Map<1, 3.size()> <br/>
	 *            Map<2, 3.size()> <br/>
	 *            Map<3, 3.size()> <br/>
	 *            Map<4, 1.size()> <br/>
	 * 
	 * @return
	 */
	public static <T> Map<Integer, List<T>> splitList(List<T> list, int itemSize) {
		Map<Integer, List<T>> rsMap = new LinkedHashMap<Integer, List<T>>();

		int count = list.size() / itemSize;
		int yu = list.size() % itemSize;
		int last = itemSize - 1;

		for (int i = 0; i < itemSize; i++) {
			List<T> subList = new ArrayList<T>();
			// 最后一次把乘下的（余）加上去
			if (i == last) {
				subList = list.subList(i * count, count * (i + 1) + yu);
			} else {
				subList = list.subList(i * count, count * (i + 1));
			}
			rsMap.put(i, subList);
		}
		return rsMap;
	}

	/**
	 * 拆分集合（指定每集合大少） by jxzhang on 2016-03-21
	 * 
	 * @param list
	 * @param sizeMax
	 * @return
	 */
	public static <T> Map<Integer, List<T>> splitList2(List<T> list, int sizeMax) {
		Map<Integer, List<T>> rsMap = new LinkedHashMap<Integer, List<T>>();

		int count = list.size();
		int arrSize = count % sizeMax == 0 ? count / sizeMax : count / sizeMax + 1;

		List<T> subList = CollectionKit.newList();

		int startIndex = 0;
		int endIndex = sizeMax;

		for (int i = 1; i <= arrSize; i++) {
			if (i == arrSize) {
				subList = list.subList(startIndex, count);
			} else {
				subList = list.subList(startIndex, endIndex);
				startIndex = endIndex;
				endIndex = startIndex + sizeMax;
			}
			rsMap.put(i, subList);
		}

		return rsMap;
	}

	/**
	 * 拆分集合
	 * 
	 * @param <T>
	 * @param resList
	 *            要拆分的集合
	 * @param count
	 *            每个集合的元素个数
	 * @return 返回拆分后的各个集合
	 */
	public static <T> List<List<T>> splitList3(List<T> resList, int count) {

		if (resList == null || count < 1)
			return null;
		List<List<T>> ret = new ArrayList<List<T>>();
		int size = resList.size();
		if (size <= count) { // 数据量不足count指定的大小
			ret.add(resList);
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				List<T> itemList = new ArrayList<T>();
				for (int j = 0; j < count; j++) {
					itemList.add(resList.get(i * count + j));
				}
				ret.add(itemList);
			}
			// last的进行处理
			if (last > 0) {
				List<T> itemList = new ArrayList<T>();
				for (int i = 0; i < last; i++) {
					itemList.add(resList.get(pre * count + i));
				}
				ret.add(itemList);
			}
		}
		return ret;

	}

	/**
	 * 集合转数组 by jxzhang on 2016-12-18
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] listToStrArr(List<String> obj) {
		String[] strArr = new String[obj.size()];
		for (int i = 0; i < obj.size(); i++) {
			strArr[i] = obj.get(i);
		}
		return strArr;
	};

	/**
	 * List 元素的多个属性进行排序。例如 ListSorter.sort(list, "name", "age")，则先按 name 属性排序，name
	 * 相同的元素按 age 属性排序。
	 *
	 * @param list
	 *            包含要排序元素的 List
	 * @param properties
	 *            要排序的属性。前面的值优先级高。
	 * @return list 已排好序list
	 */
	public static <V> List<V> sortMultiProper(List<V> list, final String... properties) {
		Collections.sort(list, new Comparator<V>() {
			@Override
			public int compare(V o1, V o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;

				for (String property : properties) {
					Comparator c = new BeanComparator(property);
					int result = c.compare(o1, o2);
					if (result != 0) {
						return result;
					}
				}
				return 0;
			}
		});
		return list;
	}

	/**
	 * @describe 依据某个字段对集合进行排序
	 * @author jxzhang
	 * @date 2017-1-22 下午3:44:47
	 * @param list
	 *            待排序的集合
	 * @param fieldName
	 *            依据这个字段进行排序
	 * @param asc
	 *            如果为true，是正序；为false，为倒序
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> sort(List<T> list, String fieldName, boolean asc) {
		Comparator<?> mycmp = ComparableComparator.getInstance();
		mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
		if (!asc) {
			mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
		}
		Collections.sort(list, new BeanComparator(fieldName, mycmp));
		return list;
	}

}

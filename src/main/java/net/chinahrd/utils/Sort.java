/**
*net.chinahrd.utils
*/
package net.chinahrd.utils;

import java.util.List;


/**用于对对象进行排序
 * @author htpeng
 *2016年4月7日下午1:26:35
 */
public class Sort<T extends Comparable<T>> {
	
	/**
	 * 降序
	 * @param list
	 * @return
	 */
	public List<T> desc(List<T> list) {
		List<T> result=CollectionKit.newList();
		for(T t:list){
			int index=result.size();
			if(result.size()>0){
				for(int i=result.size()-1;i>=0;i--){
					if(t.compareTo(result.get(i))>0){
						index=i;
					}else{
						break;
					}
				}
			}
			result.add(index,t);
		}
		return result;
	}
	/**
	 * 升序
	 * @param list
	 * @return
	 */
	public List<T> asc(List<T> list) {
		List<T> result=CollectionKit.newList();
		for(T t:list){
			int index=result.size();
			if(result.size()>0){
				for(int i=result.size()-1;i>=0;i--){
					if(t.compareTo(result.get(i))<0){
						index=i;
					}else{
						break;
					}
				}
			}
			result.add(index,t);
			
		}
		return result;
//		int start = 0, len = list.size() - 1, end = len;
//		while (start < end) {
//			DismissTrendDto startDto = list.get(start);
//			
//			int bool = 0;
//			int n = start;
//			int index = -1;
//			if (start == 0 && end == len) {
//				DismissTrendDto endDto = list.get(end);
//				if (startDto.getRate() < endDto.getRate()) {
//					list.remove(start);
//					list.add(start, endDto);
//					list.remove(end);
//					list.add(end, startDto);
//				}
//				start++;
//				end--;
//			}else{
//				while (--n >= 0) {
//					if (startDto.getRate() <= list.get(n).getRate()) {
//						break;
//					} else{
//						index = n;
//						bool = 1;
//					}
//				}
//				int m = end;
//				if (index> -1) {
//					while (++m <= len) {
//						if (startDto.getRate() >= list.get(m).getRate()) {
//							break;
//						}else {
//							index = m;
//							bool = 2;
//						}
//					}
//				}
//				
//				 if (index > -1) {
//					DismissTrendDto indexDto = list.get(index);
//					list.remove(start);
//					list.add(start, indexDto);
//					list.remove(index);
//					list.add(index, startDto);
//					if (bool == 1) {
//						start++;
//					} else if (bool == 2) {
//						end--;
//					}
//				}else{
//					start++;
//				}
//			}
//		}

	}
}

package net.chinahrd.utils;

import java.util.Comparator;

/**
 * Java 对象排序工具
 * Created by wqcai on 2016/1/27 027.
 */
public class CompareUtil {
    //sort 1正序 -1 倒序  filed 多字段排序
    public static <t> Comparator createComparator(int sort, String... filed) {
        return new ImComparator(sort, filed);
    }

    public static class ImComparator implements Comparator {
        int sort = 1;
        String[] filed;

        public ImComparator(int sort, String... filed) {
            this.sort = sort == -1 ? -1 : 1;
            this.filed = filed;
        }

        @Override
        public int compare(Object o1, Object o2) {
            int result = 0;
            for (String file : filed) {
                Object value1 = ReflexUtil.invokeMethod(file, o1);
                Object value2 = ReflexUtil.invokeMethod(file, o2);
                if (value1 == null || value2 == null) {
                    continue;
                }
                if (!(value1 instanceof Integer) || !(value1 instanceof Integer)) {
                    continue;
                }
                int v1 = Integer.valueOf(value1.toString());
                int v2 = Integer.valueOf(value2.toString());
                if (v1 == v2) continue;
                if (sort == 1) {
                    return v1 - v2;
                } else if (sort == -1) {
                    return v2 - v1;
                } else {
                    continue;
                }
            }
            return result;
        }
    }
}

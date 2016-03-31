package com.luohe.android.luohe.utils;

import java.util.List;

/**
 * Desc   : Simple Class Description
 */
public class ListUtils {

    /**
     * 集合是否为空
     *
     * @param list
     * @param <T>
     * @return true 是空集合 ; false 有数据
     */
    public static <T> boolean isEmpty(List<T> list) {
        return null == list || list.size() <= 0;
    }
}

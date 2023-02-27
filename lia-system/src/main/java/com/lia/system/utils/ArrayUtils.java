package com.lia.system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArrayUtils {

    public static <T> ArrayList<T> asList(T... items) {
        ArrayList<T> list = new ArrayList<T>();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }

    public static <T> ArrayList<T> asList(Set<T> set){
        ArrayList<T> list = new ArrayList<T>();
        for (T t : set) {
            list.add(t);
        }
        return list;
    }

}

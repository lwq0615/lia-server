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

}

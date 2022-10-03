package com.lia.system.utils;

import java.util.ArrayList;

public class ArrayUtils {

    public static <T> ArrayList<T> asList(T... items) {
        ArrayList list = new ArrayList();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }

}

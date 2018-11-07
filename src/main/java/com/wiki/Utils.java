package com.wiki;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    public static <T> Set<T> setOf(T... elements) {
        return Arrays.stream(elements).collect(Collectors.toSet());
    }

    public static <T> List<T> listOf(T... elements) {
        return Arrays.stream(elements).collect(Collectors.toList());
    }
}

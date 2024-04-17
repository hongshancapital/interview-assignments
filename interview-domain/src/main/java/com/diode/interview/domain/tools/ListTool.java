package com.diode.interview.domain.tools;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
public class ListTool {
    private ListTool() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> List<T> safeList(List<T> unSafeList) {
        return Optional.ofNullable(unSafeList)
                .map(e -> e.stream().filter(Objects::nonNull).collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}

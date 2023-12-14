package com.example.cinema.parse;

import java.util.List;
import java.util.stream.Collectors;

public class Parse {
    public static List<Long> convertStringListToLongList(List<String> stringList) {
        return stringList.stream()
                .map(Long::parseLong) // 각 String을 Long으로 변환
                .collect(Collectors.toList());
    }

}

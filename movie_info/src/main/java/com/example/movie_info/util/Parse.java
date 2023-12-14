package com.example.movie_info.util;

import com.example.movie_info.entity.MovieInfoEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class Parse {
    private static final String url = "lb://GATEWAY-SERVER";

    public static String finalListUrl(String target, List<?> list){
        String queryString = list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url+target);
        builder.queryParam("list", queryString);
        String finalUrl = builder.build().toUriString();

        return finalUrl;
    }

    public static String finalStringUrl(String target, String ids){


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url+target);
        builder.queryParam("id", ids);
        String finalUrl = builder.build().toUriString();

        return finalUrl;
    }
}

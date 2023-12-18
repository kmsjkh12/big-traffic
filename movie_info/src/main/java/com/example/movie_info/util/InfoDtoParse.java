package com.example.movie_info.util;

import com.example.movie_info.dto.MovieInfoDto;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfoDtoParse {

    public static List<MovieInfoDto> mergeRemoveDuplicates(List<Date> reserve, List<Date> notReserve){

        //예매할 수 있는 정보
        List<MovieInfoDto> reserveDtos = reserve.stream().map(
                (v)-> new MovieInfoDto(v, true)
        ).collect(Collectors.toList());

        //예매 불가능한 정
        List<MovieInfoDto> notReserveDtos = notReserve.stream().map(
                (v)-> new MovieInfoDto(v, false)
        ).collect(Collectors.toList());


        List<MovieInfoDto> mergedList = notReserveDtos.stream()
                .filter(dto -> !reserve.contains(dto.getMiday()))
                .collect(Collectors.toList());

        return Stream.concat(reserveDtos.stream(), mergedList.stream())
                .sorted(Comparator.comparing(MovieInfoDto::getMiday)) // miday를 기준으로 오름차순 정렬
                .collect(Collectors.toList());
    }




}

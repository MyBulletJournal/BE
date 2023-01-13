package com.hanghae.bulletbox.diary.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseShowDailyByCategoryDto {

    private List<DailyDto> dailyDtoList;

    private ResponseShowDailyByCategoryDto(List<DailyDto> dailyDtoList) {
        this.dailyDtoList = dailyDtoList;
    }

    public static ResponseShowDailyByCategoryDto toResponseShowDailyByCategoryDto(List<DailyDto> dailyDtoList) {
        return new ResponseShowDailyByCategoryDto(dailyDtoList);
    }
}

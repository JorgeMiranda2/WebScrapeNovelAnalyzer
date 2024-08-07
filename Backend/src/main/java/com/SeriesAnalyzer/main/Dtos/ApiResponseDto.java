package com.SeriesAnalyzer.main.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseDto {
    private boolean success;
    private String message;
    private Object data;
}

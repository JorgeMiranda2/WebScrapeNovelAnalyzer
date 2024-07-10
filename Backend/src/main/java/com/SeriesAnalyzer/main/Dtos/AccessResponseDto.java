package com.SeriesAnalyzer.main.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccessResponseDto {
    private String token;
    private String username;
}

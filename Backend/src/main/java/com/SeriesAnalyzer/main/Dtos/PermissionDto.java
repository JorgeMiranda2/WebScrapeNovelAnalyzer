package com.SeriesAnalyzer.main.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PermissionDto {
    private String method;
    private String route;
    private Long roleId;
    private Long stateId;
}

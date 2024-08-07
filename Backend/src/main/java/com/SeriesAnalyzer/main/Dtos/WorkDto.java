package com.SeriesAnalyzer.main.Dtos;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




@Builder
@Getter
@Setter
public class WorkDto {
    private Long id;
    private String title;
    private String image;
    private String workType;


    public WorkDto(Long id, String title, String image, String workType) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.workType = workType;
    }

}

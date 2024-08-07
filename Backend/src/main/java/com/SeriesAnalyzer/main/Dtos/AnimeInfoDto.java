package com.SeriesAnalyzer.main.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeInfoDto {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Rating")
    private Float rating;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("Genres")
    private List<String> genres = new ArrayList<>();

    @JsonProperty("Year")
    private String year;

    @JsonProperty("List")
    private String list;
}
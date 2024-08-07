package com.SeriesAnalyzer.main.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MangaInfoDto {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Rating")
    private String rating;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("Demography")
    private String demography;

    @JsonProperty("Genres")
    private String genres;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("List")
    private String list;
}

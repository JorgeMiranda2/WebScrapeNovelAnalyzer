package com.SeriesAnalyzer.main.Utils;

import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Series.Anime;
import com.SeriesAnalyzer.main.Models.Series.Manga;
import com.SeriesAnalyzer.main.Models.Series.Novel;
import org.springframework.data.domain.Page;

public class WorkMapper {
    public  static WorkDto convertAnimePageToWorkDto(Anime anime){
    return WorkDto.builder().id(anime.getId()).workType("anime").image(anime.getImageRoute()).title(anime.getName()).build();
    }
    public  static WorkDto convertMangaPageToWorkDto(Manga manga){
        return WorkDto.builder().id(manga.getId()).workType("manga").image(manga.getImageRoute()).title(manga.getName()).build();

    }
    public  static WorkDto convertNovelPageToWorkDto(Novel novel){
    return WorkDto.builder().id(novel.getId()).workType("novel").image(novel.getImageRoute()).title(novel.getName()).build();
    }

}

package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Dtos.UserWorkTypeScrap;
import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Series.Novel;
import com.SeriesAnalyzer.main.Repositories.Series.INovel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NovelService {

    @Autowired
    private INovel novelRepository;

    public void obtainNovelsResponse(UserWorkTypeScrap userWorkTypeScrap){

    }

    public Page<Novel> getNovelCovers(Pageable pageable) {
        return novelRepository.findAll(pageable);
    }

    public Page<Novel> getNovels(Pageable additionalPageable) {
        return null;
    }

    public long getNovelAmount() {
        return novelRepository.count();
    }
}

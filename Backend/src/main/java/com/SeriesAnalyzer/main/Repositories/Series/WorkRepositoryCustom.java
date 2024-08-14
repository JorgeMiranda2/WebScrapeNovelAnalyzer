package com.SeriesAnalyzer.main.Repositories.Series;

import com.SeriesAnalyzer.main.Dtos.WorkDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkRepositoryCustom {
    List<WorkDto> findAllWorksWithImageRoute();
    Page<WorkDto> findAllWorksWithImageRoute(Pageable pageable);
    Page<WorkDto> findAllWorksWithImageRouteAndSearch(String search,Pageable pageable);
    Page<WorkDto> getAllWorksByUserId(Long id, Pageable pageable);


}
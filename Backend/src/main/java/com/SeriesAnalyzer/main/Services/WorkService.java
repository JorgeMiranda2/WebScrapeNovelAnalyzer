package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Dtos.UserWorkTypeScrap;
import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.*;
import com.SeriesAnalyzer.main.Repositories.Series.IAnime;
import com.SeriesAnalyzer.main.Repositories.Series.IManga;
import com.SeriesAnalyzer.main.Repositories.Series.INovel;
import com.SeriesAnalyzer.main.Repositories.Series.IWork;
import com.SeriesAnalyzer.main.Utils.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkService {
    @Autowired
    private IManga mangaRepository;
    @Autowired
    private IAnime animeRepository;
    @Autowired
    private INovel novelRepository;
    @Autowired
    private AnimeService animeService;
    @Autowired
    private MangaService mangaService;
    @Autowired
    private NovelService novelService;

    @Autowired
    private IWork workRepository;

    public List<WorkDto> getAllWorksWithImageRoute() {
        return workRepository.findAllWorksWithImageRoute();
    }
    public Page<WorkDto> getAllWorksWithImageRoute(Pageable pageable) {
        return workRepository.findAllWorksWithImageRoute(pageable);
    }

    public Page<WorkDto> findAllWorksWithImageRouteAndSearch(String search, Pageable pageable){
        return workRepository.findAllWorksWithImageRouteAndSearch(search,pageable);
    }

    @Async
    public void obtainWorksByScraping(String workType, UserWorkTypeScrap userWorkTypeScrap, User user) {
        Class<? extends Work> workClass = getWorkTypeClass(workType);

        if (workClass == Anime.class) {
            animeService.obtainAnimeResponse(userWorkTypeScrap)
                    .subscribe(animeList -> {
                System.out.println("guardando la data");
                animeService.saveAnimeData(animeList, user);
            }, error -> {
                // Maneja errores aquí
                error.printStackTrace();
            });;
        } else if (workClass == Manga.class) {
            mangaService.obtainMangasResponse(userWorkTypeScrap)
                    .subscribe(mangaList -> {
                        System.out.println("guardando la data");
                        mangaService.saveMangaData(mangaList, user);
                    }, error -> {
                        // Maneja errores aquí
                        error.printStackTrace();
                    });
        } else if (workClass == Novel.class) {
            novelService.obtainNovelsResponse(userWorkTypeScrap);
        } else {
            throw new IllegalArgumentException("Unsupported work type: " + workType);
        }
    }


    public Class<? extends Work> getWorkTypeClass(String workType){
        Class<? extends Work> workClass;
        switch (workType.toLowerCase()) {
            case "anime":
                return Anime.class;
            case "manga":
                return Manga.class;
            case "novel":
                return Novel.class;
            default:
                return null;
        }
    }

    public boolean checkIfExistWork(Class<? extends Work> work){
        return false;
    }

    public List<? extends Work> getWorksByUserIdAndType(Long userId, Class<? extends Work> workType) {


        if (workType == Anime.class) {
            return animeRepository.findByUserId(userId);
        } else if (workType == Manga.class) {
            return mangaRepository.findByUserId(userId);
        } else if (workType == Novel.class) {
            return novelRepository.findByUserId(userId);
        } else {
            throw new IllegalArgumentException("Invalid work type: " + workType);
        }
    }

    public Object getWorkServiceByName(String name){
        return switch (name) {
            case "anime" -> animeService;
            case "manga" -> mangaService;
            case "novel" -> novelService;
            default -> throw new Error("Service type not found");
        };
    }




}

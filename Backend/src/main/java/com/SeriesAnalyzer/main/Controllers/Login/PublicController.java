package com.SeriesAnalyzer.main.Controllers.Login;



import com.SeriesAnalyzer.main.Dtos.IdentificationTypesDto;
import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Login.IdentificationType;
import com.SeriesAnalyzer.main.Models.Series.Anime;
import com.SeriesAnalyzer.main.Models.Series.Manga;
import com.SeriesAnalyzer.main.Models.Series.Novel;
import com.SeriesAnalyzer.main.Models.Series.Work;
import com.SeriesAnalyzer.main.Services.*;
import com.SeriesAnalyzer.main.Utils.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private WorkService workService;

    @Autowired
    private IdentificationTypeService identificationTypeService;

    @GetMapping("/identificationtypes")
    public ResponseEntity<List<IdentificationTypesDto>> getIdentificationTypes(){

        List<IdentificationType> identificationTypes = identificationTypeService.getAllIdentificationTypes();
        System.out.println("types2:" + identificationTypes);
        List<IdentificationTypesDto> identificationTypesDtos = identificationTypes.stream().map(identificationType -> {
            return IdentificationTypesDto.builder().id(identificationType.getId()).name(identificationType.getName()).build();
        }).toList();
        return ResponseEntity.status(HttpStatus.OK).body(identificationTypesDtos);
    }

    @GetMapping("/get-works/{type}")
    public ResponseEntity<Page<WorkDto>> getWorkCovers(@PathVariable String type,
                                                       @RequestParam(defaultValue = "0") int numberPage,
                                                       @RequestParam(defaultValue = "25") int itemsPerPage)
    {
        Object service = workService.getWorkServiceByName(type);
        Page<? extends Work> coversPage;
        Page<WorkDto> coversPageDto;


        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);

        if (service instanceof AnimeService) {
             coversPage = ((AnimeService) service).getAnimes(pageable);
            coversPageDto = coversPage.map(anime -> WorkMapper.convertAnimePageToWorkDto((Anime) anime));
        } else if (service instanceof MangaService) {
            coversPage = ((MangaService) service).getMangas(pageable);
            coversPageDto = coversPage.map(manga -> WorkMapper.convertMangaPageToWorkDto((Manga) manga));
        } else if (service instanceof NovelService) {
            coversPage  = ((NovelService) service).getNovelCovers(pageable);
            coversPageDto = coversPage.map(novel -> WorkMapper.convertNovelPageToWorkDto((Novel) novel));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Manejo de error si el tipo no coincide
        }


        return ResponseEntity.status(HttpStatus.OK).body(coversPageDto);
    }

    @Transactional
    @PostMapping("get-works/pageable")
    public ResponseEntity<Page<WorkDto>> getWorksPageable(
                                                        @RequestParam(defaultValue = "0") int numberPage,
                                                        @RequestParam(defaultValue = "25") int itemsPerPage){
        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);


        return ResponseEntity.status(HttpStatus.OK).body(workService.getAllWorksWithImageRoute(pageable));
    }

    @PostMapping("get-works/all")
    public ResponseEntity<List<WorkDto>> getAllWorks(
                                     @RequestParam(defaultValue = "0") int numberPage,
                                     @RequestParam(defaultValue = "25") int itemsPerPage) {

        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);
        return ResponseEntity.status(HttpStatus.OK).body(workService.getAllWorksWithImageRoute());
    }

    @Transactional
    @PostMapping("get-works/search/{name}")
    public ResponseEntity<Page<WorkDto>> getWorksByName(@PathVariable String name,
                                                        @RequestParam(defaultValue = "0") int numberPage,
                                                        @RequestParam(defaultValue = "25") int itemsPerPage){
        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);


        return ResponseEntity.status(HttpStatus.OK).body(workService.findAllWorksWithImageRouteAndSearch(name,pageable));
    }


}

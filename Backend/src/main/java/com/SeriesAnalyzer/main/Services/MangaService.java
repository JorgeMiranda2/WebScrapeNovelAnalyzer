package com.SeriesAnalyzer.main.Services;


import com.SeriesAnalyzer.main.Dtos.MangaInfoDto;
import com.SeriesAnalyzer.main.Dtos.UserWorkTypeScrap;
import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.*;
import com.SeriesAnalyzer.main.Repositories.Series.IManga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MangaService {


   @Autowired
   private WebClient webClient;

   @Autowired
   private UserWorkService userWorkService;

    @Autowired
    private IManga mangaRepository;

    @Autowired
    private UserService userService;

    @Value("${analitycs.python.url}")
    private String analyticsPythonUrl;

    private final String endpointGet = "/getwork";

    public Mono<List<MangaInfoDto>> obtainMangasResponse(UserWorkTypeScrap userWorkTypeScrap) {
        System.out.println("Enviando petición");
        System.out.println(analyticsPythonUrl + endpointGet);

        return webClient.post() // Usa .post() para la solicitud POST
                .uri(uriBuilder -> uriBuilder
                        .path(endpointGet) // El camino relativo
                        .queryParam("work_type", "manga") // Parámetro de consulta
                        .build())
                .bodyValue(userWorkTypeScrap) // Envía el cuerpo JSON
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MangaInfoDto>>() {})
                .doOnSuccess(mangaList -> System.out.println("Petición exitosa, tamaño de la lista: " + (mangaList != null ? mangaList.size() : 0)))
                .doOnError(error -> System.out.println("Error en la petición: " + error.getMessage()))
                .onErrorResume(e -> Mono.empty()); // Manejar errores aquí
    }

    @Transactional
    @Async
    public void saveMangaData(List<MangaInfoDto> mangas, User user) {
        CompletableFuture.runAsync(() -> {

            mangas.forEach(mangaDto -> {

                        Manga manga;
                        Optional<Manga> optionalManga = this.findMangaByName(mangaDto.getTitle());

                        if (optionalManga.isPresent()) {
                            manga = optionalManga.get();
                        } else {
                            manga = this.saveMangaDto(mangaDto);
                        }


                userWorkService.vinculateWorkIdToUser(user, manga);
                System.out.println("Manga linked to user: " + mangaDto.getTitle());
            });
            System.out.println("Completed processing all mangas");
        });
    }

    public Manga saveMangaDto(MangaInfoDto mangaDto){
        try {
            System.out.println("Processing manga: " + mangaDto.getTitle());
            Manga manga = new Manga();
            manga.setName(mangaDto.getTitle());
            manga.setYear(mangaDto.getYear());
            manga.setDemography(mangaDto.getDemography());
            manga.setRating(0f);
            manga.setAuthor(Author.builder().id(1L).build());
            manga.setImageRoute(mangaDto.getImage());
            manga.setSynopsis("");
            manga.setStatusOfWork(StatusOfWork.builder().id(1L).build());
            System.out.println("Manga saved: " + mangaDto.getTitle());
            return mangaRepository.save(manga);


        } catch (Exception e) {
            System.out.println("Error processing manga: " + mangaDto.getTitle());
            e.printStackTrace();
            return null;

        }
    }

    public Optional<Manga> findMangaByName(String name){
        return mangaRepository.findByName(name);
    }


    public Page<Manga> getMangas(Pageable pageable) {
        return mangaRepository.findAll(pageable);
    }

    public Page<Manga> getMangasByName(Pageable pageable, String name) {
        return mangaRepository.findByNameContaining(name,pageable);
    }

    public long getMangaAmount() {
        return mangaRepository.count();
    }
}

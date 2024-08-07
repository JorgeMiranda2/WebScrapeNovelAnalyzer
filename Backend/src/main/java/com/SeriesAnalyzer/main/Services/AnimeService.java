package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Dtos.AnimeInfoDto;
import com.SeriesAnalyzer.main.Dtos.UserWorkTypeScrap;
import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.Anime;
import com.SeriesAnalyzer.main.Models.Series.Author;
import com.SeriesAnalyzer.main.Models.Series.StatusOfWork;
import com.SeriesAnalyzer.main.Repositories.Series.IAnime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class AnimeService {

    @Autowired
    private IAnime animeRepository;

    @Autowired
    private UserWorkService userWorkService;

    @Autowired
    private WebClient webClient;

    @Value("${analitycs.python.url}")
    private String analyticsPythonUrl;

    private final String endpointGet = "/getwork";

    public Mono<List<AnimeInfoDto>> obtainAnimeResponse(UserWorkTypeScrap userWorkTypeScrap){
        System.out.println("Enviando petición");
        System.out.println(analyticsPythonUrl + endpointGet);

        return webClient.post() // Usa .post() para la solicitud POST
                .uri(uriBuilder -> uriBuilder
                        .path(endpointGet) // El camino relativo
                        .queryParam("work_type", "anime") // Parámetro de consulta
                        .build())
                .bodyValue(userWorkTypeScrap) // Envía el cuerpo JSON
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AnimeInfoDto>>() {})
                .doOnSuccess(animeList -> System.out.println("Petición exitosa, tamaño de la lista: " + (animeList != null ? animeList.size() : 0)))
                .doOnError(error -> System.out.println("Error en la petición: " + error.getMessage()))
                .onErrorResume(e -> Mono.empty()); // Manejar errores aquí
    }

    @Async
    @Transactional
    public void saveAnimeData(List<AnimeInfoDto> animeList, User user) {
        CompletableFuture.runAsync(() -> {

            animeList.forEach(animeDto -> {

                Anime anime;
                Optional<Anime> optionalAnime = this.findAnimeByName(animeDto.getTitle());

                if (optionalAnime.isPresent()) {
                    anime = optionalAnime.get();
                } else {
                    anime = this.saveAnimeDto(animeDto);
                }


                userWorkService.vinculateWorkIdToUser(user, anime);
                System.out.println("anime linked to user: " + animeDto.getTitle());
            });
            System.out.println("Completed processing all animes");
        });
    }

    private Optional<Anime> findAnimeByName(String title) {
        return animeRepository.findAnimeByName(title);
    }

    private Anime saveAnimeDto(AnimeInfoDto animeDto) {

    Anime anime = Anime.builder()
            .rating(animeDto.getRating())
            .year(animeDto.getYear())
            .imageRoute(animeDto.getImage())
            .name(animeDto.getTitle())
            .synopsis("")
            .statusOfWork(StatusOfWork.builder().id(1L).build())
            .author(Author.builder().id(1L).build())
            .build();

        return animeRepository.save(anime);
    }

    public Page<Anime> getAnimes(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Page<Anime> getAnimesByName(Pageable pageable, String name) {
        return animeRepository.findByNameContaining(name,pageable);
    }

    public Long getAnimeAmount() {
        return animeRepository.count();
    }
}

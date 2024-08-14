package com.SeriesAnalyzer.main.Controllers.Series;

import com.SeriesAnalyzer.main.Dtos.MangaInfoDto;
import com.SeriesAnalyzer.main.Dtos.UserWorkTypeScrap;
import com.SeriesAnalyzer.main.Dtos.WorkDto;
import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.Anime;
import com.SeriesAnalyzer.main.Models.Series.Manga;
import com.SeriesAnalyzer.main.Models.Series.Novel;
import com.SeriesAnalyzer.main.Models.Series.Work;
import com.SeriesAnalyzer.main.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@EnableAsync
@RestController
@RequestMapping("/api/works")
public class WorkController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;
    @Autowired
    private MangaService mangaService;
    @Autowired
    private AnimeService animeService;
    @Autowired
    private NovelService novelService;

    @GetMapping("/user/{userId}/type/{workType}")
    public ResponseEntity<Page<? extends Work>> getWorksByUserIdAndType(@PathVariable Long userId, @PathVariable String workType,
                                                                        @RequestParam(defaultValue = "0") int numberPage,
                                                                        @RequestParam(defaultValue = "25") int itemsPerPage) {
        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);
        Class<? extends Work> workClass = workService.getWorkTypeClass(workType);

            if(workClass == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        Page<? extends Work> works = workService.getWorksByUserIdAndType(userId, workClass, pageable) ;
        return new ResponseEntity<>(works, HttpStatus.OK);
    }

    @GetMapping("/myWorks/type/{workType}")
    public ResponseEntity<Page<? extends Work>> getWorksByUserTokenAndType( @PathVariable String workType,
                                                                        @RequestParam(defaultValue = "0") int numberPage,
                                                                        @RequestParam(defaultValue = "25") int itemsPerPage) {
        Long userId = userService.getUserFromToken()
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);
        Class<? extends Work> workClass = workService.getWorkTypeClass(workType);

        if(workClass == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<? extends Work> works = workService.getWorksByUserIdAndType(userId, workClass, pageable) ;
        return new ResponseEntity<>(works, HttpStatus.OK);
    }


    @PostMapping("/scrap/type/{workType}")
    public CompletableFuture<ResponseEntity<String>> obtainWorksByScraping
            (@PathVariable String workType, @RequestBody UserWorkTypeScrap userWorkTypeScrap) throws InterruptedException {
        Optional<User> user = userService.getUserFromToken();

        if (user.isEmpty()){
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok("User Not found"));
        }
        System.out.println(user.get().getUsername());
        workService.obtainWorksByScraping(workType, userWorkTypeScrap, user.get());
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok("Obtaining the works, you will be notified when it ends"));
    }

    @GetMapping("/myWorks")
    public ResponseEntity<Page<WorkDto>> getAllMyWorks(@RequestParam(defaultValue = "0") int numberPage,
                                                       @RequestParam(defaultValue = "25") int itemsPerPage){

        Optional<User> optionalUser = userService.getUserFromToken();
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Pageable pageable = PageRequest.of(numberPage, itemsPerPage);
        Page<WorkDto> workCovers = workService.getAllWorksByUserId(optionalUser.get().getId(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(workCovers);
    }

}
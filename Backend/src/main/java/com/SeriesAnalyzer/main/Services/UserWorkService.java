package com.SeriesAnalyzer.main.Services;

import com.SeriesAnalyzer.main.Models.Login.User;
import com.SeriesAnalyzer.main.Models.Series.UserWork;
import com.SeriesAnalyzer.main.Models.Series.Work;
import com.SeriesAnalyzer.main.Repositories.Series.IUserWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWorkService {
    @Autowired
    private IUserWork userWorkRepository;

    public void vinculateWorkIdToUser(User user, Work work){

        userWorkRepository.save(UserWork.builder()
                        .user(user)
                        .work(work)
                        .status("unkown")
                        .listName("unkown")
                        .build());
    }
}

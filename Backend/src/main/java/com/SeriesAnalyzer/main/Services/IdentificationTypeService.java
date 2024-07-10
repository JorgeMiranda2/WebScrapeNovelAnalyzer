package com.SeriesAnalyzer.main.Services;




import com.SeriesAnalyzer.main.Repositories.Login.IIdentificationType;
import com.SeriesAnalyzer.main.Models.Login.IdentificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationTypeService {
    @Autowired
    private IIdentificationType iIdentificationType;

    public List<IdentificationType> getAllIdentificationTypes(){
        return iIdentificationType.findAll();
    }
}

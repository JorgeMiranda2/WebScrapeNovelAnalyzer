package com.SeriesAnalyzer.main.Services;




import com.SeriesAnalyzer.main.Repositories.Login.IIdentificationType;
import com.SeriesAnalyzer.main.Models.Login.IdentificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdentificationTypeService {
    @Autowired
    private IIdentificationType identificationTypeRepository;

    public List<IdentificationType> getAllIdentificationTypes(){
        return identificationTypeRepository.findAll();
    }

    public Optional<Long> getIdentificationTypeByName(String name) {
        return identificationTypeRepository.getIdentificationTypeByName(name);
    }
}

package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.repository.MajorRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MajorService {
    MajorRepository majorRepository;

    public Major save(MajorCreateRequest request) {
        var major = new Major();
        major.setName(request.name());
        return majorRepository.save(major);
    }
}

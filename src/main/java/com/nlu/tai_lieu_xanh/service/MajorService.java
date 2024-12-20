package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.exception.MajorNotFoundException;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.repository.MajorRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MajorService {
    MajorRepository majorRepository;

    public List<Major> findAll() {
        return majorRepository.findAll();
    }

    public List<Major> searchByName(String name) {
        return majorRepository.searchMajorByName(name);
    }

    public void deleteMajor(Integer majorId) {
        var major = findById(majorId);
        majorRepository.delete(major);
    }

    public Major findById(Integer id) {
        return majorRepository.findById(id).orElseThrow(MajorNotFoundException::new);
    }

    public Major update(Integer id, MajorUpdateRequest request) {
        var major = findById(id);
        major.setName(request.name());
        return majorRepository.save(major);
    }

    public Major save(MajorCreateRequest request) {
        var major = new Major();
        major.setName(request.name());
        return majorRepository.save(major);
    }
}

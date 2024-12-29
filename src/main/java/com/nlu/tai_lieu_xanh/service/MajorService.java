package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.dto.request.MajorUpdateRequest;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorRes;
import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.dto.response.post.PostResponse;
import com.nlu.tai_lieu_xanh.dto.response.tag.TagWithPostsRes;
import com.nlu.tai_lieu_xanh.exception.MajorNotFoundException;
import com.nlu.tai_lieu_xanh.mapper.PostMapper;
import com.nlu.tai_lieu_xanh.mapper.SharedConfig;
import com.nlu.tai_lieu_xanh.model.Major;
import com.nlu.tai_lieu_xanh.model.Tag;
import com.nlu.tai_lieu_xanh.repository.MajorRepository;
import com.nlu.tai_lieu_xanh.repository.PostRepository;
import com.nlu.tai_lieu_xanh.repository.TagRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.poifs.filesystem.POIFSStream;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MajorService {
    MajorRepository majorRepository;
    PostRepository postRepository;
    TagRepository tagRepository;

    public List<MajorRes> findAll() {
        var majors = majorRepository.findAll();
        return majors.stream().map(SharedConfig::toMajorRes).toList();
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

    public List<MajorWithPostsRes> findHotMajorsWithPosts() {
        return postRepository.findHotMajorsWithPosts();
    }

    public List<TagWithPostsRes> getTagWithPost() {
        return postRepository.findTagsSortedByPostCountAsDTO();
    }

    public List<MajorWithPostsRes> getMajorWithPost() {
        List<Major> majors = majorRepository.findAll();
        return majors.stream()
                .map(major -> {
                    long postCount = postRepository.countByMajorId(major.getId());
                    return new MajorWithPostsRes(major.getId(), major.getName(), Math.toIntExact(postCount));
                })
                .toList();
    }
}

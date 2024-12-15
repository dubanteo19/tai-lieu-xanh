package com.nlu.tai_lieu_xanh.controller;


import com.nlu.tai_lieu_xanh.service.MajorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/api/v1/majors")
public class MajorController {
    MajorService majorService;

}

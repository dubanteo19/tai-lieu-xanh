package com.nlu.tai_lieu_xanh.data_loader;

import com.nlu.tai_lieu_xanh.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MajorLoader implements CommandLineRunner {
    private final MajorService majorService;
    @Value("${include-data-loader}")
    boolean includeDataLoader;

    public MajorLoader(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (includeDataLoader) {
            return;
        }
        var m1 = new MajorCreateRequest("Ngôn ngữ Anh");
        var m2 = new MajorCreateRequest("Kinh tế");
        var m3 = new MajorCreateRequest("Kế toán");
        var m4 = new MajorCreateRequest("Nông học");
        var m5 = new MajorCreateRequest("Công nghệ thông tin");
        majorService.save(m1);
        majorService.save(m2);
        majorService.save(m3);
        majorService.save(m4);
        majorService.save(m5);
    }
}
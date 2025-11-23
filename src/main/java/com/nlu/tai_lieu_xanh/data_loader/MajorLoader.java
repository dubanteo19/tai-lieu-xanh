package com.nlu.tai_lieu_xanh.data_loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.nlu.tai_lieu_xanh.application.major.dto.request.MajorCreateRequest;
import com.nlu.tai_lieu_xanh.application.major.service.AdminMajorService;

@Component
@Order(1)
public class MajorLoader implements CommandLineRunner {
  private final AdminMajorService adminMajorService;
  @Value("${include-data-loader}")
  boolean includeDataLoader;

  public MajorLoader(AdminMajorService adminMajorService) {
    this.adminMajorService = adminMajorService;
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
    var m6 = new MajorCreateRequest("Thú y");
    adminMajorService.save(m1);
    adminMajorService.save(m2);
    adminMajorService.save(m3);
    adminMajorService.save(m4);
    adminMajorService.save(m5);
    adminMajorService.save(m6);
  }
}

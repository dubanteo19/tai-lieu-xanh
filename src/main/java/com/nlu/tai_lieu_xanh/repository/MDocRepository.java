package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.model.MDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MDocRepository extends JpaRepository<MDoc, Integer> {
    @Query("SELECT SUM(d.downloads) FROM MDoc d")
    Long totalDownload();
}

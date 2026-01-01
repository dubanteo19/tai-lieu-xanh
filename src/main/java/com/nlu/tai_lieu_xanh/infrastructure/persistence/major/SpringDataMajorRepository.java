package com.nlu.tai_lieu_xanh.infrastructure.persistence.major;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.tai_lieu_xanh.domain.major.Major;

public interface SpringDataMajorRepository extends JpaRepository<Major, Long> {
  @Query("""
      SELECT m from Major m
      WHERE LOWER(m.name) LIKE LOWER(CONCAT('%',:name,'%'))
          """)
  List<Major> searchMajorByName(@Param("name") String name);

  /*
   * @Query("""
   * SELECT m.id as id, m.name as majorName, COUNT(p) as postCount
   * FROM Major m
   * LEFT JOIN m.posts p
   * GROUP BY m.id, m.name
   * """)
   * List<MajorWithPostCountProjection> findAllMajorsWithPostCount();
   */
}

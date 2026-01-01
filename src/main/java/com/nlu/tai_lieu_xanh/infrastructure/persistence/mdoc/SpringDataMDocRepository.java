package com.nlu.tai_lieu_xanh.infrastructure.persistence.mdoc;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;

public interface SpringDataMDocRepository extends JpaRepository<MDoc, Long> {

}

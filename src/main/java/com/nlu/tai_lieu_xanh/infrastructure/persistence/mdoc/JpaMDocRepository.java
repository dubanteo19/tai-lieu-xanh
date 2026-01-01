package com.nlu.tai_lieu_xanh.infrastructure.persistence.mdoc;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nlu.tai_lieu_xanh.domain.mdoc.MDoc;
import com.nlu.tai_lieu_xanh.domain.mdoc.MDocRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaMDocRepository implements MDocRepository {
  private final SpringDataMDocRepository springDataMDocRepository;

  @Override
  public MDoc save(MDoc mDoc) {
    return springDataMDocRepository.save(mDoc);
  }

  @Override
  public Optional<MDoc> findById(Long id) {
    return springDataMDocRepository.findById(id);
  }

  @Override
  public Long getTotalDownload() {
    // Todo
    return 0L;
  }

}

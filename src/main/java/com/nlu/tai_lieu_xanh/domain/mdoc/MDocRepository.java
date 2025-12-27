package com.nlu.tai_lieu_xanh.domain.mdoc;

import java.util.Optional;

public interface MDocRepository {
  MDoc save(MDoc mDoc);

  Optional<MDoc> findById(Long id);

  Long getTotalDownload();
}

package io.back3nd.backend.domain.dao;

import io.back3nd.backend.domain.entity.ImageFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFilesRepository extends JpaRepository<ImageFiles, Long> {
}

package com.ImageUploadDownload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ImageUploadDownload.entity.ImageData;

public interface Store extends JpaRepository<ImageData, Long> {
	Optional<ImageData> findByName(String filename);
}

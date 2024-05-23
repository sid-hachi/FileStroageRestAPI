package com.ImageUploadDownload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ImageUploadDownload.entity.Filedata;

public interface FileSystemStorage extends JpaRepository<Filedata, Long> {
	Optional<Filedata> findByName(String Filename);
}

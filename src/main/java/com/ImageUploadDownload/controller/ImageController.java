package com.ImageUploadDownload.controller;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ImageUploadDownload.service.StorageService;

@RestController
public class ImageController {
	
	@Autowired
	private StorageService service;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImg(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}
	
	@GetMapping("/download/{filename}")
	public ResponseEntity<?> downloadImg(@PathVariable String filename) throws DataFormatException{
		byte[] downloadImage = service.downloadImage(filename);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(downloadImage);
		
	}
	
	@GetMapping("/check")
	public boolean check() throws DataFormatException, IOException {
		boolean tocheck = service.tocheck();
		return tocheck;
		
	}
	
	@PostMapping("/filesystem")
	public ResponseEntity<?> uploadImgFS(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadFileSystem(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}
	
	@GetMapping("/filesystem/{filename}")
	public ResponseEntity<?> downloadImgFS(@PathVariable String filename) throws DataFormatException, IOException{
		byte[] downloadImage = service.downloadFileSystem(filename);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(downloadImage);
		
	}
}

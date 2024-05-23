package com.ImageUploadDownload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ImageUploadDownload.entity.Filedata;
import com.ImageUploadDownload.entity.ImageData;
import com.ImageUploadDownload.repository.FileSystemStorage;
import com.ImageUploadDownload.repository.Store;
import com.ImageUploadDownload.util.ImageUtility;

@Service
public class StorageService {
	
	Logger logger = LoggerFactory.getLogger(StorageService.class);

	@Autowired
	private Store image_db;
	
	@Autowired
	private FileSystemStorage storage;
	private final String FILEPATH = "D:\\work\\IMAGES\\";

	public String uploadImage(MultipartFile file) throws IOException {
		Logger logger = LoggerFactory.getLogger(StorageService.class);
		logger.info("Size of the file is : "+file.getSize());
		ImageData data = image_db.save(ImageData.builder().name(file.getOriginalFilename()).type(file.getContentType())
				.imageData(ImageUtility.compress(file.getBytes())).build());
		
		if(data!=null) {
			return "Image Successfully Uploaded";
		}
		return "Failed to upload the image";
		
	}

	public byte[] downloadImage(String Filename) throws DataFormatException {
		Optional<ImageData> byName = image_db.findByName(Filename);
		byte[] decompress = ImageUtility.decompress(byName.get().getImageData());
		return decompress;
	}

	public boolean tocheck() throws DataFormatException, IOException {
		// Load the image file
		File imageFile = new File("C:\\Users\\Sghanekar\\Documents\\Capture.png");
		byte[] imageData = readBytesFromFile(imageFile);
		boolean check = false;
		// Compress the image data
		byte[] compressedData = ImageUtility.compress(imageData);

		// Decompress the compressed data
		byte[] decompressedData = ImageUtility.decompress(compressedData);

		// Check if the original and decompressed data are equal
		boolean isEqual = Arrays.equals(imageData, decompressedData);
		if (isEqual) {
			System.out.println("Compression and decompression successful.");
			check=true;
		} else {
			System.out.println("Compression and decompression failed.");
		}
		
		return check;
	}

	public static byte[] readBytesFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		fis.read(bytes);
		fis.close();
		return bytes;
	}
	
	public String uploadFileSystem(MultipartFile file) throws IllegalStateException, IOException {
		String filepath = FILEPATH + file.getOriginalFilename();
		logger.info("Storing file at : " + filepath);
		Filedata save = storage.save(Filedata.builder().name(file.getOriginalFilename()).type(file.getContentType())
				.filepath(filepath).build());
		if (save != null) {
			logger.info("Filedata Successfully stored in database");
			file.transferTo(new File(filepath));
			logger.info("File is stored in FileSystem at " + filepath);
			return "File is stored in FileSystem at " + filepath;
		} else {
			logger.info("Failed to store file");
			return "Failed to store file";
		}
	}
	
	public byte[] downloadFileSystem(String filename) throws IOException {
		Optional<Filedata> byName = storage.findByName(filename);
		String filepath = byName.get().getFilepath();
		byte[] allBytes = Files.readAllBytes(new File(filepath).toPath());
		return allBytes;
	}
}

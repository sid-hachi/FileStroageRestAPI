package com.ImageUploadDownload.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtility {

	public static byte[] compress(byte[] arr) {
		Deflater deflater = new Deflater();
		deflater.setInput(arr);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		while (!deflater.finished()) {
			int compressedSize = deflater.deflate(buffer);
			outputStream.write(buffer, 0, compressedSize);
		}

		return outputStream.toByteArray();
	}

	public static byte[] decompress(byte[] arr) throws DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(arr);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		while (!inflater.finished()) {
			int decompressedSize = inflater.inflate(buffer);
			outputStream.write(buffer, 0, decompressedSize);
		}

		return outputStream.toByteArray();
	}

}

package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component	// 이반적인 스프링 빈
public class FileManagerService {
	
	// 실제 이미지가 저장될 경로(서버).
	// 학원 경로
//	public static final String FILE_UPLOAD_PATH = "D:\\LEEJONGSEOK\\6_spring_project\\memo\\workspace\\images/";
	// 집 경로
	public static final String FILE_UPLOAD_PATH = "D:\\Coding\\06_spring_project\\memo\\workspace\\images/";
	
	// input : MultipartFile, userLoginId
	// output : image path
	public String saveFile(String userLoginId, MultipartFile file) {
		// 파일 디렉토리 ex) aaaa_1620546878/sun.png
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null;	// 폴더 만드는데 실패시 이미지패스 null
		}
		
		// 파일 업로드 : byte 단위로 업로드 된다.
		try {
			byte[] bytes = file.getBytes();
			// 파일 이름은 한글은 저장이 불가능하기 때문에 개인 프로젝트 진행 시 영어로 바꿔주는 로직 구성해야 함 
			Path path = Paths.get(filePath + file.getOriginalFilename());	// orignalFileName은 사용자가 올린 파일명
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드 성공했으면 이미지 url path를 리턴한다.
		// http://localhost/images/aaaa_1620546878/sun.png
		return "/images/" + directoryName + file.getOriginalFilename();
	}
}

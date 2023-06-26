package org.ezen.ex02.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/upload")
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload form");
		// upload폴더 바로 밑에 uploadForm.jsp로 이동
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		//tomcat 제공 multipart-config 사용 요청시 전달되는 객체는 MultipartFile의 객체임
		//파일이 복수개로 오므로 배열로 처리
		String uploadFolder = "C:/upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			//File(String parent, String child) 생성자로 파일이 있는 폴더의 경로와 파일 이름으로 파일 객체 생성
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일 객체에 저장 
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("upload ajax");
	}
	
	
	@PostMapping("/uploadAjaxAction")
	@ResponseBody
	public String uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("update ajax post........");
		
		String uploadFolder = "C:/upload";
		
		//날짜별 폴더 만들기 
		File uploadPath = new File(uploadFolder,getFolder());
		//getFolder()는 날짜별 폴더를 반환하는 메서드(yyyy/mm/dd)로 uploadPath객체는 c:/upload/yyyy/mm/dd 폴더 객체
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); //맨처음 업로드시만 만듦
		}
		
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("----------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			//IE는 file path 까지 파일 이름에 가지므로 파일 이름만 갖도록 처리 
			uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			log.info("only file name: " + uploadFileName);
			
			UUID uuid = UUID.randomUUID(); //중복되지 않은 값 UUID객체 생성
			
			uploadFileName = uuid.toString() + "_" + uploadFileName; //원래 파일 이름에 uuid.toString() + "_" 추가
			
			//File saveFile = new File(uploadFolder,uploadFileName); yyyy/mm/dd 미처리
			File saveFile = new File(uploadPath, uploadFileName); // yyyy/mm/dd 처리된 파일 객체
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일 객체에 저장
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return "success";
	}
	
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Date객체를 정해진 패턴 문자열로 변환시 사용하는 객체
		
		Date date = new Date();
		
		String str = sdf.format(date); //날짜 객체를 정해진 포맷의 문자열로 변환
		
		return str.replace("-", File.separator); //문자열중 -를 File.separator(파일 구분자)로 변경
	}
	
}

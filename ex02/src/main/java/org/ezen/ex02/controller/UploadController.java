package org.ezen.ex02.controller;

import java.io.File;

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
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("----------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			//IE는 file path 까지 파일 이름에 가지므로 파일 이름만 갖도록 처리 
			uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			log.info("only file name: " + uploadFileName);
			
			File saveFile = new File(uploadFolder,uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일 객체에 저장
			}
			catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return "success";
	}
	
}

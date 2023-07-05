package org.ezen.ex02.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ezen.ex02.domain.AttachFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

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
		//tomcat제공 multipart-config사용시 요청시 전달되는 객체는 MultipartFile의 객체임
		//파일이 복수개로 오므로 배열로 처리
		String uploadFolder = "C:/upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			//File(String parent, String child) 생성자로 파일이 있는 폴더의 경로와 파일 이름으로 파일 객체 생성 
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일객체에 저장
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
	
	//첨부 파일 정보를 ajax로 반환 미처리
	/*
	@PostMapping("/uploadAjaxAction")
	@ResponseBody	
	public String uploadAjaxPost(MultipartFile[]  uploadFile) {
		
		log.info("update ajax post.........");
		
		String uploadFolder = "C:/upload";
		
		//날짜별 폴더 만들기
		File uploadPath = new File(uploadFolder, getFolder());
		//getFolder()는 날짜별 폴더를 반환하는 메서드(yyyy/mm/dd)로 uploadPath객체는 c:/upload/yyyy/mm/dd 폴더 객체
		
		if (uploadPath.exists() == false)  {
			uploadPath.mkdirs(); //그날중 맨처음 업로드시만 만듬
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			// IE는 file path까지 파일 이름에 가지므로 파일 이름만 갖도록 처리
			uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			log.info("only file name: " + uploadFileName);
			
			UUID uuid = UUID.randomUUID(); //중복되지 않은 값 UUID객체 생성
			
			uploadFileName = uuid.toString() + "_" + uploadFileName; //원래 파일이름에 uuid.toString() + "_" 추가
			
			//File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename()); //yyyy/mm/dd미처리
			File saveFile = new File(uploadPath, uploadFileName); //yyyy/mm/dd처리된 파일 객체
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일객체에 저장
				
				if (checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					//uploadPath, "s_" + uploadFileName파일 객체에 출력
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					//수신된 파일을 출력 스트림인 thumbnaildp zmrl 100 x 100으로 생성
					
					thumbnail.close();
				}
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
			
		}
		
		return "success";
		
	}
	*/
	
	//첨부 파일 정보 ajax로 반환
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody	
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("update ajax post.........");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:/upload";
		
		String uploadFolderPath = getFolder(); //yyyy/mm/dd
		
		//전체 경로 폴더
		File uploadPath = new File(uploadFolder, uploadFolderPath );		
		
		//getFolder()는 날짜별 폴더를 반환하는 메서드(yyyy/mm/dd)로 uploadPath객체는 c:/upload/yyyy/mm/dd 폴더 객체
		
		if (uploadPath.exists() == false)  {
			uploadPath.mkdirs(); //그날중 맨처음 업로드시만 만듬
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			// IE는 file path까지 파일 이름에 가지므로 파일 이름만 갖도록 처리
			uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			log.info("only file name: " + uploadFileName);
			
			attachDTO.setFileName(uploadFileName);  //AttachFileDTO의 fileName멤버변수
			
			UUID uuid = UUID.randomUUID(); //중복되지 않은 값 UUID객체 생성
			
			uploadFileName = uuid.toString() + "_" + uploadFileName; //원래 파일이름에 uuid.toString() + "_" 추가
			
			//File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename()); //yyyy/mm/dd미처리
			File saveFile = new File(uploadPath, uploadFileName); //yyyy/mm/dd처리된 파일 객체
			
			try {
				multipartFile.transferTo(saveFile); //수신된 파일을 지정된 파일객체에 저장
				
				attachDTO.setUuid(uuid.toString()); //AttachFileDTO의 uuid멤버변수
				attachDTO.setUploadPath(uploadFolderPath); //AttachFileDTO의 uploadPath맴버변수
				
				if (checkImageType(saveFile)) {
					
					attachDTO.setImage(true); //AttachFileDTO의 image멤버변수
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					//uploadPath, "s_" + uploadFileName파일 객체에 출력
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					//수신된 파일을 출력 스트림인 thumbnaildp zmrl 100 x 100으로 생성
					
					thumbnail.close();
				}
				
				// add to List
				list.add(attachDTO);
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
			
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/display", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) { //클라이언트에서 이미지 파일만 보냄
		
		log.info("fileName: " + fileName);
		
		File file = new File("c:/upload/" + fileName); //이미지 파일들
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			HttpHeaders header = new HttpHeaders(); //Content-Type속성을 넣어주기위해 생성
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			//FileCopyUtils.copyToByteArray(file)은 파일 객체를 바이트 배열로 변환 반환
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) {
		
		log.info("download file: " + fileName);
		
		Resource resource = new FileSystemResource("c:/upload/" + fileName);
		//FileSystemResource는 Resource를 구현한 클래스 (자원을 대표하는 인터페이스)
		//경로가 지정하는 파일로 된 자원
		
		if (resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource: " + resource);
		
		String resourceName = resource.getFilename();
		
		// remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			// UTF-8로된 문자열을 바이트배열로 변경후 ISO-8859-1로 인코딩된 문자열로 변경,파일이름을 지정하여 한글 께짐 처리
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping(value = "/deleteFile", produces = { MediaType.TEXT_PLAIN_VALUE + ";charset=UTF8" })	
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		
		log.info("deleteFile: " + fileName);
		
		File file = null;
		
		try {
			
			file = new File("c:/upload/" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete(); //지정된 파일 삭제(이미지는 섬네일파일,일반파일은 파일 삭제)
			
			if (type.equals("image")) {
				//원본파일 구하기
				String largeFileName = file.getAbsolutePath().replace("s_", ""); //s_를 삭제
				
				log.info("largeFileName: " + largeFileName);

				file = new File(largeFileName);

				file.delete();

			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Date객체를 정해진 패턴 문자열로 변환시 사용하는 객체

		Date date = new Date();

		String str = sdf.format(date); //날짜 객체를 정해진 포맷의 문자열로 변환

		return str.replace("-", File.separator); //문자열중 -를 File.separator(파일 구분자)로 변경
	}
	
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			//file객체의 Path객체에 있는 contentType을 알아냄
			if( contentType == null) {
				return false;
			}
			
			return contentType.startsWith("image");
			//startWith(문자열)은 문자열로 시작하면 true
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}

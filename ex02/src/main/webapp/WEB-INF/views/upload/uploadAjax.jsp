<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %> 
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Insert title here</title>
<meta charset="UTF-8">

<!-- RWD -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- MS -->
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,IE=EmulateIE9"/> 
</head>
<body>
<%@ include file="../includes/header.jsp" %>

<div class="container mt-4 mb-4" id="mainContent">
	<div class="row">
		<div class="col-md-2">
			<h4 class="wordArtEffect text-success pl-4">메뉴</h4>
			<nav class="navbar bg-dark navbar-dark container">
				<!-- 수직메뉴 d-md-none으로 컬랩스 해결-->
				<button class="navbar-toggler d-md-none" type="button"
					data-toggle="collapse" data-target="#collapsibleVertical">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse d-md-block"
					id="collapsibleVertical">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="#"><i
								class="fas fa-home" style="font-size: 30px; color: white;"></i></a></li>
						
						<li class="nav-item"><a class="nav-link" href="list">리스트</a></li>
						<li class="nav-item"><a class="nav-link"
						 href='modify?bno=<c:out value="${board.bno}" />'>수정</a></li>
					</ul>
				</div>
			</nav>
		</div><!-- col-md-2 -->
		<div class="col-md-10">
			<div id="submain">
				<h4 class="text-center wordArtEffect text-success">파일 업로드</h4>
				<!-- 업로드 입력 창 -->
				<div class="uploadDiv">
					<input type="file" name="uploadFile" multiple />
				</div>
				<p>
					<button type="button" id="uploadBtn" class="btn btn-primary mt-3">Ajax Upload</button>
				</p>
				<!-- 업로드 파일 보여주기 창 -->
				<div class='uploadResult mt-3'>
					<div class='row' id='cardRow'>
						<!-- 
						<div class='card col-md-3'></div>의 card엘리먼트를 파일 갯수만큼 자바스크립트에서 만들어 줌
						 -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../includes/footer.jsp" %>

<script>
//ajax upload이벤트 처리
$("#uploadBtn").on("click", function(e){
	
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	//RegExp는 정규식 처리 코어 객체로 exe,sh,zip,alz를 포함하고 있는 정규식 객체
	let maxSize = 5242880; //5MB
	
	let cloneObj = $(".uploadDiv").clone(); //입력전의 ajax 파일 업로드 객체
	
	let uploadResult = $(".uploadResult #cardRow");
	
	let formData = new FormData();
	//FormData는 자바스크립트 코어 객체로 <form>태그의 DOM을 나타냄 <form></form>의 DOM
	let inputFile = $("input[name='uploadFile']"); //배열 형식으로 반환
	let files = inputFile[0].files; //files 속성은 input태그에서 선택한 복수개의 파일 정보를 가짐
	console.log(files);
	
	for(let i = 0; i < files.length; i++) {
		
		if(!checkExtension(files[i].name, files[i].size)){
			//선택된 파일 files[i]의 name과 size속성
			return false;
		}
		
		formData.append("uploadFile",files[i]);
		//formData DOM객체에 name속성은 uploadFile인 <input>태그를 만들고 선택한 file객체를 가진 엘리먼트를 추가
		
	}
	
	$.ajax({
		url : 'uploadAjaxAction',
		type : 'POST', //필수
		processData : false, //필수
		contentType : false, //필수
		data : formData, //서버로 보내는 데이터로 <form>엘리먼트 DOM
		success : function(result) {
			console.log(result);
			
			showUploadedFile(result);
			
			$(".uploadDiv").html(cloneObj.html()); //파일 업로드창 초기화
			
		},
		error : function() {
			alert("upload fail");
		}
	});
	
	function checkExtension(fileName,fileSize) {
		
		if(fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)) {
			//test는 RegExp코어 객체의 메서드로 정규식에 지정된 단어 포함 여부 체크
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	function showUploadedFile(uploadResultArr) {
		
		//uploadResultArr 는 서버로 부터 받은 JSON객체 타입(list형식--배열형식)의 result값
		
		let str = ""; //HTML을 만들 문자열
		
		$(uploadResultArr).each(function(i, obj){
			//JQuery의 each문 , i는 색인번호이고 obj는 uploadResultArr를 구성하고 있는 원소(AttachFileDTO)
			if(!obj.image) {
				//한글이나 공백등이 URL에 포함되어 있을시를 해결 encodeURIComponent()
				let fileCallPath = encodeURIComponent( obj.uploadPath+"/"+ obj.uuid + "_" + obj.fileName);
				
				str += "<div class='card col-md-3'>";
				str += "<div class='card-body'>";
				str += "<p class='mx-auto' style='width:90%;' title='"+ obj.fileName +"'>";
				str += "<a href='download?fileName="+ fileCallPath +"'>";
				str += "<img class='mx-auto d-block' src='../images/attach.png'>";
				str += "</a>";
				str += "</p>";
				str += "</div>";
				str += "</div>";
			}
			else {
				//str += "<p>"+ obj.fileName +"</p>"; 이름만 표시 
				//썸네일 이미지 를 보여주기
				//공백 ,한글 등으로 된 경로를 처리 encodeURIComponent
				let fileCallPath = encodeURIComponent(obj.uploadPath+ "/s_" + obj.uuid + "_" + obj.fileName);
				str += "<div class='card col-md-3'>";
				str += "<div class='card-body'>";
				str += "<p class='mx-auto' style='width:90%;' title='"+ obj.fileName +"'>";
				str += "<img class='mx-auto d-block' src='display?fileName="+ fileCallPath +"'>";
				str += "</p>";
				str += "</div>";
				str += "</div>";
				
			}
		});
		
		uploadResult.append(str);
	}
});
</script>


</body>
</html>
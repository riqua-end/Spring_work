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

<!-- list화면 표시 -->
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
				<div class="collapse navbar-collapse d-md-block" id="collapsibleVertical">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-home" style="font-size:30px;color:white;"></i></a></li>
						<li class="nav-item"><a class="nav-link" href="register">게시물 등록</a></li>
						<li class="nav-item"><a class="nav-link" href="#">리스트</a></li>
						<li class="nav-item"><a class="nav-link" href="#">도움말</a></li>
					</ul>
				</div>
			</nav>
		</div><!-- col-md-2 -->
		<div class="col-md-10">
			<div id="submain">
				<h4 class="text-center wordArtEffect text-success">게시글 내용</h4>
				<form>
					<div class="form-group">
						<label for="bno">번호 : </label>
						<input type="text" class="form-control" id="bno" name="bno" readonly value='<c:out value="${board.bno}"/>' />
					</div>
					<div class="form-group">
						<label for="title">제목 : </label>
						<input type="text" class="form-control" id="title" name="title" readonly value='<c:out value="${board.title}"/>' />
					</div>
					<div class="form-group">
						<label for="content">내용 : </label>
						<textarea class="form-control" id="content" name="content" rows="10" readonly><c:out value="${board.content}"/></textarea>
					</div>
					<div class="form-group">
						<label for="writer">작성자 : </label>
						<input type="text" class="form-control" id="writer" name="writer" readonly value='<c:out value="${board.writer}"/>' />
					</div>
				</form>
				
				<!-- jquery사용 이전 
				<a href="modify?bno=<c:out value="${board.bno}"/>" class="btn btn-info">Modify</a>
				
				<a href="list" class="btn btn-danger">List</a>
				-->
				
				<!-- jquery와 data-xxx 속성 사용 
				<button type="button" data-oper='modify' class="btn btn-info"
				 onclick="location.href='modify?bno=<c:out value="${board.bno}"/>'">수정
				</button>&nbsp;&nbsp;
				<button data-oper='list' class="btn btn-danger"
				onclick="location.href='list'">게시판 목록</button>
				-->
				
				<!-- 버튼과 form을 사용 -->
				
				<button data-oper='modify' class="btn btn-info">Modify</button>&nbsp;&nbsp;
				<button data-oper='list' class="btn btn-danger">게시판목록</button>
				
				<!-- 버튼 클릭을 처리하기 위한 form,안보이는 창(나중 페이지 정보 댓글 정보 등을 같이 처리 -->
				<form id='operForm' action="modify" method="get">
					<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
					<!-- 페이지 정보를 추가 -->
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
					<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
					<!-- 검색처리 추가 -->
					<input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
				</form>
			</div><!-- submain -->
		</div><!-- col-md-10 -->
	</div><!-- row -->
</div>
		

<%@ include file="../includes/footer.jsp" %>

<%-- 외부 js파일 임포트 --%>
<script src="../js/reply.js"></script>

<script>
$(function(){
	let operForm = $("#operForm");
	$("button[data-oper='modify']").on("click",function(e){
		operForm.attr("action", "modify").submit();
	});
	$("button[data-oper='list']").on("click",function(e){
		operForm.find("#bno").remove();
		//id가 bno인 DOM을 찾아서 제거
		operForm.attr("action","list");
		operForm.submit();
	});
});
</script>

<script>
$(function(){
	//댓글처리 테스트(테스트 이후에는 이 자바스크립트 영역은 주석 처리)
	console.log("======================");
	console.log("JS TEST");
	
	let bnoValue = '<c:out value = "${board.bno}"/>';
	//replyService 객체의 add속성인 function add(reply,callback,error)메서드의 파라메터인 reply와 callback에 전달
	/*
	replyService.add(
		{reply:"JS Test",replyer:"tester",bno:bnoValue},
		function(result) {
			alert("RESULT : " + result);
		}
	);
	*/
	
	/*
	replyService.getList({bno:bnoValue,page:1}, function(list){
		//list는 getList에서 받는 성공시 데이터\
		for(var i = 0, len = list.length || 0; i < len; i++) {
			console.log(list[i]);
		}
	});
	*/
	
	replyService.remove(15,
		function(count){
			console.log(count);
			if (count === "success") {
				alert("REMOVED");
			}
		},
		function(err){
			alert("ERROR....");
		}
	);
});
</script>
</body>
</html>
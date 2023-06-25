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
		<div class="col-md-2 pl-0">
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
						<li class="nav-item"><a class="nav-link" href="list">리스트</a></li>
						<li class="nav-item"><a class="nav-link"
						 href='modify?bno=<c:out value="${board.bno}" />'>수정</a></li>
					</ul>
				</div>
			</nav>
		</div><!-- col-md-2 -->
		<div class="col-md-10">
			<div id="submain">
				<h4 class="text-center wordArtEffect text-success">게시물 등록</h4>
				<form action="register" method="post" id="freg" name="freg" role="form">
					<div class="form-group">
						<label for="title">제목 :</label>
						<input type="text" class="form-control" id="title" placeholder="Enter title" name="title" required>
					</div>
					<div class="form-group">
						<label for="content">내용 :</label>
						<textarea class="form-control" id="content" placeholder="Enter content" name="content" rows="10" required></textarea>
					</div>
					<div class="form-group">
						<label for="writer">작성자 :</label>
						<input type="text" class="form-control" id="writer" name="writer">
					</div>
					<button type="submit" class="btn btn-success">작성</button>&nbsp;&nbsp;
					<button type="reset" class="btn btn-danger">취소</button>&nbsp;&nbsp;
					<a id="listLink" href="list" class="btn btn-primary">목록보기</a>
				</form>
			</div>
		</div><!-- col-md-10 -->
		
	</div><!-- row -->
</div>

<%@ include file="../includes/footer.jsp" %>
</body>
</html>
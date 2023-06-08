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

<%--지시자 include는 소스 포함하여 컴파일시에 포함 시킴,액션태그 include는 실행 시점에 include --%>
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
				<h4 class="text-center wordArtEffect text-success">게시판</h4>
				<div>
					<button type="button" class="btn btn-primary float-right mb-3" id="regBtn">게시물 등록</button>
				</div>
				
				<!-- table-responsive-md로 RWD해결 -->
				<div class="table-responsive-md">
					<table id="boardTable" class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>수정일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="board">
								<tr>
									<td class="bno"><c:out value="${board.bno}"/></td>
									<td>
										<a class="move" href='get?bno=<c:out value="${board.bno}"/>'>
											<c:out value="${board.title}"/>
										</a>
									</td>
									<td>
										<c:out value="${board.writer}"/>
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd" value="${board.regDate}"/>
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div><!-- table-responsive-md -->
			</div><!-- submain -->
		</div><!-- col-md-10 -->
	</div><!-- row -->
</div>

<%@ include file="../includes/footer.jsp" %>
</body>
</html>
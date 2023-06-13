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
			
			<!-- 페이지 표시 영역 -->
			<ul class="pagination justify-content-center" style="margin:20px 0">
				<c:if test="${pageMaker.prev}">
					<li class="page-item">
						<a class="page-link" href="${pageMaker.startPage - 1}">Prev</a><!-- 앞페이지 마지막 -->
					</li>
				</c:if>
				<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li class="page-item ${pageMaker.cri.pageNum == num ? 'active':''}">
						<a class="page-link" href="${num}">${num}</a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next}">
					<li class="page-item">
						<a class="page-link" href="${pageMaker.endPage + 1}">Next</a> <!-- 다음페이지 처음 페이지 표시 -->
					</li>
				</c:if>
			</ul>
			
			<!-- 페이지 번호 클릭시 콜트롤러로 public void list(Criteria cri, Model model)로 요청하는 form
					나중에 검색데이터도 여기서 같이 처리 -->
			<form id='actionForm' action="list" method='get'>
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
			</form>
		</div><!-- col-md-10 -->
	</div><!-- row -->
</div>
<%@ include file="../includes/messageModal.jsp" %>
<%@ include file="../includes/footer.jsp" %>

<script>
//게시물 등록 처리
$(document).ready(function() {
	
	//EL의 result는 RedirectAttributes의 rttr.addFlashAttribute("result" , board.getBno());로 전달된 값
	let result = '<c:out value="${result}"></c:out>';
	
	console.log("result : " + result);
	
	checkModal(result);
	
	history.replaceState({},null,null);
	//현재의 히스토리를 전부 비워 줍니다.
	//뒤로가기 방지
	
	$("#regBtn").on("click",function(){
		self.location = "register";
	});
	
	function checkModal(result) {
		
		if (result == "") {
			return;
		}
		if (parseInt(result) > 0) {
			$(".modal-body #mbody").html("게시글 : " + parseInt(result) + "번이 등록 되었습니다.");
		}
		else if (result == "success") {
			$(".modal-body #mbody").html("게시글 수정/삭제가 처리 되었습니다.");
		}
		else {
			return;
		}
		
		$("#messageModal").modal("show"); //선택한 modal 엘리먼트를 보여주기
	}
});
</script>

<script>
//pagination처리
$(document).ready(function(){
	
	let actionForm = $("#actionForm");
	
	$(".page-item a").on("click",function(e){
		e.preventDefault(); //a의 원래 기능을 취소
		console.log('page 번호 클릭');
		actionForm.find("input[name='pageNum']")
		.val($(this).attr("href"));
		//find(selector)메서드는 자식 엘리먼트에서 selector에 해당하는 엘리먼트를 선택
		//pageNum이 name인 input의 value에 클릭한 a의 href값(페이지 번호)를 넣어줌
		//this는 이벤트가 일어난 객체이므로 <a>가 됨
		actionForm.submit(); //submit(),reset()은 form의 이벤트
	});
	
});
</script>
</body>
</html>
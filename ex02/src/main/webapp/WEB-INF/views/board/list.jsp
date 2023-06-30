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


<%--지시자 incude는 소스 포함하여 컴파일시에 포함 시킴,액션태그 include는 실행 시점에 include --%>

<%@include file="../includes/header.jsp"%>

<!-- list화면 표시 -->
<div class="container mt-4 mb-4" id="mainContent">
	<div class="row">
		<div class="col-md-2 pl-0">
			<h4 class="wordArtEffect text-success pl-4">메뉴</h4>
			<nav class="navbar bg-dark navbar-dark container">
				<!-- 수직 메뉴 d-md-none으로 컬랩스 해결-->
				<button class="navbar-toggler d-md-none" type="button"
						data-toggle="collapse" data-target="#collapsibleVertical">
						<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse d-md-block"  id="collapsibleVertical">
					<ul class="navbar-nav">
						<li class="nav-item">
							<a class="nav-link" href="#"> 
								<i class="fas fa-home" style="font-size: 30px; color: white;"></i>
							</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="register">게시물등록</a></li>
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
				
				<!-- 검색 기능 엘리먼트 -->
				<form id='searchForm' action="list" method='get' class="mb-3">
					<div class="input-group">
						<div class="input-group-prepend">
							<select name='type'>
								<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>-------</option>
								<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : ''}"/>>제목</option>
								<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : ''}"/>>내용</option>
								<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''}"/>>작성자</option>
								<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>>제목	or 내용</option>
								<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : ''}"/>>제목 or 작성자</option>
								<option value="TWC"	<c:out value="${pageMaker.cri.type eq 'TWC' ? 'selected':''}"/>>제목 or 내용 or 작성자</option>
							</select>
						</div> <!-- prepend -->
						
						<input class="form-control" type='text' name='keyword'	value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
						<input	type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
						<input	type='hidden' name='amount'	value='<c:out value="${pageMaker.cri.amount}"/>' />
						
						<div class="input-group-append" id="button-addon4">
							<!-- button은 default type이 submit -->
							<button id="search" class='btn btn-outline-primary'>Search</button>
							<button id="clear" class="btn btn-outline-info btn-clear" type="button">Clear</button> <!-- list기능으로 복귀 -->
						</div>
					</div><!-- init-group -->
				</form>
				
				<!-- table-responsive-md로 RWD핵 -->
				<div class="table-responsive-md">
					<table id="boardTable" class="table table-bordered table-hover ">
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
									<td class="bno"><c:out value="${board.bno}" /></td>
									<td>
										<!-- 페이지 이벤트 처리 전 
										<a class="move" href='get?bno=<c:out value="${board.bno}"/>'>
											<c:out value="${board.title}" />
										</a>
										-->	
										<!-- 페이지를 조회페이지로 보내기 처리(페이지처리에 추가로 bno를 같이 보내줌 -->
										<!-- 댓글수 미고려 
										<a class='move' href='<c:out value="${board.bno}"/>'>
												<c:out value="${board.title}" />												
										</a>
										-->	
										<!-- 댓글수 고려 -->	
										<a class='move' href='<c:out value="${board.bno}"/>'>
											<c:out value="${board.title}" />
											<span class="badge badge-secondary badge-pill float-right">
												<c:out value="${board.replyCnt}" />
											</span>
										</a>						
									</td>
									<td><c:out value="${board.writer}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regDate}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div><!-- table-responsive-md -->
			</div><!-- submain -->
			
			<!-- 페이지 표시 영역 -->
			<ul class="pagination justify-content-center" style="margin: 20px 0">
				<c:if test="${pageMaker.prev}">
					<li class="page-item">
						<a class="page-link" href="${pageMaker.startPage - 1}">Prev</a> <!-- 앞페이지 마지막 -->
					</li>
				</c:if>
				<c:forEach var="num" begin="${pageMaker.startPage}"	end="${pageMaker.endPage}">
					<li	class="page-item ${pageMaker.cri.pageNum == num ? 'active':''}">
						<a class="page-link" href="${num}">${num}</a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next}">
					<li class="page-item">
						<a class="page-link" href="${pageMaker.endPage + 1}">Next</a> <!-- 다음페이지 처음 페이지 표시 -->
					</li>
				</c:if>		
			</ul>
			
			<!-- 페이지 번호 클릭시 콘트롤라로 (public void list(Criteria cri, Model model)) 로 요청하는 form, 나중에 검색 데이터도 여기서 같이 처리  -->
			<form id='actionForm' action="list" method='get'>
				<input type='hidden' name='pageNum'	value='${pageMaker.cri.pageNum}'> 
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
				<!-- 검색처리 추가 -->
				<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type }"/>'> 
				<input type='hidden' name='keyword'	value='<c:out value="${ pageMaker.cri.keyword }"/>'>
			</form>
			
		</div> <!-- col-md-10 -->
	</div> <!-- row -->
</div>	

<%@include file="../includes/messageModal.jsp"%>
<%@include file="../includes/footer.jsp"%>

<script>
//게시물 등록 처리
$(document).ready(function(){
	
	//EL의 result는 RedirectAttributes의 rttr.addFlashAttribute("result", board.getBno());로 전달된 값
	let result = '<c:out value="${result}"></c:out>';
	
	console.log("result : " + result);
	
	checkModal(result);
	
	history.replaceState({}, null, null);
	//현재의 히스토리를 전부 비워 줍니다.
	//뒤로가기 방지
	
	$("#regBtn").on("click", function(){
		self.location = "register";
	});
	
	//페이지 처리
	let actionForm = $("#actionForm");	
	
	$(".page-item a").on("click",function(e){		
		e.preventDefault(); //a의 원래 기능을 취소
		console.log('page 번호 클릭');
		actionForm
		.find("input[name='pageNum']")
		.val($(this).attr("href"));
		//find(selector)메서드는 자식 엘리먼트에서 selector에 해당하는 엘리먼트를 선택 
		//pageNum이 neme인 input의 value에 클릭한 a의 href값(페이지 번호)을 넣어줌
		//this는 이벤트가 일어난 객체이므로 <a>가 됨
		actionForm.attr("action", "list");
		actionForm.submit(); //submit(),reset()은 form의 이벤트
	});
	
	$(".move").on("click",function(e){
		e.preventDefault(); //a의 원래 기능을 취소
		console.log('page 번호 클릭');
		actionForm
		.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");  //게시물번호 bno를 actionForm에 추가
		actionForm.attr("action", "get"); //콘트롤라 get으로 요청
		actionForm.submit();
	});
	
	//검색 처리
	let searchForm = $("#searchForm");
	
	$("#searchForm #search").on("click",function(e) {

				if (!searchForm.find("option:selected").val()) {
					alert("검색종류를 선택하세요");
					return false;
				}

				if (!searchForm.find("input[name='keyword']").val()) {
					alert("키워드를 입력하세요");
					return false;
				}

				searchForm.find("input[name='pageNum']").val("1");  //검색시는 1페이지를 pageNum으로 보냄
				e.preventDefault();
				searchForm.submit();

	});
	
	$('#searchForm #clear').click(function(e){

        searchForm.empty().submit(); //일반 리스트로 처리

   });				
		
	function checkModal(result) {
		if (result == "") {
			return;
		}
		if(parseInt(result) > 0) {
			$(".modal-body #mbody").html("게시글 : " + parseInt(result) + "번이 등록 되었습니다");
		}
		else if (result == "success") {
			$(".modal-body #mbody").html("게시글 수정/삭제가 처리 되었습니다");
		}
		else {
			return;
		}
		
		$("#messageModal").modal("show"); //선택한 modal엘리먼트를 보여주기
	}
});
</script>


</body>
</html>
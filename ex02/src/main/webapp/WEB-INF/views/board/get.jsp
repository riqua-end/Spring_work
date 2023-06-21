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
				
				<!-- 댓글 아이콘 영역 -->
				<div class="row mt-4">
					<div class="col-md-12 clearfix">
						<i class="fas fa-reply fa-2x"></i> Reply <!-- 댓글 아이콘 -->
						<button id='addReplyBtn' class='btn btn-outline-primary float-right'>
							New Reply
						</button>
					</div>
				</div>
				
				<!-- 댓글 리스트 영역 -->
				<div class="row mt-2">
					<div class="col-md-12">
						<ul class="chat list-group">
							<!-- 이 영역에 댓글의 목록을 자바 스크립트로 갯수 만큼 반복처리하여 만듦 -->
							<!-- 임시 데이터 -->
							<li class='list-group-item clearfix' data-rno='12'>
								<strong class='text-primary'>user00</strong>
								<small class='float-right text-mute'>2023-05-03</small>
								<p>댓글 내용입니다.</p>
							</li>
						</ul>
					</div>
				</div>
			</div><!-- submain -->
		</div><!-- col-md-10 -->
	</div><!-- row -->
</div>
		

<%@ include file="../includes/replyModal.jsp" %>
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
	
	/*
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
	*/
	
	/*
	replyService.update({
		rno : 13,
		bno : bnoValue,
		reply : "Modified Reply....."
	}, function(result) {
		alert("수정 완료...");
	
	});
	*/
	
	/*
	//댓글 조회처리
	replyService.get(13,function(data){
		console.log(data);
	});
	*/
});
</script>

<script>
$(function(){
	//댓글 처리 스크립트 부분
	let bnoValue = '<c:out value="${board.bno}"/>';
	let replyUL = $(".chat"); //댓글 콘테이너인 ul로 이 안에 댓글 항목을 추가
	
	showList(1); //댓글 리스트 보여주기 함수
	
	function showList(page) {
		console.log("show list" + page);
		
		replyService.getList({bno:bnoValue,page: page||1 },
			function(list) {
				//list는 서버에서 ArrayList(배열형태,요소는 reply객체의 JSON배열)
				//자바 스크립트에서는 JS배열객체 처럼 사용
				let str = "";
				if(list == null || list.length == 0) {
					replyUL.html("");
					return;
				}
				for (let i = 0, len = list.length || 0; i < len; i++) {
					str += "<li class='list-group-item clearfix' data-rno='"+list[i].rno+"'>";
					str += "<strong class='text-primary'>" + list[i].replyer + "</strong>";
					str += "<small class='float-right text-mute'>" + replyService.displayTime(list[i].replyDate) + "</small>";
					str += "<p>" + list[i].reply + "</p>";
					str += "</li>";
				}
				
				replyUL.html(str);
				
			} //function()		
		); //getList()
	} //showList()
	
	//댓글 처리 모달창 처리 및 댓글처리 이벤트 처리
	let modal = $("#myReplyModal"); //replyModal의 modal DOM
	//입력요소를 DOM객체로 
	let modalInputReply = modal.find("input[name='reply']"); //find는 후손중에서 선택
	let modalInputReplyer = modal.find("input[name='replyer']");
	let modalInputReplyDate = modal.find("input[name='replyDate']");
	//모달창의 버튼 DOM
	let modalModBtn = $("#modalModBtn");
	let modalRemoveBtn = $("#modalRemoveBtn");
	let modalRegisterBtn = $("#modalRegisterBtn");
	
	//댓글 추가 버튼 클릭 이벤트 처리
	$("#addReplyBtn").on("click",function(e){
		
		
		modal.find("input").val(""); //input의 값을 초기화
		//modal.find("input[name='replyer']").val(replyerS); //로그인한 사용자로 작성자 고정
		modalInputReplyDate.closest("div").hide(); //날짜 입력 DOM은 감춤
		modal.find("button[id != 'modalCloseBtn']").hide(); //나가기만 보임
		
		modalRegisterBtn.show(); //등록버튼 다시 보이게
		
		$(".replyModal").modal("show");
		
	});
	
	//나가기버튼 이벤트 처리
	$("#modalCloseBtn").on("click",function(e){
		modal.modal('hide');
	});
	
	//댓글 등록 버튼 클릭 이벤트 처리 및 모달창 닫고 댓글 리스트창 보여주기
	modalRegisterBtn.on("click",function(){
		//reply.js의 replyService객체의 add메서드 속성 사용하기
		//댓글 생성에 필요한 데이터를 자바스크립트 객체로 보내줌
		let reply = {
				reply : modalInputReply.val(),
				replyer : modalInputReplyer.val(),
				bno : bnoValue
		};
		replyService.add(reply,function(result){
			
			alert(result);
			//등록 후 초기화
			modal.find("input").val("");
			modal.modal("hide");
			
			showList(1); //등록 후 댓글 목록 보이게 함
		});
	});
	
	//댓글 조회 이벤트(modify와 remove를 위해 조회)
	$(".chat").on("click","li",function(e){
		//li는 .chat의 자식(후손)
		let rno = $(this).data("rno");
		//이벤트가 일어난 li는 this
		//data(data-의 값)은 data-값으로 되어있는 DOM선택
		
		replyService.get(rno, function(reply){
			
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer);
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
			.attr("readonly","readonly");
			modal.data("rno",reply.rno);
			//data-rno속성을 reply.rno로 추가
			
			modal.find("button[id !='modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			$(".replyModal").modal("show");
		});
	});
	
	//댓글 수정 이벤트 처리
	modalModBtn.on("click",function(e){
		
		var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
		
		replyService.update(reply, function(result){
			
			alert(result);
			modal.modal("hide");
			showList(1); //업데이트 이후에는 댓글리스트 보여주기
		});
	});
});
</script>
</body>
</html>
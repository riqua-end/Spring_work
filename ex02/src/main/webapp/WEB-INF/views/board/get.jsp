
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

<style>
.card img {
	width : 150px;
	height : 150px;
}
</style>
</head>
<body>


<%@include file="../includes/header.jsp"%>

<div class="container mt-4 mb-4" id="mainContent">
	<div class="row">		
		<div class="col-md-2">
			<h4 class="wordArtEffect text-success pl-4">메뉴</h4>
			<nav class="navbar bg-dark navbar-dark container">
				<!-- RWD의 화면 축소시 나타나는 메뉴 버튼(상병계급장) -->
				<!-- d-md-none은 메뉴가 감추어지지 아노고 펼쳐지는 것 예방 -->
				<button class="navbar-toggler d-md-none" type="button"
					data-toggle="collapse" data-target="#collapsibleVertical">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse d-md-block"
					id="collapsibleVertical">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="#"> <i
								class="fas fa-home" style="font-size: 30px; color: white;"></i>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="list">게시판
								목록</a></li>
						<li class="nav-item"><a class="nav-link"
							href='modify?bno=<c:out value="${board.bno }"/>'>수정</a></li>
					</ul>
				</div>
			</nav>
		</div>
		<div class="col-md-10">
			<div id="submain">
				<h4 class="text-center wordArtEffect text-success">게시글 내용</h4>
				<form>
					<div class="form-group">
							<label for="bno">번호:</label> 
							<input type="text"
								class="form-control" id="bno" name="bno" readonly
								value='<c:out value="${board.bno }"/>' />
						</div>
						<div class="form-group">
							<label for="title">제목:</label> 
							<input type="text"
								class="form-control" id="title" name="title" readonly
								value='<c:out value="${board.title }"/>' />
						</div>
						<div class="form-group">
							<label for="content">내용:</label>
<textarea class="form-control" id="content" name="content"	rows="10" readonly>
<c:out value="${board.content}" />
</textarea>
						</div>
						<div class="form-group">
							<label for="writer">작성자:</label> 
							<input type="text"
								class="form-control" id="writer" name="writer" readonly
								value='<c:out value="${board.writer }"/>' />
						</div>
				</form>
				
				<!-- jquery사용 이전  -->	
				<!-- 		
        		<a class="btn btn-info" href="modify?bno=<c:out value="${board.bno}"/>">Modify</a>&nbsp;&nbsp;        		
        		<a class="btn btn-danger" href="list">List</a>        		
        		 -->	
        		  
        		 <!-- jquery와 data-xxx속성 사용(버튼으로 바로 처리) -->
        		 <!--  
        		 <button type="button" data-oper='modify' class="btn btn-info" 
					onclick="location.href='modify?bno=<c:out value="${board.bno}"/>'">
        		 	Modify
        		 </button>&nbsp;&nbsp;
				 <button data-oper='list' class="btn btn-danger" onclick="location.href='list'">
				 	게시판목록
				 </button>
				  -->
				  
				  <!-- 버튼과 form을 사용  --> 
				  <!-- 시큐리티 미적용 -->
				  <!--  
				  <button data-oper='modify' class="btn btn-info">Modify</button>&nbsp;&nbsp;
				  -->
				  
				  <!-- 시큐리티 적용 로그인아이디와 게시글 작성자 동일시만 버튼 보임 -->
				  <sec:authentication property="principal" var="pinfo"/>	
				  <!-- EL안에서는 pinfo사용 -->
				  <sec:authorize access="isAuthenticated()">
				  	<c:if test="${pinfo.username eq board.writer}">
				  		<button data-oper='modify' class="btn btn-info">Modify</button>
				  	</c:if>
				  </sec:authorize>
				  	
				  <button data-oper='list' class="btn btn-danger">게시판목록</button>
				  
				  <!-- 버튼 클릭을 처리하기 위한 form,안보이는 창(나중 페이지 정보 댓글 정보 등을 같이 처리 -->
				  <form id='operForm' action="modify" method="get">
				  	<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
				  	<!-- 페이지 정보를 추가 -->	
					<input	type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'> 
					<input	type='hidden' name='amount' 	value='<c:out value="${cri.amount}"/>'>
					<!-- 검색처리 추가 -->
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
  					<input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>	
				  </form>
				  
				  <!-- 첨부 파일 처리 영역 -->
				  <div class='uploadResult mt-3'>
				  	<div class='row' id='cardRow'>
					</div>  			
				  </div>
				  
				  <!-- 댓글 아이콘과 만들기 영역 -->
				  <div class="row mt-4">
				  	<div class="col-md-12 clearfix">
				  		<i class="fas fa-reply fa-2x"></i> Reply  <!-- 댓글 아이콘 -->
				  		<!-- 시큐리티 미적용 -->
				  		<!-- 
				  		<button id='addReplyBtn' class='btn btn-outline-primary float-right'>
				  			New Reply
				  		</button>
				  		 -->				  		 
				  		 <!-- 시큐리티 적용 -->
				  		 <sec:authorize access="isAuthenticated()">
				  		 	<button id='addReplyBtn' class='btn btn-outline-primary float-right'>
				  		 		New Reply
				  		 	</button>
				  		 </sec:authorize>
				  	</div>
				  </div>
				  
				  <!-- 댓글 리스트 영역 -->
				  <div class="row mt-2">
				  	<div class="col-md-12">
				  		<ul class="chat list-group">
				  			<!-- 이 영역에 댓글의 목록을 자바 스티립트로 갯수 만큼 반복처리하여 만듬 -->
				  			<!-- 임시 데이터 -->
				  			<!--  
				  			<li class='list-group-item clearfix' data-rno='12'>
				  				<strong class='text-primary'>user00</strong>
								<small class='float-right text-mute'>2023-05-03</small>
								<p>댓글 내용입니다</p>
				  			</li>
				  			-->
				  		</ul>
				  	</div>
				  </div> <!-- row mt-2 -->
				  
				  <!-- 댓글용 페이지 표시 -->
				  <div id='replyPage'>
				  </div>
				  
			</div><!-- submain -->
		</div><!-- col-md-10 -->
	</div><!-- row -->
</div>

<%@ include file="../includes/replyModal.jsp"%>
<%@include file="../includes/footer.jsp"%>
<%@ include file="../includes/imageModal.jsp"%>

<%--외부 js파일 임포트 --%>
<script src="../js/reply.js"></script>

<script>
$(document).ready(function(){	
	//게시글 조회 버튼 처리 
	//modify,list 처리 버튼 
	
	let operForm = $("#operForm");
	
	$("button[data-oper='modify']").on("click", function(e){
		operForm.attr("action", "modify").submit();
	});
	$("button[data-oper='list']").on("click", function(e){
		operForm.find("#bno").remove();
		//id가 bno인 DOM을 찾아서 제거
		operForm.attr("action", "list");
		operForm.submit();
	});
});
</script>

<script>
$(document).ready(function(){
	//댓글처리 테스트 (테스트 이후에는 이 자바스크립트 영역은 주석 처리) 
	console.log("=========================");
	console.log("JS TEST");
	
	let bnoValue = '<c:out value="${board.bno}"/>';
	
	/*
	//replyServicer객체의 add속성인 function add(reply,callback,error)메서드의 파라메터인 reply와 callback에 전달	
	replyService.add(
				{reply:"JS Test", replyer:"tester", bno:bnoValue},
				function(result) {
					alert("RESULT: " + result);
				}
	);
	*/
	
	/*
	replyService.getList({bno:bnoValue, page:1}, function(list){
	      //list는 getList에서 받는 성공시 데이터
		  for(var i = 0,  len = list.length||0; i < len; i++ ){
		    console.log(list[i]);
		  }
	});
	*/
	
	/*
	replyService.remove(56,
		function(count) {
		 	console.log(count);
		 	if (count === "success") {
			     alert("REMOVED");
			}
		},
		function(err) {
			alert("ERROR....");
		}
	);
	*/
	
	/*
	replyService.update({
		  rno : 65,
		  bno : bnoValue,
		  reply : "Modified Reply...."
		}, 
		function(result) {

		  alert("수정 완료...");

	});
	*/
	
	/*
	replyService.get(68,function(data){
		console.log(data);
	});
	*/
	
});
</script>


<script>
$(document).ready(function(){
	//댓글처리 스크립트 부문
	let bnoValue = '<c:out value="${board.bno}"/>';
	let replyUL = $(".chat"); //댓글 콘테이너인 ul로 이 안에 댓글항목을 추가
	
	let pageNum = 1;
	let replyPageFooter = $("#replyPage");
	
	showList(1); //댓글 리스트 보여주기 함수
	
	function showList(page) {
		console.log("show list " + page);
		
		replyService.getList({bno:bnoValue,page: page|| 1 },
			//function(list) {			
				//list는 서버에서 ArrayList(배열형태,요소는 reply객체의 JSON배열) 자바스크립트에서는 JS배열객체 처럼 사용
			function(rpDto) {	
				//rpDto는 PageDTO객체
				let replyCnt = rpDto.replyCnt;
		    	let list = rpDto.list;
			    console.log("replyCnt: "+ replyCnt );
			    console.log("list: " + list);
			    
			    if(page == -1){
				      pageNum = Math.ceil(replyCnt/10.0);  //마지막 페이지로 이동
				      showList(pageNum);
				      return;
				}
			    
				let str = "";
				
				if(list == null || list.length == 0) {
					replyUL.html("");
					return;
				}
				for (let i = 0, len = list.length || 0; i < len; i++) {
					str += "<li class='list-group-item clearfix pb-0' data-rno='"+list[i].rno+"'>";
					str += "<strong class='text-primary'>" + list[i].replyer + "</strong>";
					//str += "<small class='float-right text-mute'>" + list[i].replyDate + "</small>";
					str += "<small class='float-right text-mute'>" + replyService.displayTime(list[i].replyDate) + "</small>";
					str += "<p>" + list[i].reply + "</p>";
					str += "</li>";
				}
				
				replyUL.html(str);
				
				//댓글페이지 표시 처리 함수 호출
				showReplyPage(replyCnt);
				
			} //function()
		); //getlist()
	} //ㄴshowliat()
	
	//let pageNum = 1;
	//let replyPageFooter = $("#replyPage");
	
	function showReplyPage(replyCnt) {
		
		let endNum = Math.ceil(pageNum / 10.0) * 10;
		
		let startNum = endNum - 9;
		
		let prev = startNum != 1;
	    let next = false;
	    
	    if(endNum * 10 >= replyCnt) {
	    	endNum = Math.ceil(replyCnt/10.0);
	    }
	    
	    if(endNum * 10 < replyCnt) {
	    	next = true;
	    }
	    
	    let str = "<ul class='pagination justify-content-center' style='margin: 20px 0'>";
	    
	    if(prev) {
	    	str += "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
	    }
	    
	    for(let i = startNum ; i <= endNum; i++) {
	    	let active = pageNum == i ? "active": "";
	    	str+= "<li class='page-item " +active+" '><a class='page-link' href='"+ i +"'>"+ i +"</a></li>";
	    }
	    
	    if(next) {
	    	str += "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
	    }
	    
	    str += "</ul>";
	    
	    console.log(str);
	    
	    replyPageFooter.html(str);
	}
	
	//댓글처리 모달창 처리 및 댓글처리 이벤트 처리
	let modal = $("#myReplyModal"); //replyModal의 modal DOM
	//입력요소를 DOM객체로
	let modalInputReply = modal.find("input[name='reply']"); //find는 후손중에서 선택
    let modalInputReplyer = modal.find("input[name='replyer']");
    let modalInputReplyDate = modal.find("input[name='replyDate']");
    //모달창의 버튼 DOM
    let modalModBtn = $("#modalModBtn");
    let modalRemoveBtn = $("#modalRemoveBtn");
    let modalRegisterBtn = $("#modalRegisterBtn");
    
    //댓글 작성자를 로그인한 username으로 지정
    let replyer = null;
    //sec EL문을 자바스크립트에서 사용
    <sec:authentication property="principal" var="pinfo"/>
    <sec:authorize access="isAuthenticated()">
    	replyer ='<sec:authentication property="principal.username"/>';
    	//이메일 주소가 encode되어 나옴
    	let replyers = "${pinfo.username}";
    	//정상 이메일 문자열
    </sec:authorize>
    	
    //ajax csrf설정
    let csrfHeaderName = "${_csrf.headerName}";
    let csrfTokenValue = "${_csrf.token}";
    
    $(document).ajaxSend(function(e,xhr,options){
    	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);	
    });
    
    
    
    
    //댓글 생성 버튼 클릭 이벤트 처리
    $("#addReplyBtn").on("click", function(e){
    	
		modal.find("input").val(""); //input의 값을 초기화	    
	    //댓글 작성자를 로그인 username으로 지정
	    modal.find("input[name='replyer']").val(replyers);
		modalInputReplyDate.closest("div").hide(); //날짜 입력DOM은 감춤
	    modal.find("button[id !='modalCloseBtn']").hide(); //나가기만 보임
	      
	    modalRegisterBtn.show(); //등록버튼 다시 보이게
	     
	    $(".replyModal").modal("show");			   
	      
	 });
    
  //나가기버튼 이벤트 처리
    $("#modalCloseBtn").on("click", function(e){

		modal.modal('hide');
	});
    
    //댓글 등록 버튼 클릭 이벤트 처리 및 모달창 닫고 댓글 리스트창 보여주기
    modalRegisterBtn.on("click",function(e){
    	//reply.js의 replyService객체의 add메서드 속성 사용하기
    	//댓글 생성에 필요한 데이터를 자바스크립트 객체로 보내줌
    	let reply = {
	            reply: modalInputReply.val(),
	            replyer:modalInputReplyer.val(),
	            bno:bnoValue
	    };
    	
    	replyService.add(reply, function(result){
    		 alert(result);
    		 //등록후 초기화
    		 modal.find("input").val("");
		     modal.modal("hide");
		     
		     //showList(1); //등록후 댓글 목록 보이게 함(페이지 미고려)
		     showList(-1); //페이지 고려시는 등록후 제일 마지막 페이지의 마지막 댓글로 처리
    	});
    	
    });
    
    //댓글 조회 이벤트(modify와 remove를 위해 조회)
    $(".chat").on("click", "li", function(e){
    	//li는 .chat의 자식(후손)
    	let rno = $(this).data("rno"); 
		//이벤트가 일어난 li는 this
		//data(data-의 값)은 data-값으로 되어있는 DOM선택
		
		replyService.get(rno, function(reply){
			
			modalInputReply.val(reply.reply);		       
	        modalInputReplyer.val(reply.replyer);		        
	        modalInputReplyDate.val(replyService.displayTime( reply.replyDate))
	        .attr("readonly","readonly");
	        modal.data("rno", reply.rno);
	        //data-rno속성을 reply.rno로 추가
	        
	        modal.find("button[id !='modalCloseBtn']").hide();
	        modalModBtn.show();
	        modalRemoveBtn.show();
	        
	        $(".replyModal").modal("show");
		});
    	
    });
    
    //댓글 수정 이벤트 처리
    modalModBtn.on("click", function(e){
        
        let reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
        
        replyService.update(reply, function(result){
              
          alert(result);
          modal.modal("hide");
          //showList(1);  //업데이트 이후에는 댓글리스트 보여주기(페이지 미고려)
          showList(pageNum);  //업데이트 이후에는 댓글리스트 보여주기(페이지 고려)
          
        });
        
    });
    
    //댓글 삭제 이벤트 처리
    modalRemoveBtn.on("click", function(e){
    	 let rno = modal.data("rno");
    	 
    	 replyService.remove(rno, function(result){
    		 alert(result);
   	      	 modal.modal("hide");   	      		    	     
   	    	//showList(1);  //삭제 이후에는 댓글리스트 보여주기(페이지 미고려)
             showList(pageNum);  //삭제 이후에는 댓글리스트 보여주기(페이지 고려)
    	 });
    });
    
    //페이지 번호 클릭시 이벤트 처리(해당 페이지의 댓글 리스트 표시)
	replyPageFooter.on("click","li a", function(e){
		
		e.preventDefault();
        console.log("page click");
        
        let targetPageNum = $(this).attr("href");
        
        console.log("targetPageNum: " + targetPageNum);
        
        pageNum = targetPageNum;
        
        showList(pageNum);
	
	});
	
});
</script>

<script>
//첨부뭃 처리 스크립트
$(document).ready(function(){
	//즉시실행함수(1회)
	(function(){
		let bno = '<c:out value="${board.bno}"/>';
		$.getJSON("getAttachList",{bno: bno},function(arr){
			console.log(arr); //arr은 컨틀로라에서 반환하는 json으로 된 List<BoardAttachVO>객체
			let str = "";
			$(arr).each(function(i, obj){
				if(!obj.fileType) { //일반 파일일시
					let fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
					str += "<div class='card col-md-3'>";
					str += "<div class='card-body'>";
					str += "<p class='mx-auto' style='width:90%;' title='"+ obj.fileName + "'>";
					str += "<a href='../upload/download?fileName=" + fileCallPath +"'>";
					str += "<img class='mx-auto d-block' src='../images/attach.png' >";
					str += "</a>";
					str += "</p>";
					str += "</div>";
					str += "</div>";
				}
				else {  //이미지 파일일시
					let fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid+"_"+obj.fileName); //섬네일
					
					let originPath = obj.uploadPath+ "\\"+obj.uuid +"_"+obj.fileName; //원본파일 경로
					originPath = originPath.replace(new RegExp(/\\/g),"/"); //\\를 /로 대체 
					
					str += "<div class='card col-md-3'>";
					str += "<div class='card-body'>";
					str += "<p class='mx-auto' style='width:90%;' title='"+ obj.fileName + "'>";
					str += "<a href=\"javascript:showImage(\'"+originPath+"\')\">"; //원본 파일 보기 위해 클릭 이벤트 처리
					str += "<img class='mx-auto d-block' src='../upload/display?fileName=" +fileCallPath+"'>"; //클릭 링크 이미지,직접 자원에 접근 못함	
					str += "</a>";
					str += "</p>";
					str += "</div>";
					str += "</div>";				
				}				
			});
			 $(".uploadResult #cardRow").html(str);
		});
	})();	
	
});

function showImage(fileCallPath) {
	
	$('.imageModal .modal-body').html("<img class='d-block w-75 mx-auto' src='../upload/display?fileName="+encodeURI(fileCallPath)+"&size=1' >")
	$(".imageModal").modal("show");
}
</script>
</body>
</html>
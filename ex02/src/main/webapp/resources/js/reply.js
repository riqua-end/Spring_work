/**
 *  reply.js by kim 2023/06/19
 	자바 스크립트 모듈은 여러개의 함수를 하나의 모듈처럼 묶음으로 구성(함수 변수안에 내부 함수를 여러개 만듦)
 	로컬 변수인 함수를 외부에서 접근하는 클로져 기법임
 	
 	board의 get.jsp에서 사용
 */

console.log("Reply Module..........");

//즉시 실행 함수 만든 후 내부 함수 만듦
let replyService = (function(){
	
	//중첩된 내부 함수
	//js는 호출시 꼭 파라메터 갯수를 적용 안해도 됨(필요 없는 파라메터는 안보내도 무관)
	//callback은 ajax성공시 실행할 함수, error는 실패시 실행할 내용
	function add(reply,callback){
		console.log("reply..........");
		
		//.ajax() 제이쿼리메서드는 서버와 비동기 방식으로 통신
		//파라메터로 {}로 JS객체형식으로 속성들을 작성
		$.ajax({
			type : 'post', //전송방식
			url : '../replies/new', //요청경로
			data : JSON.stringify(reply), //서버로 보내는 데이터값
			//reply는 파라메터로 받은 값으로 JS의 객체형인데 이를 JSON문자열로 변환
			contentType : "application/json; charset=UTF-8", //서버로 보내는 데이터 타입
			//dataType : "json" ,dataType은 서버로 부터 받는 데이터 형식(생략해도 무방)
			success : function(result,status,xhr) {
				//전송 성공시 실행할 함수,result는 서버로 부터 받는 값,status는 상태 xhr는 xmlhttprequest객체
				if (callback) {
					//callback을 인자로 받았으면 true
					//자바스크립트는 true/false가 아니고 null,0,undefined등은 false,아니면 true로 취급
					callback(result);
				}
			},
			error : function (xhr,status,er) { //실패시 실행 함수
				if(error) {
					error(er);
				}	
			}
		}); //.ajax
	} //add 함수
	
	function getList(param,callback,error) {
		
		let bno = param.bno;
		let page = param.page || 1; //1은 디폴트값으로 값이 없을시 1로 설정
		
		//get방식으로 list를 반환 받을시는 $.getJSON(url,성공시 실행 함수)사용
		$.getJSON("../replies/pages/" + bno + "/" + page,
			function(data) { 
			//페이지 미고려시는 data는 서버로 부터 받은 데이터로 ReplyVO배열이고
			//페이지 고려시는 replyCnt와 list를 같이 가진 JSON객체로 ReplyVO를 배열로 갖고 댓글수는 속성(pageDTO)
				if(callback) {
					callback(data); //댓글 목록만 가져오는 경우
				}
			}
		)
		.fail(function(xhr,status,err){
			if(error) {
				error(err);
			}
		});
	} //getList()
	
	function remove(rno,replyer,callback,error) {
		console.log("----------------------------");
		
		$.ajax({
			type : 'delete',
			url : '../replies/' + rno,
			success : function(deleteResult, status , xhr) {
				if(callback) {
					callback(deleteResult);
				}
			},
			error : function (xhr,status,er) {
				if(error) {
					error(er);
				}
			}		
		}); //.ajax()
	} //remove()
	
	function update(reply,callback,error) {
		console.log("RNO: " + reply.rno);
		
		$.ajax({
			type : 'put', //update는 PUT또는PATCH
			url : '../replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result,status,xhr) {
				if (callback) {
					callback(result);
				}	
			},
			error : function(xhr,status,er) {
				if(error) {
					error(er);
				}
			}
		}); //ajax()
	} //update()
	
	function get(rno,callback,error) {
		
		$.get("../replies/" + rno, function(result) { //get방식만 처리하는 제이쿼리 메서드
			
			if (callback) {
				callback(result);
			}
		}).fail(function(xhr,status,err) {
			if (error) {
				error(er);
			}
		});
	} //get()
	
	function displayTime(timeValue) {
		//서버에서 오는 Date객체 값은 posix타임임(밀리세컨드)
		var today = new Date(); //현재 시각을 나타내는 JS Date객체
		
		//자바스크립트의 Date객체를 posix타임으로 변환하는 메서드 getTime()
		var gap = today.getTime() - timeValue; //차이는 밀리세컨드
		
		var dateObj = new Date(timeValue); //posix타입을 이용하여 지정된 Date객체로 변환
		var str = "";
		
		if (gap < (1000 * 60 * 60 * 24)) {
			//gap이 하루 이하이면 시간까지 표시
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			//두자리로 시간 표시
			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
					':', (ss > 9 ? '' : '0') + ss ].join('');
					//배열 요소를 문자열로 변환(문자열에 공백 없이 처리)
					//join()은 문자열에 , 로 구분 처리 09:33:27
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth() is zero-basesd(0~11)
			var dd = dateObj.getDate();
			
			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
					(dd > 9 ? '' : '0') + dd ].join('');
					//2023/06/20
		}			
	} //displayTime(timeValue)
	
	return {
		add:add, //속성이 add이고 값이 add메서드인 객체를 반환하여 replyService에 대입
		getList:getList,
		remove : remove,
		update : update,
		get : get,
		displayTime : displayTime
	};
	
})();

console.log(replyService);
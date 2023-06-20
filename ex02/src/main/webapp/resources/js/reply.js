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
	
	return {add:add}; //속성이 add이고 값이 add메서드인 객체를 반환하여 replyService에 대입
	
})();

console.log(replyService);
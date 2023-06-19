/**
 *  reply.js by kim 2023/06/19
 	자바 스크립트 모듈은 여러개의 함수를 하나의 모듈처럼 묶음으로 구성(함수 변수안에 내부 함수를 여러개 만듦)
 	로컬 변수인 함수를 외부에서 접근하는 클로져 기법임
 	
 	board의 get.jsp에서 사용
 */

console.log("Reply Module..........");

//즉시 실행 함수 만든 후 내부 함수 만듦
let replyService = (function(){
	
	function add(reply,callback){
		console.log("reply..........");
	}
	
	return {add:add}; //속성이 add이고 값이 add메서드인 객체를 반환하여 replyService에 대입
	
})();

console.log(replyService);
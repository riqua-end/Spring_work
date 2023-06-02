<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



<h2>ex04.jsp</h2>
<%--EL로 서버에서 전달된 값 사용 --%>
<!-- 자바 bean객체는 자동 반환되고 반환된 객체는 클래스명의 첫 자를 소문자로 변환 -->

<h3>SAMPLEDTO ${sampleDTO}</h3>
<h3>PAGE ${page}</h3>
</body>
</html>
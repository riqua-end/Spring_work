<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 파일 업로드는 post이어야 하고 enctype="multipart/form-data"를 필수로 작성 -->
<form action="exUploadPost" method="post" enctype="multipart/form-data">
	<div>
		<input type="file" name="files" multiple>
	</div>
	<!--  
	<div>
		<input type="file" name="files">
	</div>
	<div>
		<input type="file" name="files">
	</div>
	<div>
		<input type="file" name="files">
	</div>
	<div>
		<input type="file" name="files">
	</div>
	-->
	<div>
		<input type="submit" value="파일 업로드">
	</div>
</form>

</body>
</html>
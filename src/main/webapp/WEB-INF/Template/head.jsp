<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/Template/tags.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
</style>
</head>
<body>
	<div class="head-container">
		<c:forEach items="${headMenu}" var="head">
			<a href="${head.menuUrl}">${head.menuName}</a>
		</c:forEach>
	</div>
	<a href="${path}/login/exit" style="float: right;">exit</a>
</body>
</html>
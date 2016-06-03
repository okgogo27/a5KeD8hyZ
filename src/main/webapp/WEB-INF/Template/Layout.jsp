<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/Template/tags.jsp"%>
<html>
<head>
<jsp:include page="/WEB-INF/Template/resources.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tiles:insertAttribute name="head" ignore="true" />
<style type="text/css">
.menu-head {
	margin-top: 10px;
	margin-left: 10%;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div class="menu-head">
		<jsp:include page="/WEB-INF/Template/head.jsp" />
	</div>
	<div class="container-fluid">
		<div class="col-md-1">
			<jsp:include page="/WEB-INF/Template/left-menu.jsp" />
		</div>
		<div class="col-md-11">
			<tiles:insertAttribute name="body" ignore="true" />
		</div>
	</div>
</body>
</html>
<%@tag import="platform.demo.entity.CodeEntity"%>
<%@tag import="platform.utils.PredicateUtils"%>
<%@tag import="platform.utils.CodeManager"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="type" type="java.lang.String" required="true"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%@ attribute name="all" type="java.lang.Boolean" required="false"%>
<%@ attribute name="className" type="java.lang.String" required="false"%>
<%
    if (!type.isEmpty()) {
        request.setAttribute("codes",
                PredicateUtils.getCodeManager().getCodeMethods(CodeEntity.class).findCodeByType(type));
    }
%>
<select <c:if test="${not empty id}">id="${id}"</c:if> name="${name}"
	class="${className}">
	<c:if test="${all}">
		<option value="" selected></option>
	</c:if>
	<c:forEach items="${codes}" var="codeName" varStatus="status">
		<option value="${codeName.code}"
			<c:if test="${value==codeName.code}">selected</c:if>>
			${codeName.name}</option>
	</c:forEach>
</select>

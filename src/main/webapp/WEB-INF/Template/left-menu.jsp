<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/Template/tags.jsp"%>

<script type="text/javascript">
	$(function() {
		$(".active").closest("ul").addClass("in");
	});
</script>
<ul class="nav nav-list">
	<c:forEach items="${leftMenu}" var="left">
		<c:if test="${not empty left.children}">
			<li class="nav-header" data-toggle="collapse"
				data-target="#${left.id}">${left.menuName}
				<ul id="${left.id}" class="collapse nav nav-list">
					<c:forEach items="${left.children}" var="child">
						<li
							<c:if test="${child.menuUrl==currentMenu.menuUrl}">class="active"</c:if>><a
							href="${child.menuUrl}">${child.menuName}</a></li>
					</c:forEach>
				</ul>
			</li>
		</c:if>
	</c:forEach>
</ul>

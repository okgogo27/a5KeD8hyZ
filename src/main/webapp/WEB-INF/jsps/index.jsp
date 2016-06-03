<%@ include file="/WEB-INF/Template/tags.jsp"%>
<tiles:insertDefinition name="Layout">
	<tiles:putAttribute name="head">
		<script type="text/javascript">
			$(function() {

				$("form").validate({
					rules : {
						birthday : {
							required : true
						}
					},
					messages : {

					}
				});

			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		${currentDate}
		${path}
		${resourcePath}
		<form action="${path}/main/save">
			<div>
				name=<input name="name" />
			</div>
			<div>
				birthday= <input name="birthday" />
			</div>
			<div>
				sex=<input name="sex" />
			</div>
			<button type="submit">save</button>
		</form>
		<div>
			<table>
				<tr>
					<th>name</th>
					<th>sex</th>
					<th>birthday</th>
				</tr>
				<c:forEach items="${demoEntity}" var="demo">
					<tr>
						<td>${demo.name}</td>
						<td>${demo.sex}</td>
						<td>${demo.birthday}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<d:code-select name="abc" type="RELATIONSHIP" value=""></d:code-select>
		<a href="${path}/main/tile">tileDemo</a>
	</tiles:putAttribute>
</tiles:insertDefinition>




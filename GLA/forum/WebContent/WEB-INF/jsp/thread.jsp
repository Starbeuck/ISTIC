<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>${param.title}</title>

<link rel="stylesheet" href="fichiers/style.css" type="text/css" />
</head>
<body class="ltr">

	<div id="wrapcentre">

		<br style="clear: both;" />

		
		<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
			cellpadding="0" width="100%">
			<tbody>
				<tr>

					<td class="row1">
						<p class="breadcrumbs">
							Connect&eacute; en tant que
							<%=session.getAttribute("user")%>
							!
						</p>
					</td>
				</tr>
				<tr>
					<td class="row1">
						<p class="breadcrumbs">
							<a href="home">Board index</a>
						</p>
					</td>
				</tr>

			</tbody>
		</table>
		<br />

		<form action="login" method="post">

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<th colspan="2">${param.title}</th>
					</tr>

					<tr>
						<td class="row2">

							<table style="width: 100%;" cellspacing="1" cellpadding="4"
								align="center">
								<tbody>

									<c:forEach items="${messages}" var="message">
										<tr>
											<td class="row1" align="center" width="5"><p>${message.author}</p></td>
											<td class="row2" align="center" width="130"><p>${message.text}</p></td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>

		</form>

		<c:if test="${sessionScope.user != null}">
			<table cellspacing="1" width="100%">
				<tbody>
					<tr>
						<c:set var="message">
							<c:url value="message.jsp">
								<c:param name="title" value="${param.title}" />
								<c:param name="author" value="${param.author}" />
							</c:url>
						</c:set>
						<td valign="middle" align="right"><a href="${message}"
							class="breadcrumbs"><img
								src="fichiers/button_topic_reply.gif" alt="Post new message"
								title="Post new message" /></a></td>
					</tr>
				</tbody>
			</table>
		</c:if>


		<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
			cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td class="row1">
						<p class="breadcrumbs">Index du forum</p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

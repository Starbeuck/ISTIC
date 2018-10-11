<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>FORUM</title>



<link rel="stylesheet" href="fichiers/style.css" type="text/css" />
</head>
<body class="ltr">


	<div id="wrapcentre">

		<div id="pagecontent">

			<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
				cellpadding="0" width="100%">
				<tbody>
					<tr>
						<c:if test="${sessionScope.user != null}">

							<td class="row1">
								<p class="breadcrumbs">
									Connect&eacute; en tant que
									<%=session.getAttribute("user")%>
									!
								</p>
							</td>
							<td class="row1"><a href="logout" class="breadcrumbs">D&eacute;connexion</a>

							</td>

						</c:if>

						<c:if test="${sessionScope.user == null}">

							<td class="row1">
								<p class="breadcrumbs">Non connect&eacute;</p>
							</td>
							<td class="row1">
								<p class="breadcrumbs">
									<a href="login" class="breadcrumbs">Se connecter</a>
								</p>
							</td>
							<td class="row1">
								<p class="breadcrumbs">
									Vous n'avez pas de compte ? <a href="signup"
										class="breadcrumbs">Sign Up ! </a>
								</p>
							</td>

						</c:if>
					</tr>
				</tbody>
			</table>

			<br clear="all" />

			<c:if test="${sessionScope.user != null}">
				<table cellspacing="1" width="100%">
					<tbody>
						<tr>
							<td valign="middle" align="left"><a href="topic"
								class="breadcrumbs"><img src="fichiers/button_topic_new.gif"
									alt="Post new topic" title="Post new topic" /></a></td>
						</tr>
					</tbody>
				</table>
			</c:if>

			<br clear="all" />

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<td class="cat" colspan="4">
							<table cellspacing="0" width="100%">
								<tbody>
									<tr class="nav">
										<td valign="middle">&nbsp;</td>
										<td valign="middle" align="right">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>

						<th>&nbsp;Topics&nbsp;</th>
						<th>&nbsp;Auteur&nbsp;</th>
						<th>&nbsp;R&eacute;ponses&nbsp;</th>
						<th>&nbsp;Vues&nbsp;</th>
					</tr>


					<c:forEach items="${threads}" var="thread">
						<tr>
							<c:set var="URL">
								<c:url value="thread.jsp">
									<c:param name="title" value="${thread.title}" />
									<c:param name="author" value="${thread.author}" />
								</c:url>
							</c:set>
							<td class="row1"><a class="topictitle" href="${URL}">${thread.title}</a></td>
							<td class="row2" align="center" width="130"><p
									class="topicauthor">
									${thread.author}
								</p></td>
							<td class="row1" align="center" width="50"><p
									class="topicdetails">10</p></td>
							<td class="row2" align="center" width="50"><p
									class="topicdetails">1234</p></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			<br clear="all" />
		</div>

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

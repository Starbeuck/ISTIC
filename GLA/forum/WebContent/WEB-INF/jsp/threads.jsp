<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>FORUM DE GLA</title>

<!-- Bootstrap -->
<link href="fichiers/css/bootstrap.css" rel="stylesheet" />
<link href="fichiers/css/bootstrap-theme.css" rel="stylesheet" />

<!-- css style -->
<link href="fichiers/css/style.css" rel="stylesheet" />
</head>
<body>

	<!-- Fixed navbar -->
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="home">Forum de GLA</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${sessionScope.user != null}">
						<li><a href="upload"> Connect&eacute; en tant que <%=session.getAttribute("user")%>
								!
						</a></li>
						<li><a href="logout">D&eacute;connexion </a></li>
					</c:if>
					<c:if test="${sessionScope.user == null}">
						<li><a href="login">Non connect&eacute; ! Sign In !</a></li>
						<li><a href="signup"> Vous n'avez pas de compte ? Sign Up
								! </a></li>
					</c:if>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<div class="container" style="padding-top: 7%;">
		<c:if test="${sessionScope.user != null}">
			<a class="btn btn-primary" href="topic" role="button">New topic !</a>
		</c:if>

		<table class="table table-striped">
			<thead>
				<tr>
					<th align="center">&nbsp;Topics&nbsp;</th>
					<th align="center">&nbsp;Auteur&nbsp;</th>
					<th align="center">&nbsp;R&eacute;ponses&nbsp;</th>
					<th align="center">&nbsp;Vues&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${threads}" var="thread">
					<tr>
						<c:set var="URL">
							<c:url value="thread.jsp">
								<c:param name="title" value="${thread.title}" />
								<c:param name="author" value="${thread.author}" />
							</c:url>
						</c:set>
						<c:set var="profil">
							<c:url value="profil.jsp">
								<c:param name="author" value="${thread.author}" />
							</c:url>
						</c:set>
						<td><a href="${URL}">${thread.title}</a></td>
						<td align="center" width="130"><c:if
								test="${sessionScope.user != null}">
								<a href="${profil}">${thread.author}</a>
							</c:if> <c:if test="${sessionScope.user == null}">
								<p>${thread.author}</p>
							</c:if></td>
						<td class="row1" align="center" width="50"><p
								class="topicdetails">${thread.nbMessage}</p></td>
						<td class="row2" align="center" width="50"><p
								class="topicdetails">1234</p></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

		<!--  footer -->
		<div id="footer">
			<div class="container ">
				<div class="row">
					<div class="col-sm-6">
						<p class="copyright">Made by Solenn KEROULLAS</p>
					</div>
					<div class="col-sm-6">
						<div class="credits">
							Designed by <a
								href="https://github.com/Starbeuck/ISTIC/tree/master/GLA">Github
								Repository</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
</body>

</html>

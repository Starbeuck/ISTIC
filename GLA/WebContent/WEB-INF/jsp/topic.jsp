<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>TOPIC</title>

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
					<c:if test="${fn:escapeXml(sessionScope.user != null)}">
						<li><a href="upload"> <c:out
									value="Connect� en tant que
								${fn:escapeXml(user)} !"></c:out>
						</a></li>
						<li><a href="logout">D&eacute;connexion </a></li>
					</c:if>
					<c:if test="${fn:escapeXml(sessionScope.user == null)}">
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
		<form action="topic" method="post">


			<div class="py-5  text-center">
				<h2>Write a new topic</h2>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label>Title of topic *</label> <input name="title" size="25"
							class="form-control" placeholder="Title for the topic*"
							type="text" tabindex="1" required
							value="<c:out value="${param.title}"/>"></input> <label>Content
							of topic *</label>
						<textarea name="content" size="5000" class="form-control"
							placeholder="Message for the topic*" rows="4" tabindex="2"
							required value="<c:out value="${param.content}"/>"></textarea>
					</div>
				</div>
				<div class="col-md-12 text-center">
					<span class="erreur">${erreurs['content']}</span><input
						type="submit" class="btn btn-success btn-send" value="Post Topic" />
					<p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
					<span class="erreur">${erreurs['resultat']}</span>
				</div>
			</div>
		</form>
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

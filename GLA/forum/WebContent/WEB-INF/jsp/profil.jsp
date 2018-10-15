<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>Profil de ${param.author}</title>


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
						<li><a href="logout"> Connect&eacute; en tant que <%=session.getAttribute("user")%>
								! D&eacute;connexion
						</a></li>
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
		<div class="py-5  text-center">
			<h2>Profil de ${param.author}</h2>
		</div>

		<div class="row justify-content-between">
			<div class="col-sm-5 py-5  text-center">
				<img class="mb-4" src="<c:url value="${photo}"/>" alt="Icon" />
				<p>Gender : ${getUser.gender }</p>
			</div>
			<div class="col-sm-7">
				<p>Age : ${getUser.age }</p>
				<p>Habite à : ${getUser.city }</p>
				<p>Nombres de messages postés: ${nbMess}</p>
				<p>
					Rank : ${rank.ranked} <img class="mb-4"
						src="<c:url value="${rank.URLBadge}" />" alt="Icon ${rank.ranked}" />
				</p>
			</div>
		</div>



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

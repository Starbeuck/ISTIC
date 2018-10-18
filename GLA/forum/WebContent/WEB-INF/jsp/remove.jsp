<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>${param.title}</title>

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
									value="Connecté en tant que
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
		<form action="remove.jsp" method="post">
			<h1>Êtes-vous sûr.e de vouloir supprimer ce message ?</h1>
			<table class="table table-striped table-bordered table-hover">
				<thead class="text-center">
					<tr>
						<th style="text-align: center;" width="150">Author</th>
						<th style="text-align: center;">Message</th>
					</tr>
				</thead>


				<tbody>
					<tr class="text-center">
						<td><img class="mb-4" height="64" width="64" name="photo"
							src="<c:url value="${fn:escapeXml(photo)}"/>" alt="Icon" />
							<p>
								<c:out value="${fn:escapeXml(author)}" />
							</p></td>
						<td><p>
								<c:out value="${fn:escapeXml(content)}" />
							</p></td>
					</tr>
				</tbody>
			</table>
			<div style="text-align: center">
				<div class="form-label-group">
					<label for="inputPassword">Password</label> <input type="password"
						class="form-control" placeholder="Password" name="password"
						required /><span class="erreur">${erreurs['password']}</span>
				</div>
				<input type="submit" class="btn btn-danger btn-send" role="button"
					value="Oui, supprimer ce message !" />
				<p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
				<span class="erreur">${erreurs['resultat']}</span>
			</div>
		</form>
		<!--  footer -->
		<div id="footer">
			<div class="inner">
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

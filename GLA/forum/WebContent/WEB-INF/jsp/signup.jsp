<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>SIGN UP</title>

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
		<div class="container">
			<div class="py-5  text-center">
				<h2>Create an account</h2>
			</div>
			<form action="signup" method="post">
				<div class="row">
					<div class="col-md-6 mb-3">
						<label>Username</label><input tabindex="1" type="text"
							class="form-control" placeholder="Username" required name="login"
							value="<c:out value="${param.login}"/>" /><span class="erreur">${erreurs['login']}</span>
						<div class="invalid-feedback" style="width: 100%;">Your
							username is required.</div>
					</div>
					<div class="col-md-6 mb-3">
						<label>Password</label> <input class="form-control"
							name="password" size="25" tabindex="2" type="password"
							placeholder="Password" required /> <span class="erreur">${erreurs['password']}</span>
						<div class="invalid-feedback">Valid password is required.</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4 mb-3">
						<label>How old are you ?</label><input type="number" min="18"
							class="form-control" placeholder="Age" required name="age"
							tabindex="3" value="<c:out value="${param.age}"/>" /><span
							class="erreur">${erreurs['age']}</span>
					</div>
					<div class="col-md-4 mb-3">
						<label>Which gender are you ?</label> <select name="gender"
							class="form-control" tabindex="4">
							<option>Male</option>
							<option>Female</option>
						</select> <span class="erreur">${erreurs['gender']}</span>
					</div>
					<div class="col-md-4 mb-3">
						<label>Where do you live ?</label> <input class="form-control"
							size="25" tabindex="5" type="text" name="city"
							required value="<c:out value="${param.city}"/>" /> <span
							class="erreur">${erreurs['city']}</span>
					</div>
				</div>

				<div class="row text-center">
					<input class="btn btn-theme" name="signup" class="btnmain"
						value="Create my account !" type="submit" style="margin-top: 5%" tabindex="6"/>
					<span class="erreur">${erreurs['already']}</span>
					<p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
				</div>
			</form>

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

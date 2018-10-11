<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>SIGN UP</title>

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
							<a href="home">Board index</a>
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<br />

		<form action="signup" method="post">

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<th colspan="2">Sign Up</th>
					</tr>

					<tr>
						<td class="row2">

							<table style="width: 100%;" cellspacing="1" cellpadding="4"
								align="center">
								<tbody>

									<tr>
										<td valign="top"><b class="gensmall">Choose a login :</b></td>
										<td><input class="post" name="login" size="25"
											tabindex="1" type="text"
											value="<c:out value="${param.login}"/>" /> <span
											class="erreur">${erreurs['login']}</span></td>

									</tr>
									<tr>
										<td valign="top"><b class="gensmall">Choose a
												password :</b></td>
										<td><input class="post" name="password" size="25"
											tabindex="2" type="password"/> <span
											class="erreur">${erreurs['password']}</span></td>
									</tr>


								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<span class="erreur">${erreurs['already']}</span>
						<p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
						<td class="cat" colspan="2" align="center"><input
							name="signup" class="btnmain" value="Create my account !"
							tabindex="5" type="submit" /></td>
					</tr>
				</tbody>
			</table>

		</form>


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

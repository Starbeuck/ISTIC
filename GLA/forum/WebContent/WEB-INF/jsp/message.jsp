<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>POST NEW MESSAGE</title>

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

		<form action="message.jsp" method="post">

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<th colspan="2">Write a new message into "${param.title}${title}"</th>
					</tr>

					<tr>
						<td class="row2">

							<table style="width: 100%;" cellspacing="1" cellpadding="4"
								align="center">
								<tbody>
									<tr>
										<td valign="top"><b class="gensmall">Content</b></td>
										<td><textarea style="" class="post" name="content"
												size="5000" tabindex="2" type="text"
												value="<c:out value="${content}"/>"></textarea><span
											class="erreur">${erreurs['content']}</span></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<td class="cat" colspan="2" align="center"><input
							name="newmessage" class="btnmain" value="Post Message"
							tabindex="5" type="submit" />
							<p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p></td>
						<span class="erreur">${erreurs['resultat']}</span>
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

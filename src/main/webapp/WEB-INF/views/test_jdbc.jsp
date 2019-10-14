<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Tests JDBC</title>
<c:url value="/css/index.css" var="indexCssUrl" />
<link rel="stylesheet" href="${ indexCssUrl }">
</head>
<body>
	<h1>Tests JDBC</h1>
	<ul>
		<c:forEach items="${ messages }" var="message" varStatus="boucle">

			<li>${ boucle.count }.${ message }</li>

		</c:forEach>
	</ul>
</body>
</html>
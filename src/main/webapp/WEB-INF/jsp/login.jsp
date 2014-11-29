<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>MyGalaxy app 4 Akvelon Hackathon</title>
<link rel="stylesheet" type="text/css" href="css/galaxy.css"></link>
<script lang="javascript" type="text/javascript" src="js/jquery-2.1.1.js"></script>
<script lang="javascript">
</script>
</head>
<body class="loginbody">
	<div class="main">
	<br>
	Please sign in
	<p class="vk">
		<c:choose>
		<c:when test="${empty sessionScope.instuser}">
		<a href="https://oauth.vk.com/authorize?client_id=${vk_client_id}&scope=${vk_scope}&redirect_uri=${vk_redirect_uri}&display=${vk_display}&v=5.27&response_type=${vk_response_type}"><img src="img/vk.png" alt="VK Sign In" style="width: 100px; height: 100px;" title="VK Sign In"/></a>
		</c:when>
		<c:otherwise>
		<img src="img/vk_disabled.png" alt="VK Sign In" style="width: 100px; height: 100px;" title="VK Sign In"/>
		</c:otherwise>
		</c:choose>
	</p>
	<p class="inst">
		<c:choose>
		<c:when test="${empty sessionScope.instuser}">
		<a href="https://api.instagram.com/oauth/authorize/?client_id=${insta_client_id}&redirect_uri=${insta_redirect_uri}&response_type=${insta_response_type}"><img src="img/inst.jpg" alt="Instagram Sign In" style="width: 100px; height: 100px;" title="Instagram Sign In"/></a>
		</c:when>
		<c:otherwise>
		<img src="img/inst_disabled.png" alt="Instagram Sign In" style="width: 100px; height: 100px;" title="Instagram Sign In"/>
		</c:otherwise>
		</c:choose>
	</p>
	<br>
	<p>
		<a href="asyncgraph" class="view"><img src="img/view.png" alt="View graph" title="View graph" style="width: 120px; height: 70px;"/></a>
	</p>
	</div>
</body>
</html>

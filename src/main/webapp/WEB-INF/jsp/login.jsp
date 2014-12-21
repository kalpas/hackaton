<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MyGalaxy app 4 Akvelon Hackathon</title>
<link rel="stylesheet" type="text/css" href="css/galaxy.css"></link>
<link href="css/bootstrap.css" rel="stylesheet">
</head>
<body class="loginbody">
	<script lang="javascript" type="text/javascript"
		src="js/jquery-2.1.1.js"></script>
	<script src="js/bootstrap.js"></script>
	<div class="spacer"></div>
	<div class="container base-container" role="main">
		<div class="jumbotron">
			<h1>Hi there</h1>
			<p>This is the app, that helps you visualize your social network.</p>
			<p>Simply login with one or both social networks and chose the
				type of a graph you want.</p>
		</div>
		<h1>Please sign in</h1>
		<div class="social-box">
			<div class="vk">
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<img src="img/vk.jpg" alt="VK Sign In"
							style="width: 100px; height: 100px;" title="VK Sign In" />
						<a class="btn btn-lg btn-success" role="button"
							href="https://oauth.vk.com/authorize?client_id=${vk_client_id}&scope=${vk_scope}&redirect_uri=${vk_redirect_uri}&display=${vk_display}&v=5.27&response_type=${vk_response_type}">Sign
							In</a>
					</c:when>
					<c:otherwise>
						<img src="img/vk_disabled.png" alt="VK Sign In"
							style="width: 100px; height: 100px;" title="VK Sign In" />
						<div class="alert alert-success" role="alert">
							<strong>Authorised!</strong>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="inst">
				<c:choose>
					<c:when test="${empty sessionScope.instuser}">
						<img src="img/inst.jpg" alt="Instagram Sign In"
							style="width: 100px; height: 100px;" title="Instagram Sign In" />
						<a class="btn btn-lg btn-success" role="button"
							href="https://api.instagram.com/oauth/authorize/?client_id=${insta_client_id}&redirect_uri=${insta_redirect_uri}&response_type=${insta_response_type}">Sign
							In</a>
					</c:when>
					<c:otherwise>
						<img src="img/inst_disabled.png" alt="Instagram Sign In"
							style="width: 100px; height: 100px;" title="Instagram Sign In" />
						<div class="alert alert-success" role="alert">
							<strong>Authorised!</strong>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="view">
			<c:choose>
				<c:when
					test="${not empty sessionScope.instuser or not empty sessionScope.user}">
					<div class="page-header">
						<H2>Now you are able to view following graphs:</H2>
					</div>
				</c:when>
			</c:choose>

			<div class="list-group">
				<c:choose>
					<c:when test="${not empty sessionScope.user}">
						<a href="graph?type=vk" class="list-group-item">
							<h3 class="list-group-item-heading">VK graph</h3>
							<p class="list-group-item-text">This will show VK graph.</p>
							<p></p>
							<button type="button" class="btn btn-lg btn-success">Build
								it! >></button>
						</a>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${not empty sessionScope.instuser}">
						<a href="graph?type=insta" class="list-group-item">
							<h3 class="list-group-item-heading">Instagram graph</h3>
							<p class="list-group-item-text">This will show Instagram
								graph.</p>
							<p></p>
							<button type="button" class="btn btn-lg btn-success">Build
								it! >></button>
						</a>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when
						test="${not empty sessionScope.instuser and not empty sessionScope.user}">
						<a href="graph?type=joined" class="list-group-item">
							<h3 class="list-group-item-heading">Combined graph</h3>
							<p class="list-group-item-text">This will show graph
								combining your VK network with instagram.</p>
							<p class="list-group-item-text label label-danger">But it
								won't work properly in case you haven't authorised with VK</p>
							<p></p>
							<button type="button" class="btn btn-lg btn-success">Build
								it! >></button>
						</a>
					</c:when>
				</c:choose>
			</div>

		</div>
		<div class="spacer"></div>
		<footer class="footer">
			<p class="text-muted">
				Source is placed on GitHub under MIT License. Copyright (c) 2014
				kalpas-team <a href="https://github.com/kalpas-team/hackaton"
					type="button" class="btn btn-xs btn-link">Link</a>
				</button>
			</p>

		</footer>
	</div>

</body>
</html>

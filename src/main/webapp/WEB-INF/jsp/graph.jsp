<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/galaxy.css"></link>
<link href="css/bootstrap.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Graph will be here</title>
	<style>
		body { background-color: black; height: 100%; width: 100%; position: absolute; overflow: hidden; margin: 0; padding: 0;}
	</style>
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bundle.js"></script>
</head>
<body onload='ngraph.main()'>
	<button id='transparentLinks' >Hide/Show Links</button>
	<div class="busy alert alert-danger" id="busy">
		<p>Loading Data. Please, be patient!</p>
	</div>
	<input type="hidden" id="graphId" value="${graphId}" />
	<input type="hidden" id="graphType" value="${graphType}">
</body>

</body>
</html>
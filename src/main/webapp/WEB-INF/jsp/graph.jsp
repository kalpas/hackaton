<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Grpah will be here</title>
	<style>
		body { background-color: black; height: 100%; width: 100%; position: absolute; overflow: hidden; margin: 0; padding: 0;}
	</style>
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bundle.js"></script>
</head>
<body onload='ngraph.main()'>
	<input type="hidden" id="graphId" value="${graphId}" />
	<button id="butt">Some addeble node</button>
	<button id="removeButton">Some removable node</button>
</body>

</body>
</html>
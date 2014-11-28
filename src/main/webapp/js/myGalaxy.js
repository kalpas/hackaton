module.exports.main = function () {

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/threex.domevents.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/three.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);
	

	setTimeout(function(){
	
	var graph = require('ngraph.graph')();
	var threeGraphics = require('ngraph.three')(graph,{ interactive: true, container: document.getElementById('container') });
	var THREE = threeGraphics.THREE;
	var camera = threeGraphics.camera;
	
	var domEvents = new THREEx.DomEvents(camera, threeGraphics.renderer.domElement, THREE);
	   
	}, 500);
};
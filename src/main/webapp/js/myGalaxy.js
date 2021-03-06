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
		var graphId = $('#graphId').val();
		var graphType = $('#graphType').val();
		console.info(graphId);
		if (graphId && graphType) {
			getData(graphId,graphType);
			//timerId = setInterval(function(){getData(graphId)},10000);
		} else {
			console.info('No graphId');
		}
	}, 500);

};

module.exports.buildGraph = function (data) {
	
	var graph = graph ? graph : require('ngraph.graph')();
	var layout = layout ? layout : require('ngraph.forcelayout3d')(graph, {gravity: 1.2, theta: 0.2, dragCoeff: 0.9, timeStep : 1 } ); 
	var threeGraphics = threeGraphics ? threeGraphics : require('ngraph.three')(graph,{ interactive: true });
	var THREE = THREE ? THREE : threeGraphics.THREE;
	var camera = camera ? camera : threeGraphics.camera;
	
	var domEvents = domEvents ? domEvents : new THREEx.DomEvents(camera, threeGraphics.renderer.domElement, THREE);
	
	 // tell graphics we want custom UI
	threeGraphics.createNodeUI(function (node) {     
		var nodeGeometry = node.data.additionalProperties.type == 'vk' ? new THREE.SphereGeometry(node.data.size) : new THREE.BoxGeometry(Math.round(node.data.size),Math.round(node.data.size),Math.round(node.data.size));
		var nodeMaterial = new THREE.MeshBasicMaterial({ color: node.data.color });
		
		var result = new THREE.Mesh(nodeGeometry, nodeMaterial);
		result['nodeId'] =  node.id;
		node.data['selected'] = false;
		domEvents.addEventListener(result, 'mouseover',onMouseOver);
		domEvents.addEventListener(result, 'mouseout',onMouseOut);
        domEvents.addEventListener(result, 'click',onMouseClick);
		return result;
  }).createLinkUI(function(link) {
		var linkGeometry = new THREE.Geometry();
		// we don't care about position here. linkRenderer will update it
		linkGeometry.vertices.push(new THREE.Vector3(0, 0, 0));
		linkGeometry.vertices.push(new THREE.Vector3(0, 0, 0));

		var linkMaterial = new THREE.LineBasicMaterial({ color: link.data.color, transparent: true });
		return new THREE.Line(linkGeometry, linkMaterial);
  });
	
	layout.step();
	
	addEventListeners(graph,threeGraphics, 'transparentLinks' )
	createGraph(graph,data);
	
	
	threeGraphics.run();
	
	function onMouseOver(e) {
		var nodeId = e.target.nodeId;
		/*console.info('nodeId - ' + nodeId + ' nodeIdUI - ' + e.target.id);*/
		var node = graph.getNode(nodeId);
		/*console.info('node name ' + node.data.name);*/
		
		var links = node.links;
		
		for (var i=0; i < links.length; i++) {
			var link = threeGraphics.getLinkUI(links[i].id);
			if (link) link.material.color.setStyle('#b14b56');
		}
		if(node.data['selected']  != true)
		{
			showName(node.data.name, e);
		}
	}
    
	function onMouseClick(e) {
		var nodeId = e.target.nodeId;
		var node = graph.getNode(nodeId);
		if(node.data['selected'] != true)
		{
	        graph.forEachNode(function(nodes){
	        	 if(nodes.data['selected'] == true)
	        	 {
	                 nodes.data['selected'] = false;
	                 var links = nodes.links;
	         		for (var i=0; i < links.length; i++) {
	        			var link = threeGraphics.getLinkUI(links[i].id);
	        			if (link) link.material.color.setStyle(links[i].data.color ? links[i].data.color : 'blue');
	        		}
	         		hideDetailsInfo();
	        	 }
	        	 
	        });
			node.data['selected'] = true;
	        var links = node.links;
	 		for (var i=0; i < links.length; i++) {
				var link = threeGraphics.getLinkUI(links[i].id);
				if (link) link.material.color.setHex(0xff0000);
			}
	 		hideName();
	 		showDetailsInfo(node,e);
	 		
	        console.info('nodeId selected ' + node.id);
		}
		else
		{
            node.data['selected'] = false;
            var links = node.links;
    		for (var i=0; i < links.length; i++) 
    		{
    			var link = threeGraphics.getLinkUI(links[i].id);
    			if (link) link.material.color.setStyle(links[i].data.color ? links[i].data.color : 'blue');
    		}
    		hideDetailsInfo();
		}
	}
	
	function onMouseOut(e) {
		var nodeId = e.target.nodeId;
		var node = graph.getNode(nodeId);
		var links = node.links;
		
		if(node.data['selected'] != true) 
		{

			for (var i=0; i < links.length; i++) {
				var link = threeGraphics.getLinkUI(links[i].id);
				if (link) link.material.color.setStyle(links[i].data.color ? links[i].data.color : 'blue');
			}
		
		}
		hideName();
	}
	
  
};

function showName(name, event) {
	
	var newDiv = document.createElement('div');
	newDiv.className = 'div-class';
	newDiv.name = 'nameDiv';
	newDiv.innerHTML = name;
	
	newDiv.style.position = "absolute";
	newDiv.style.left = (event.origDomEvent.clientX + 10)+'px';
	newDiv.style.top = (event.origDomEvent.clientY - 10)+'px';
	newDiv.style.color = 'white';
	newDiv.style.fontWeight="bold";
	newDiv.style.fontSize = '26px';
	
	document.body.appendChild(newDiv);
}

function showDetailsInfo(node, event) {
	var newDiv = document.createElement('div');
	newDiv.className = 'div-info';
	newDiv.name = 'nameDiv';
	newDiv.innerHTML = name;
	
	newDiv.style.position = "absolute";
	newDiv.style.right = '0px';
	newDiv.style.top = '0px';
	newDiv.style.fontSize = '24px';
	newDiv.style.color = 'white';
	newDiv.style.backgroundColor = '#101010';
	newDiv.align = 'center';
	
	newDiv.style.padding = '5px';
	newDiv.style.borderRadius = '10px';

	if(node.data.additionalProperties.link)
	{
		var divText = document.createElement("a");
		divText.height = '100px';
		divText.textContent = node.data.name;
		divText.href = node.data.additionalProperties.link;
		newDiv.appendChild(divText);
		var pp = document.createElement("p");
		newDiv.appendChild(pp);
	}
	else
	{
		var divText = document.createElement("p");
		divText.height = '100px';
		divText.textContent = node.data.name;
		divText.align = 'center';
		newDiv.appendChild(divText);
	}
	
	if(node.data.photos && node.data.photos[0])
	{
		var img = document.createElement("img");
		img.src = node.data.photos[0];
		img.style.width="200";
		img.style.height = '400';
		newDiv.appendChild(img);
	}
	
	document.body.appendChild(newDiv);
}

function hideDetailsInfo() {
	var elems = document.getElementsByClassName('div-info');
	
	if(elems)
	{
		for (var i = 0; i < elems.length; i++) {
			document.body.removeChild(elems[i]);
		}
	}
}

function hideName() {
	var elems = document.getElementsByClassName('div-class');
	
	if(elems)
	{
		for (var i = 0; i < elems.length; i++) {
			document.body.removeChild(elems[i]);
		}
	}
}

function createGraph(graph, data) {
	
	if (!data.nodes || !data.nodes.length || !data.edges || !data.edges.length) {
		console.info('Graph not created!');
		return;
	}
	
	graph.beginUpdate();
	
	for (var i = 0; i < data.nodes.length; i++) {
		//console.info('Node : id - ' + data.nodes[i].id);
		graph.addNode(data.nodes[i].id, data.nodes[i]);
	}
	
	for (var i = 0; i < data.edges.length; i++) {
		//console.info('Link : from - ' + data.edges[i].from + ' to - ' + data.edges[i].to);
		graph.addLink(data.edges[i].from, data.edges[i].to, data.edges[i]);
	}
		
	graph.endUpdate();
	console.info('Graph created!');
}

function addEventListeners(graph, threeGraphics, elementId) {
	var element = document.getElementById(elementId);
	if (element) {
		element.addEventListener('click', function(){
			graph.forEachLink(function(link){
				var linkUI = threeGraphics.getLinkUI(link.id);
				if (linkUI)
					linkUI.material.opacity = linkUI.material.opacity > 0 ? 0 : 1;
			});
		});
	}
}

function getData(graphId,graphType) {
	jQuery.ajax({
		type : 'GET',
		async : true,
		cashe : false,
		dataType : 'json',
		// url : 'js/4080446.json',
		url : 'rest/'+graphType,
		success : function(data) {
			$('#busy').remove();
			console.info(data);
			if (!data) {
				//clearInterval(timerId);
			} else {
				ngraph.buildGraph(data);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus, errorThrown);
			$('#busy').remove();
			alert('Error occured!');
			//clearInterval(timerId);
		} 
	});
}
module.exports.main = function () {

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/threex.domevents.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/three.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);
	
	var graphId = $('#graphId').val();
	console.info(graphId);
	if (graphId) {
		getData(graphId);
		timerId = setInterval(function(){getData(graphId)},10000);
	} else {
		console.info('No graphId');
	}

};

module.exports.buildGraph = function (data) {
	
	var graph = graph ? graph : require('ngraph.graph')();
	var threeGraphics = threeGraphics ? threeGraphics : require('ngraph.three')(graph,{ interactive: true });
	var THREE = THREE ? THREE : threeGraphics.THREE;
	var camera = camera ? camera : threeGraphics.camera;
	
	var domEvents = domEvents ? domEvents : new THREEx.DomEvents(camera, threeGraphics.renderer.domElement, THREE);
	
	 // tell graphics we want custom UI
	threeGraphics.createNodeUI(function (node) {     
		var nodeGeometry = new THREE.SphereGeometry(node.data.size);
		var nodeMaterial = new THREE.MeshBasicMaterial(/*{ color: node.data.color }*/);
		
		var result = new THREE.Mesh(nodeGeometry, nodeMaterial);
		result['nodeId'] =  node.id;
		domEvents.addEventListener(result, 'mouseover',onMouseOver);
		domEvents.addEventListener(result, 'mouseout',onMouseOut);
        domEvents.addEventListener(result, 'click',onMouseClick);
		return result;
  }).createLinkUI(function(link) {
		var linkGeometry = new THREE.Geometry();
		// we don't care about position here. linkRenderer will update it
		linkGeometry.vertices.push(new THREE.Vector3(0, 0, 0));
		linkGeometry.vertices.push(new THREE.Vector3(0, 0, 0));

		var linkMaterial = new THREE.LineBasicMaterial(/*{ color: link.data.color }*/);
		return new THREE.Line(linkGeometry, linkMaterial);
  });
	
	createGraph(graph,data);
	addEventListeners(graph);
	
	threeGraphics.run();
	
	function onMouseOver(e) {
		var nodeId = e.target.nodeId;
		/*console.info('nodeId - ' + nodeId + ' nodeIdUI - ' + e.target.id);*/
		var node = graph.getNode(nodeId);
		/*console.info('node name ' + node.data.name);*/
		
		var links = node.links;
		
		for (var i=0; i < links.length; i++) {
			var link = threeGraphics.getLinkUI(links[i].id);
			if (link) link.material.color.setHex(0xff0000);
		}
	}
    
	function onMouseClick(e) {
		var nodeId = e.target.nodeId;
		var node = graph.getNode(nodeId);
        
        graph.forEachNode(function(nodes){
             nodes.data['selected'] = false;
        });
		node.data['selected'] = true;
        console.info('nodeId selected ' + node.id);
	}
	
	function onMouseOut(e) {
		var nodeId = e.target.nodeId;
		var node = graph.getNode(nodeId);
		var links = node.links;
		
		for (var i=0; i < links.length; i++) {
			var link = threeGraphics.getLinkUI(links[i].id);
			if (link) link.material.color.setHex(links[i].data.color ? links[i].data.color : 0xffffff);
		}

	}
	
    
    
function addEventListeners(graph) {
	var el = document.getElementById('butt');
	el.addEventListener("click",function(e) {
		graph.beginUpdate();
		
		var node = graph.addNode(9,{'id':'1','name':'first','size':'8','color':'white'});

        if(graph.getNode(3) && graph.getNode(9))
        {
            graph.addLink(3,9,{'id':'8','fromId':'3','toId':'9','color':'purple','linewidth':'1'});
        }
		
		graph.endUpdate();
	});
    
    var rel = document.getElementById('removeButton');
    rel.addEventListener("click",function(e) {
		graph.beginUpdate();
		console.info('to remove');
		graph.forEachNode(function(node){
            if(node.data.selected == true)
            {
                console.info('nodeId removed ' + node.id);
                var toDel = threeGraphics.getNodeUI(node.id);
                domEvents.removeEventListener(toDel, 'mouseover',onMouseOver);
                domEvents.removeEventListener(toDel, 'click',onMouseClick);
                domEvents.removeEventListener(toDel, 'mouseout',onMouseOut);
                graph.removeNode(node.id);
            }
        });
		
		graph.endUpdate();
	});
}
};

function createGraph(graph, data) {
	
	if (!data.nodes || !data.nodes.length || !data.edges || !data.edges.length) {
		console.info('Graph not created!');
		return;
	}
	
	graph.beginUpdate();
	
	for (var i = 0; i < data.nodes.length; i++) {
		console.info('Node : id - ' + data.nodes[i].id);
		graph.addNode(data.nodes[i].id, data.nodes[i]);
	}
	
	for (var i = 0; i < data.edges.length; i++) {
		console.info('Link : from - ' + data.edges[i].from + ' to - ' + data.edges[i].to);
		graph.addLink(data.edges[i].from, data.edges[i].to, data.edges[i]);
	}
		
	graph.endUpdate();
	console.info('Graph created!');
}

function getData(graphId) {
	jQuery.ajax({
		type : 'GET',
		async : true,
		cashe : false,
		dataType : 'json',
		url : 'rest/pull?id='+graphId,
		success : function(data) {
			console.info(data);
			if (!data) {
				clearInterval(timerId);
			} else {
				ngraph.buildGraph(data);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus, errorThrown);
			alert('Error occured!');
		} 
	});
}
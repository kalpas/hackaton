module.exports.main = function () {

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/threex.domevents.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);

	var fileref=document.createElement('script');
	fileref.setAttribute("type","text/javascript");
	fileref.setAttribute("src", "js/three.js");
	document.getElementsByTagName("head")[0].appendChild(fileref);
	

};

module.exports.buildGraph = function (data) {
	
	var graph = require('ngraph.graph')();
	var threeGraphics = require('ngraph.three')(graph,{ interactive: true, container: document.getElementById('container') });
	var THREE = threeGraphics.THREE;
	var camera = threeGraphics.camera;
	
	var domEvents = new THREEx.DomEvents(camera, threeGraphics.renderer.domElement, THREE);
	
	 // tell graphics we want custom UI
	threeGraphics.createNodeUI(function (node) {     
		var nodeGeometry = new THREE.SphereGeometry(node.data.size);
		var nodeMaterial = new THREE.MeshBasicMaterial({ color: node.data.color });
		
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

		var linkMaterial = new THREE.LineBasicMaterial({ color: link.data.color });
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
			if (link) link.material.color.setStyle(links[i].data.color);
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
	graph.beginUpdate();
	
	for (var i = 0; i < data.nodes.length; i++) {
		graph.addNode(data.nodes[i].id, data.nodes[i]);
	}
	
	for (var i = 0; i < data.edges.length; i++) {
		graph.addLink(data.edges[i].fromId, data.edges[i].toId, data.edges[i]);
	}
		
	graph.endUpdate();
}

package myGalaxy.graphing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import myGalaxy.VK.VkDataProvider;
import myGalaxy.domain.Edge;
import myGalaxy.domain.Graph;
import myGalaxy.domain.Node;
import myGalaxy.inst.InstagramDataProvider;
import myGalaxy.inst.api.Users;
import myGalaxy.inst.domain.InstResponse;
import myGalaxy.inst.domain.User;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;

public class GraphBuilder {
	
	private static final Double maxSize = 15.0;


	public Graph build(DataProvider provider, String userId) {
		Graph graph = new Graph();

		Multimap<Node, Node> map = provider.getData(userId);
		
		Set<Node> userSet = new HashSet<>();
		userSet.addAll(map.values());
		userSet.addAll(map.keys());
		
		int maxDegree = calculateDegree(userSet, map);
		calculateSize(userSet,maxDegree);
		
		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : map.entries()) {
			edges.add(new Edge(entry.getKey(), entry.getValue()));
		}
		graph.addAllEdges(edges);
		

		return graph;
	}
	
	public Graph build(VkDataProvider vkProvider, InstagramDataProvider instagramProvider, String vkUserId,String instaUserId, String accessToken,Users instaUsersApi) {
		Graph graph = new Graph();
		Multimap<Node, Node> vkMap = vkProvider.getData(vkUserId);
		Multimap<Node, Node> instaMap = instagramProvider.getData(instaUserId);
		
		for(Node vkUser: vkMap.keySet()){
			String instaAccount = vkUser.additionalProperties.get(Node.INSTA);
			if(instaAccount!=null){
				InstResponse instResponse = instaUsersApi.search(instaAccount, accessToken);
				if(instResponse!= null && instResponse.data!=null&& !instResponse.data.isEmpty()){
					User instaUser =instResponse.data.get(0); 
					Collection<Node> instaNodes = instaMap.get(new Node(instaUser));
					vkMap.putAll(vkUser, instaNodes);
					for(Node instaNode: instaNodes){
						vkMap.putAll(instaNode,instaMap.get(instaNode));
					}
				}
			}
		}
		
		Set<Node> userSet = new HashSet<>();
		userSet.addAll(vkMap.values());
		userSet.addAll(vkMap.keys());
		
		int maxDegree = calculateDegree(userSet, vkMap);
		calculateSize(userSet,maxDegree);
		
		graph.addAllNodes(userSet);

		List<Edge> edges = new ArrayList<>();
		for (Map.Entry<Node, Node> entry : vkMap.entries()) {
			edges.add(new Edge(entry.getKey(), entry.getValue()));
		}
		graph.addAllEdges(edges);
		
		return graph;
		
	}
	
	private int calculateDegree(Set<Node> set, Multimap<Node, Node> map) {
		int maxDegree = 0;
		for(Node element: set){
			int degree = map.get(element).size();
			degree += HashMultiset.create(map.values()).count(element);
			element.additionalProperties.put(Node.DEGREE, String.valueOf(degree));
			if (degree > maxDegree){
				maxDegree = degree;
			}
		}
		return maxDegree;
	}
	
	private void calculateSize(Set<Node> set, int maxDegree){
		for(Node element: set){
			int degree = Integer.valueOf(element.additionalProperties.get(Node.DEGREE)); 
			element.setSize(String.valueOf(degree*maxSize/maxDegree+3));
		}
	}
	
}

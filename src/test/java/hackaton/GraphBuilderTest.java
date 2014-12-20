package hackaton;

import mygalaxy.domain.Graph;
import mygalaxy.graphing.DataProvider;
import mygalaxy.graphing.GraphBuilder;
import mygalaxy.vk.VkDataProvider;

import org.junit.Test;

public class GraphBuilderTest {
	
	@Test
	public void test(){
		GraphBuilder builder = new GraphBuilder();
		DataProvider provider = new VkDataProvider("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		Graph graph = builder.build(provider, "139270103");
		System.out.println(graph.getNodes().size());
		
	}

}

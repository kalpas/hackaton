package hackaton;

import mygalaxy.domain.Graph;
import mygalaxy.graphing.GraphBuilder;
import mygalaxy.vk.VkDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class GraphBuilderTest {

	private VkDataProvider provider;

	@Test
	public void test() {
		GraphBuilder builder = new GraphBuilder();
		provider.setAccessToken("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		Graph graph = builder.build(provider, "139270103");
		System.out.println(graph.getNodes().size());

	}

}

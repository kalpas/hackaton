package hackaton;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import mygalaxy.domain.Edge;
import mygalaxy.domain.Node;
import mygalaxy.graphing.QueueHolder;
import mygalaxy.graphing.QueuedVKDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class QDPtest {

	@Autowired
	private QueuedVKDataProvider provider;

	@Test
	public void test() throws InterruptedException {
		provider.setAccessToken("10d9a1a5e8606ac47cd89fb8a97c2f5dadb39e4486cd47caf950356d7939648e859b8c4097a0a6a6241fc");

		QueueHolder queues = provider.submitTask("1080446");

		assertNotNull(queues);

		int nodes = 0;
		int edges = 0;

		while (!queues.finished) {
			Thread.sleep(10000);
			List<Node> list = new ArrayList<>();
			Node element = queues.nodeQueue.poll();
			while (element != null) {
				list.add(element);
				element = queues.nodeQueue.poll();
			}

			for (Node n : list) {
				System.out.println(n.getName());
				nodes++;
			}

			List<Edge> edgeList = new ArrayList<>();
			Edge edge = queues.edgeQueue.poll();
			while (edge != null) {
				edgeList.add(edge);
				edge = queues.edgeQueue.poll();
			}

			for (Edge e : edgeList) {
				System.out.println(e.getId());
				edges++;
			}
		}

		synchronized (queues.finished) {
			queues.finished.wait();
		}

		assertTrue(queues.finished);

		System.out.println(nodes + " " + edges);

	}

}

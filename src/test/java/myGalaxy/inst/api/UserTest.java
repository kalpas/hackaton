package myGalaxy.inst.api;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class UserTest {
	@Autowired
	private Users users;
	
	@Test
	public void testgetFollowers() throws ClientProtocolException, IOException {
		users.search("andykalpas", "423754320.9566b27.9f050b5508fe417784e000a27b168905");
	}
}

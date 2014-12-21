package hackaton;

import java.util.List;

import mygalaxy.vk.api.domain.User;
import mygalaxy.vk.api.friends.Friends;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class FriendsTest {

	@Autowired
	private Friends f;
	
	@Test
	public void test() {


		f.setAccessToken("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		f.get("1080446");

	}

	@Test
	public void test2() {


		f.setAccessToken("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		List<User> list = f.get("1080446");

		f.batchGet(list);

	}

}

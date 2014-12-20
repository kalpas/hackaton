package hackaton;

import java.util.List;

import mygalaxy.vk.api.domain.User;
import mygalaxy.vk.api.friends.Friends;

import org.junit.Test;

public class FriendsTest {
	
	@Test
	public void test(){
		
		Friends f = new Friends("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		f.get("1080446");
		
	}
	
	
	
	@Test
	public void test2(){
		
		Friends f = new Friends("4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		List<User> list = f.get("1080446");
		
		f.batchGet(list);
		
	}

}

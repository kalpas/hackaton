package hackaton;

import myGalaxy.VK.API.friends.Friends;

import org.junit.Test;

public class FriendsTest {
	
	@Test
	public void test(){
		
		Friends f = new Friends();
		f.get("1080446", "4e2a0ae4febc91a43fb494993011796409117c82b6a70b3b9b1bbb9aff2b727c80c35876af9e268d2cc2d");
		
	}

}

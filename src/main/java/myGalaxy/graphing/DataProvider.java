package myGalaxy.graphing;

import myGalaxy.VK.API.domain.User;

import com.google.common.collect.Multimap;

public interface DataProvider {
	
	public Multimap<User, User> getData(String userId);

}

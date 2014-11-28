package myGalaxy;

import java.util.HashMap;
import java.util.Map;

import myGalaxy.domain.Graph;

public class MyGalaxy {
	
	public static final String SESSION_USER_ID = "userId";
	public static final String SESSION_EXPIRES_IN = "expiresIn";
	public static final String SESSION_ACCESS_TOKEN = "accessToken";

	public static final Map<String,Graph> GRAPH_POOL = new HashMap<String,Graph>();
}

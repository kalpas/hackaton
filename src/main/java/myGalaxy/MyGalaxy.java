package myGalaxy;

import java.util.HashMap;
import java.util.Map;

import myGalaxy.domain.Graph;

public class MyGalaxy {
	
	public static final String VK_SESSION_USER_ID = "user";
	public static final String VK_SESSION_EXPIRES_IN = "expiresIn";
	public static final String VK_SESSION_ACCESS_TOKEN = "accessToken";

	public static final String INST_SESSION_USER_ID = "instuser";
	public static final String INST_SESSION_ACCESS_TOKEN = "instaccessToken";

	public static final Map<String,Graph> GRAPH_POOL = new HashMap<String,Graph>();
}

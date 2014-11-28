package myGalaxy;

import java.util.HashMap;
import java.util.Map;

import myGalaxy.graphing.QueueHolder;

public class MyGalaxy {
	
	public static final String VK_SESSION_USER_ID = "user";
	public static final String VK_SESSION_EXPIRES_IN = "expiresIn";
	public static final String VK_SESSION_ACCESS_TOKEN = "accessToken";

	public static final String INST_SESSION_USER_ID = "instuser";
	public static final String INST_SESSION_ACCESS_TOKEN = "instaccessToken";

	public static final Map<String,QueueHolder> GRAPH_POOL = new HashMap<String,QueueHolder>();
}

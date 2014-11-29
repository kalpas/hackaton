package myGalaxy.inst.domain;

import java.util.ArrayList;
import java.util.List;

public class InstResponse {
	public Pagination pagination;
	public Meta meta;
	public List<User> data = new ArrayList<User>();
}

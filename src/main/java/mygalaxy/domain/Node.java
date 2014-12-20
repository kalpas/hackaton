package mygalaxy.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mygalaxy.graphing.Graphing;
import mygalaxy.vk.api.domain.User;

public class Node implements IPassable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2674138115435304464L;
	private String id;

	private String name;
	private String size = Graphing.DEFAULT_NODE_SIZE.toString();
	private int communityId;
	private boolean passed;
	private List<String> photos;
	private String color = generateColor();

	public Map<String, String> additionalProperties = new HashMap<>();

	public transient static final String DEGREE = "degree";
	public transient static final String INSTA = "insta";
	public transient static final String VK = "vk";
	public transient static final String TYPE = "type";
	public transient static final String LINK = "link";
	public transient static final String MODULARITY_CLASS = "modularity_class";

	public Node() {

	}

	private String generateColor() {
		Random rand = new Random();
		int r = rand.nextInt(127) + 128;
		int g = rand.nextInt(127) + 128;
		int b = rand.nextInt(127) + 128;
		return "#" + Integer.toHexString(r) + Integer.toHexString(g)
				+ Integer.toHexString(b);

	}

	public Node(User user) {
		this.setId(user.uid);
		this.setName(user.first_name + " " + user.last_name);
		this.setPhotos(Arrays.asList(user.photo_200_orig));
		this.additionalProperties.put(INSTA, user.instagram);
		this.additionalProperties.put(TYPE, VK);
		this.additionalProperties.put(LINK, "http://vk.com/id"+this.getId());
	}

	public Node(mygalaxy.inst.domain.User user) {
		this.setId(user.id);
		this.setName(user.full_name);
		this.setPhotos(Arrays.asList(user.profile_picture));
		this.additionalProperties.put(TYPE, INSTA);
		this.additionalProperties.put(LINK, "http://instagram.com/"+user.username);
		this.additionalProperties.put("username", user.username);
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}

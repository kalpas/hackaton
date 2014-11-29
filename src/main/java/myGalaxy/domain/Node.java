package myGalaxy.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import myGalaxy.VK.API.domain.User;
import myGalaxy.graphing.Graphing;

public class Node implements IPassable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674138115435304464L;
	private String id;

	private String name;
	private String size = Graphing.DEFAULT_NODE_SIZE.toString();
	private String communityId;
	private boolean passed;
	private List<String> photos;
	private String color = generateColor();

	public Map<String, String> additionalProperties = new HashMap<>();

	public Node() {

	}

	private String generateColor() {
		Random rand = new Random();
		int r = rand.nextInt(127) +128;
		int g = rand.nextInt(127) +128;
		int b = rand.nextInt(127) +128;
		return "0x"+ Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
		
	}

	public Node(User user) {
		this.setId(user.uid);
		this.setName(user.first_name + " " + user.last_name);
		this.setPhotos(Arrays.asList(user.photo_200_orig));
	}

	public Node(myGalaxy.inst.domain.User user) {
		this.setId(user.id);
		this.setName(user.full_name);
		this.setPhotos(Arrays.asList(user.profile_picture));
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

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
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

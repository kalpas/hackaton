package mygalaxy.vk.api.domain;

public class User {

	public String   uid;
	public String   first_name;
	public String   last_name;
	public String   sex;
	public String   country;
	public String   online;
	public String   user_id;
	public String   deactivated;
	public String[] lists;
	public String   photo_200_orig;
	public String   instagram;
	public String   skype;
	public String   facebook;
	public String   twitter;
	public String   livejournal;
	public String   facebook_name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		User other = (User) obj;
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return first_name + " " + last_name;
	}

}

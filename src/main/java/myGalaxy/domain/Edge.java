package myGalaxy.domain;

import java.io.Serializable;

public class Edge implements IPassable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8646042769087796555L;
	private String id;
	private String from;
	private String to;
	private boolean passed;
	private Float weight = 1f;
	private String color = "#440077";

	public Edge() {
	}

	public Edge(Node key, Node value) {
		this.setFrom(key.getId());
		this.setTo(value.getId());
		this.setId(key.getId() + value.getId());
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
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
		Edge other = (Edge) obj;
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

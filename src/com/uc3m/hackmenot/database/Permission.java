package com.uc3m.hackmenot.database;

public class Permission {
	private String permission;
	private int threat_level;
	private String description;

	public String getPermission() {
		return permission;
	}

	public int getThreatLevel() {
		return threat_level;
	}

	public String getDescription() {
		return description;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setThreatLevel(int threat_level) {
		this.threat_level = threat_level;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return permission + " " + threat_level + " " + description;
	}
}
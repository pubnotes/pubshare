package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable  {
	
	private int ID;
	private String username;
	private String useremail;
	private String password;
	private boolean onSigned;
	private String[] friends;
	private String[] tags;
	private HashMap<String, List<String>> tagToUsers;
	
	public HashMap<String, List<String>> getTagToUsers() {
		return tagToUsers;
	}


	public void setTagToUsers(HashMap<String, List<String>> tagToUsers) {
		this.tagToUsers = tagToUsers;
	}

	private Profile userprofile;
	
	public User(){
		this.ID = 0;
		this.username = "";
		this.useremail = "";
		this.password = "";
		this.onSigned = false;
		this.friends = null;
		this.tags = null;
		this.tagToUsers = new HashMap<String, List<String>>();
		this.userprofile = new Profile();
	}
	
	
	public List<String> getUsersFromTag(String tag) {
		return tagToUsers.get(tag);
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isOnSigned() {
		return onSigned;
	}
	public void setOnSigned(boolean onSigned) {
		this.onSigned = onSigned;
	}
	
	public Profile getUserprofile() {
		return userprofile;
	}
	public void setUserprofile(Profile userprofile) {
		this.userprofile = userprofile;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
}

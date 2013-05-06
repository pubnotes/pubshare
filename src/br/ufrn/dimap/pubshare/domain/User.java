package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable  {
	
	@JsonProperty
	private int id;
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String useremail;
	
	@JsonProperty
	private String password;
	
	@JsonIgnore
	private boolean onSigned;
	@JsonIgnore
	private String[] friends;
	@JsonIgnore
	private String[] tags;
	@JsonIgnore
	private HashMap<String, List<String>> tagToUsers;
	@JsonIgnore
	private Profile userprofile;
	
	public HashMap<String, List<String>> getTagToUsers() {
		return tagToUsers;
	}


	public void setTagToUsers(HashMap<String, List<String>> tagToUsers) {
		this.tagToUsers = tagToUsers;
	}

	
	public User(){
		this.id = 0;
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
	
	
	public int getId() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
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

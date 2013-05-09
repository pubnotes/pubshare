package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable  {
	
	@JsonProperty
	private long id;
	
	@JsonProperty
	private String username;
	@JsonProperty
	private String useremail;
	@JsonProperty
	private String password;
	@JsonProperty
	private boolean onsigned;
	@JsonProperty
	private List<String> friends;
	@JsonProperty
	private List<String> tags;
	@JsonProperty
	private HashMap<String, List<String>> tagToUsers;
	@JsonProperty
	private Profile userprofile;
	
	public User(){
		this.id = 0;
		this.onsigned = false;
		this.friends = new ArrayList<String>();
		this.tags = new ArrayList<String>();
		this.tagToUsers = new HashMap<String, List<String>>();
		this.userprofile = new Profile();
	}
	
	
	public HashMap<String, List<String>> getTagToUsers() {
		return tagToUsers;
	}


	public void setTagToUsers(HashMap<String, List<String>> tagToUsers) {
		this.tagToUsers = tagToUsers;
	}

	public List<String> getUsersFromTag(String tag) {
		return tagToUsers.get(tag);
	}
	
	
	public long getId() {
		return id;
	}
	public void setID(long id) {
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
	public boolean isOnsigned() {
		return onsigned;
	}
	public void setOnsigned(boolean onSigned) {
		this.onsigned = onSigned;
	}
	
	public Profile getUserprofile() {
		return userprofile;
	}
	public void setUserprofile(Profile userprofile) {
		this.userprofile = userprofile;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}

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
	private List<User> friends;
	@JsonProperty
	private List<Tag> tags;
	@JsonProperty
	private Profile userprofile;
	
	public User(){
		this.id = 0;
		this.onsigned = false;
		this.friends = new ArrayList<User>();
		this.tags = new ArrayList<Tag>();
		this.userprofile = new Profile();
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}


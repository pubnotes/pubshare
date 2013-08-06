/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	private List<TagUser> markedTags;
	@JsonProperty
	private Profile userprofile;
	
	public User(){
		this.id = 0;
		this.onsigned = false;
		this.friends = new ArrayList<User>();
		this.tags = new ArrayList<Tag>();
		this.markedTags = new ArrayList<TagUser>();
		this.userprofile = new Profile();
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public List<TagUser> getMarkedTags() {
		return markedTags;
	}

	public void setMarkedTags(List<TagUser> markedTags) {
		this.markedTags = markedTags;
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


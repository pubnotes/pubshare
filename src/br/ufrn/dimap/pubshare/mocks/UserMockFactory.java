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

package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.ufrn.dimap.pubshare.people.FriendsListAdapter;
import br.ufrn.dimap.pubshare.domain.Profile;
import br.ufrn.dimap.pubshare.domain.Tag;
import br.ufrn.dimap.pubshare.domain.User;

public class UserMockFactory {
	
	public static String [] usersNames = {
		"Juvane",
		"Juliana",
		"Daniel",
		"Adorilson",
		"Lucas"
	};
	
	public static String [] usersMails = {
		"Juvane@gmail.com",
		"Juliana@gmail.com",
		"Daniel@gmail.com",
		"Adorilson@gmail.com",
		"Lucas@gmail.com"
	};
	
	public static String [] usersPasswords = {
		"123333",
		"123333",
		"123333",
		"123333",
		"123333"
	};
	
	
	public static List<String> usersFriends = new ArrayList<String>(
		    Arrays.asList("Danilo", "Thiago", "Kaio", "Juliana","Itamir" ));
	
	public static List<String> getUsersFriends() {
		return usersFriends;
	}

	public static List<String> usersTags = new ArrayList<String>(
		    Arrays.asList("Family", "University", "work", "colleagues","research group" ));
	
	public static List<Tag> getUsersTags(List<String> usersTags) {
		List<Tag> tags = new ArrayList<Tag>();
		for(String userTag : usersTags)
		{
			Tag tag = new Tag();
			tag.setDescription(userTag);
			tags.add(tag);
		}
		return tags;
	}

	public static List<User> makeUserList(){
		List<User> users = new ArrayList<User>();
		Profile p = makeSingleProfile();

		for( int i = 0; i < 5 ; i++ ){
			User user = new User();
			user.setId(i+1);
			user.setOnsigned(true);
			user.setUsername(usersNames[i]);
			user.setUseremail(usersMails[i]);
			user.setPassword(usersPasswords[i]);
			user.setFriends(makeFriendList());
			user.setTags(getUsersTags(usersTags));
			user.setUserprofile(p);
			//associateFriendsWithTags(user.getFriends(), user.getTags());
			
			users.add(user);
		}
		return users;
	}
	
	public static List<User> makeFriendList(){
		List<User> friends = new ArrayList<User>();
		Profile p = makeSingleProfile();

		for( int i = 0; i < 5 ; i++ ){
			User friend = new User();
			friend.setId(i+1);
			friend.setOnsigned(true);
			friend.setUsername(usersNames[i]);
			friend.setUseremail(usersMails[i]);
			friend.setPassword(usersPasswords[i]);
			//friend.setFriends(getFriends(usersFriends));
			//friend.setTags(getUsersTags(usersTags));
			friend.setUserprofile(p);
			//associateFriendsWithTags(user.getFriends(), user.getTags());
			
			friends.add(friend);
		}
		return friends;
	}
	
	public static List<User> getFriends (List<String> usersFriends){
		List<User> friends = new ArrayList<User>();
		for (int i = 0; i < usersFriends.size(); i++) {
			//neste caso, pra cada amigo, deve-se pegar o aboutme do profile
			User friend = new User();
			friend.setUsername(usersFriends.get(i));
			friend.getUserprofile().setAboutme("Algo sobre mim...");
			friends.add(friend);
		}
		return friends;
	}
	
	/*public static void associateFriendsWithTags(List<User> friends ,List<Tag> tags){
		
		for(int i = 0; i < 5; i++)
		{
			User friend = friends.get(i);
			Tag tag = tags.get(i);
			friend.setTag(tag);
		}
	}*/
	
	
	public static Profile makeSingleProfile(){
		Profile profile = new Profile();
		 profile.setAboutme("Algo sobre mim");
		 profile.setBirthday("23 de dezembro de 1989");
		 profile.setDegree("master");
		 profile.setEmail("xyz@gmail.com");
		 profile.setFacebook("Userfacebook");
		 profile.setGender("...");
		 profile.setInstitution("UFRN");
		 profile.setLocation("Rua...");
		 profile.setPhone("222-3333");
		 //adicionando um id no mock
		 profile.setId(1L);
		 
		 return profile;
	}
	
	/**public static Profile makeSingleProfile(){
		Profile profile = new Profile();
		 profile.setAboutme("Algo sobre mim");
		 profile.setBirthday("23 de dezembro de 1989");
		 profile.setDegree("master");
		 profile.setEmail("xyz@gmail.com");
		 profile.setFacebook("Userfacebook");
		 profile.setGender("...");
		 profile.setInstitution("UFRN");
		 profile.setLocation("Rua...");
		 profile.setPhone("222-3333");
		 return profile;
	}**/
	
	/**public static User makeSingleUser(){
		User u = new User();

		//u.setFriends(getUsersFriends());
		u.setId(0);
		u.setOnsigned(true);
		u.setPassword("1234");
		//u.setTags(getUsersTags());
		//u.setTagToUsers(makeHash());
		u.setUseremail("xy@gmail.com");
		u.setUsername("Usuario");
		u.setUserprofile(makeSingleProfile());
		
		return u;
	}**/
	
	public static User makeSingleUser(){
		User u = new User();

		//u.setFriends(getUsersFriends());
		u.setId(1L);
		u.setOnsigned(true);
		u.setPassword("1234");
		//u.setTags(getUsersTags());
		//u.setTagToUsers(makeHash());
		u.setUseremail("xy@gmail.com");
		u.setUsername("Usuario");
		u.setUserprofile(makeSingleProfile());
		
		return u;
	}
}



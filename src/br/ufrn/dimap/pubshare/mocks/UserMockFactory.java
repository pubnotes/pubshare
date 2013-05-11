package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.ufrn.dimap.pubshare.adapters.FriendsListAdapter;
import br.ufrn.dimap.pubshare.domain.Friend;
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
			associateFriendsWithTags(user.getFriends(), user.getTags());
			
			users.add(user);
		}
		return users;
	}
	
	public static List<Friend> makeFriendList(){
		List<Friend> friends = new ArrayList<Friend>();
		Profile p = makeSingleProfile();

		for( int i = 0; i < 5 ; i++ ){
			Friend friend = new Friend();
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
	
	public static List<Friend> getFriends (List<String> usersFriends){
		List<Friend> friends = new ArrayList<Friend>();
		for (int i = 0; i < usersFriends.size(); i++) {
			//neste caso, pra cada amigo, deve-se pegar o aboutme do profile
			Friend friend = new Friend();
			friend.setUsername(usersFriends.get(i));
			friend.getUserprofile().setAboutme("Algo sobre mim...");
			friends.add(friend);
		}
		return friends;
	}
	
	public static void associateFriendsWithTags(List<Friend> friends ,List<Tag> tags){
		
		for(int i = 0; i < 5; i++)
		{
			Friend friend = friends.get(i);
			Tag tag = tags.get(i);
			friend.setTag(tag);
		}
	}
	
	
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
		 
		 return profile;
	}
	
	public static User makeSingleUser(){
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
	}
}



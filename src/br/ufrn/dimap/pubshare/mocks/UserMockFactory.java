package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Profile;
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
	
	public static String [] usersFriends = {
		"Danilo",
		"Thiago",
		"Kaio",
		"Juliana",
		"Itamir"
	};
	
	public static String [] usersTags = {
		"Family",
		"University",
		"work",
		"colleagues",
		"research group"
	};
	
	
	public static List<User> makeUserList(){
		List<User> users = new ArrayList<User>();
		Profile p = makeSingleProfile();
		
		for( int i = 0; i < 5 ; i++ ){
			User user = new User();
			user.setID(i);
			user.setOnSigned(true);
			user.setUsername(usersNames[i]);
			user.setUseremail(usersMails[i]);
			user.setPassword(usersPasswords[i]);
			user.setFriends(usersFriends);
			user.setTags(usersTags);
			user.setUserprofile(p);
			user.setTagToUsers(makeHash());
			
			users.add(user);
		}
		return users;
	}
	
	public static List<User> getFriends (User user){
		List<User> u = new ArrayList<User>();
		for (String friendname : user.getFriends()) {
			//neste caso, pra cada amigo, deve-se pegar o aboutme do profile
			User us = new User();
			us.setUsername(friendname);
			us.getUserprofile().setAboutme("Algo sobre mim...");
			u.add(us);
		}
		return u;
	}
	
	public static HashMap<String, List<String>> makeHash(){
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		
		for (String tag : usersTags) {
			h.put(tag, new ArrayList<String>());
			
			for(String user : usersFriends){
				h.get(tag).add(user);
			}
		}
		return h;
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
		
		u.setFriends(usersFriends);
		u.setID(0);
		u.setOnSigned(true);
		u.setPassword("1234");
		u.setTags(usersTags);
		u.setTagToUsers(makeHash());
		u.setUseremail("xy@gmail.com");
		u.setUsername("Usuario");
		u.setUserprofile(makeSingleProfile());
		
		return u;
	}
}



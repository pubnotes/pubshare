package br.ufrn.dimap.pubshare.restclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.restclient.results.AuthenticationResult;
import br.ufrn.dimap.pubshare.util.Constants;

public class LoginRestClient {

	public User login(String login, String senha) {
		
			HttpHeaders requestHeaders = new HttpHeaders(); 
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);						 
			
			Map<String, String> body = new HashMap<String, String>();     			
			body.put("useremail", login );
			body.put("password", senha );			
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			 
			ResponseEntity<User> response = restTemplate.exchange(  
					Constants.URL_SERVER + "/user/login", 
					HttpMethod.POST, 
					new HttpEntity<Object>(body, requestHeaders), User.class);
			
			User userlogado = response.getBody();
			return userlogado;
	}
}

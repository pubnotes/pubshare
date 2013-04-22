package br.ufrn.dimap.pubshare.restclient;

import java.util.Collections;
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

import br.ufrn.dimap.pubshare.util.Constants;

public class LoginRestClient {

	public boolean login(String login, String senha) {
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);						 
			
			Map<String, String> body = new HashMap<String, String>();     			
			body.put("username", login );
			body.put("password", senha );			
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());			
			ResponseEntity<String> response = restTemplate.exchange(  
					Constants.URL_SERVER + "/user/login", 
					HttpMethod.POST, 
					new HttpEntity<Object>(body, requestHeaders), String.class);
			
			return response.getStatusCode().ordinal() == 200; // && what?
			
 		} catch (HttpClientErrorException e) {			
 			e.printStackTrace();
			return false;
		} 
	}
}

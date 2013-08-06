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

package br.ufrn.dimap.pubshare.restclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufrn.dimap.pubshare.domain.User;
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

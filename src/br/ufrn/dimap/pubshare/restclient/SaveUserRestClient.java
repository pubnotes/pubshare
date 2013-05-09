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

import android.R.bool;
import android.os.AsyncTask;
import android.util.Log;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.restclient.results.AuthenticationResult;
import br.ufrn.dimap.pubshare.restclient.results.UserResult;
import br.ufrn.dimap.pubshare.util.Constants;

public class SaveUserRestClient extends AsyncTask<User, Void,UserResult >{

	@Override
	protected UserResult doInBackground(User... users) {

		User user = (User)users[0];
		
		try{
			
			HttpHeaders requestHeaders = new HttpHeaders(); 
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);	
			
			//montando dados a serem enviados ao servidor
			/*Map<String, String> body = new HashMap<String, String>();     			
			body.put("username", user.getUsername());
			body.put("useremail", user.getUseremail());
			body.put("password", user.getPassword());*/
			
			RestTemplate restTemplate = new RestTemplate();
		 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			ResponseEntity<UserResult> response = restTemplate.exchange(  
					Constants.URL_SERVER + "/user/save", 
					HttpMethod.POST, 
					new HttpEntity<Object>(user, requestHeaders), UserResult.class);
			
			Log.d("SaveUserRestClient", "ResponseEntity " + response.getBody() );
			
			
			return response.getBody();
			
		}catch(HttpClientErrorException e){
			Log.d("SaveUserRestClient", "Erro ao tentar salvar usuario: " + e.getMessage() );
 			e.printStackTrace();
			return new UserResult();
		}
	}
}

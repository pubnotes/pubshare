package br.ufrn.dimap.pubshare.restclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.util.Constants;
import android.os.AsyncTask;

/**
 * This class is made for getting the evaluations of articles from the
 * pubnotes-server 
 * @author Daniel
 *
 */
public class GetEvaluationRestClient extends AsyncTask<Article, Void, Evaluation[]>
{

	@Override
	protected Evaluation[] doInBackground(Article... arg0) 
	{
		// TODO Auto-generated method stub
		Article article = (Article) arg0[0];
		HttpHeaders headers = new HttpHeaders();
		
		Map<String, String> body = new HashMap<String,String>();
		body.put("id", String.valueOf(article.getId()));
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/evaluation/" + article.getId();
		
		ResponseEntity<Evaluation[]> entity = restTemplate.exchange(
				Constants.URL_SERVER + url, 
				HttpMethod.GET, 
				new HttpEntity<Object>(body, headers), 
				Evaluation[].class);
		
		Evaluation[] evaluationsFromUser = entity.getBody();
				
		return evaluationsFromUser;
	}
}

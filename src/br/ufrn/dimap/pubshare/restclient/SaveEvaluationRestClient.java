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

import android.os.AsyncTask;
import android.util.Log;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.restclient.results.EvaluationResult;
import br.ufrn.dimap.pubshare.util.Constants;

public class SaveEvaluationRestClient extends AsyncTask<Evaluation, Void, EvaluationResult> {

	protected EvaluationResult doInBackground(Evaluation... obj) {
		
		Evaluation evaluation = (Evaluation)obj[0];
		
		try {
			HttpHeaders requestHeaders = new HttpHeaders(); 
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			//articleBody.
			
			//evaluationBody.put("user", String.valueOf(evaluation.getUser().getId()));
			//evaluationBody.put("article", String.valueOf(evaluation.getArticle().getId()));
			//apenas testando
			//evaluationBody.put("evalDate", 1367722800000L+"");
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			String url = "";
			if (evaluation.getId()==0){
				//url = "/evaluation/new";
				url = "/evaluation/";
			}else{
				url = "/evaluation/" + evaluation.getId()+ "/edit";
			}
			
			ResponseEntity<EvaluationResult> response = restTemplate.exchange(  
					Constants.URL_SERVER_DANIEL + url, 
					HttpMethod.POST, 
					new HttpEntity<Object>(evaluation, requestHeaders), EvaluationResult.class);
			
			Log.d("SaveEvaluationRestClient", "ResponseEntity " + response.getBody() );
			
			
			return response.getBody();
			
 		} catch (HttpClientErrorException e) {		
 			Log.d("SaveEvaluationRestClient", "Erro ao tentar editar avaliação: " + e.getMessage() );
 			e.printStackTrace();
			return new EvaluationResult();
		}
	}

}

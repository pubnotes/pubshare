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
			
			Map<String, String> body = new HashMap<String, String>();     			
			body.put("originality", Float.toString(evaluation.getOriginality()));
			body.put("contribution", Float.toString(evaluation.getContribution()));
			body.put("relevance", Float.toString(evaluation.getRelevance()));
			body.put("readability", Float.toString(evaluation.getReadability()));
			body.put("relatedWorks", Float.toString(evaluation.getRelatedWorks()));
			body.put("reviewerFamiliarity", Float.toString(evaluation.getReviewerFamiliarity()));
			body.put("id_user", Integer.toString(evaluation.getUser().getID()));
			body.put("id_article", Integer.toString(evaluation.getArticle().getId()));
			//body.put("evalDate", ???);
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			String url = "";
			if (evaluation.getId()==0){
				url = "/evaluation/new";
			}else{
				url = "/evaluation/" + evaluation.getId()+ "/edit";
			}
			
			ResponseEntity<EvaluationResult> response = restTemplate.exchange(  
					Constants.URL_SERVER + url, 
					HttpMethod.POST, 
					new HttpEntity<Object>(body, requestHeaders), EvaluationResult.class);
			
			Log.d("SaveEvaluationRestClient", "ResponseEntity " + response.getBody() );
			
			
			return response.getBody();
			
 		} catch (HttpClientErrorException e) {		
 			Log.d("SaveEvaluationRestClient", "Erro ao tentar editar avaliação: " + e.getMessage() );
 			e.printStackTrace();
			return new EvaluationResult();
		}
	}

}

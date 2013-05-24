package br.ufrn.dimap.pubshare.recomendation;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Recommendation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.util.Constants;

public class Recommendation_list extends PubnotesActivity {

	private ListView usersListView;
	private ArrayAdapter<String> adapter;
	
	User userlogado;
	
	
	
	AsyncTask<Void, Void, Recommendation[]> async; 
	ArrayList<String> articles = new ArrayList<String>();
	
	
	ArrayList<Recommendation> recommends = new ArrayList<Recommendation>();
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.recommendation_list);
	
	    // TODO Auto-generated method stub
	    userlogado = Recommendation_list.this.getCurrentUser();
	
	    if(userlogado != null){
	    	
	    
	    userlogado.getId();
	    
	    
	    
	    async = new AsyncTask<Void, Void, Recommendation[]>(){
			
			
			protected void onPreExecute() {
				super.onPreExecute();
			}
			
			protected Recommendation[] doInBackground(Void... arg0) {
				return getRecommendations();
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(Recommendation[] result) {
				Log.i("t", "post execute");
				if(result.length != 0){
					Log.i("t", "post execute if");
					for (int i = 0; i < result.length; i++) {
						Log.i("t", "post execute for");
						recommends.add(result[i]);
					}
					
					configureListView(recommends);
					
					//}
				}else{
					Log.i("t", "post execute else");
					Toast.makeText(Recommendation_list.this, "You do not have friends", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		};
		
		async.execute();

	    }
	}
	
	
	private Recommendation[] getRecommendations()
	{
		Log.i("teste",  "1");
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		Log.i("teste",  "2");
		RestTemplate restTemplate = new RestTemplate();
	
		Log.i("teste",  "3");
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		Log.i("teste",  "4");
		String url = "/reccommendation/?id=13";
		Log.i("teste",  "5");
//		ResponseEntity<User[]> response = restTemplate.exchange(  
//				Constants.URL_SERVER + url, 
//				HttpMethod.GET, 
//				new HttpEntity<Object>(user, requestHeaders), Recommendation[].class);
//		
		Recommendation[] recs = restTemplate.getForObject(url, Recommendation[].class);
//		Recommendation[] result = response.getBody();
		
		Log.i("teste",  "6");
		
		return recs;
	}
	
	private void configureListView(ArrayList<Recommendation> recommends) {
		// Find the ListView resource.
		Log.i("t", "configure list");
		if(recommends.size() > 0){
				for (int i = 0; i < recommends.size(); i++) {
					articles.add(recommends.get(i).getTitle());
				}

				 adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1 , articles);
					
					usersListView = (ListView) findViewById(R.id.listviewRecommendation);
					usersListView.setAdapter( adapter );
					usersListView.setOnItemClickListener(new OnItemClickListener()
					{
						public void onItemClick(AdapterView adapter, View v, int position, long id) 
						{
							Intent intent = new Intent();
				    	   	intent.setClass(Recommendation_list.this, Recommendation_View.class);
				    	   	startActivity(intent);	
						}
					});
					
		} else {
			Toast.makeText(Recommendation_list.this, "You do not have recommendations", Toast.LENGTH_SHORT).show();
		}
				
	}

	
}
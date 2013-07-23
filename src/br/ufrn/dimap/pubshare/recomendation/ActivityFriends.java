package br.ufrn.dimap.pubshare.recomendation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.Recommendation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.util.Constants;

public class ActivityFriends extends br.ufrn.dimap.pubshare.PubnotesActivity {
	
	Context tela;

	private ListView mainListView;
	private mItems[] itemss;
	private ArrayAdapter<mItems> listAdapter;
	ArrayList<String> checked = new ArrayList<String>();
	ArrayList<mItems> listItems = new ArrayList<mItems>();
	AsyncTask<User, Void, User[]> async; 
	AsyncTask<Recommendation, Void, Recommendation> asyncSend;
	ArrayList<User> users = new ArrayList<User>();
	
	User userlogado;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_recommendation);
		
		tela = this;
		userlogado = ActivityFriends.this.getCurrentUser();
		
		/** getting users from the server using the async task**/
		async = new AsyncTask<User, Void, User[]>(){
			
			
			protected void onPreExecute() {
				super.onPreExecute();
			}
			
			protected User[] doInBackground(User... users) {
				return getFriends(users[0]);
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(User[] result) {
				Log.i("t", "post execute");
				if(result.length != 0){
					Log.i("t", "post execute if");
					for (int i = 0; i < result.length; i++) {
						Log.i("t", "post execute for");
						users.add(result[i]);
					}
					
					configureListView(users);
					
					//}
				}else{
					Log.i("t", "post execute else");
					Toast.makeText(ActivityFriends.this, "You do not have friends", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		};
		
		async.execute(userlogado);
		
		Button botao = (Button) findViewById(R.id.buttonSend);
		botao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				List<Long> dest = new ArrayList<Long>();
				
				String t = "";
				for (int i = 0; i < listItems.size(); i++) {
					mItems item = listItems.get(i);
					if(item.isChecked()){
						dest.add(item.getId());
						t +=  dest.get(i)+ "-"+item.getName()+", ";
					}
							
				}
				t +=  " - Usuario: "+userlogado.getId();
				
			
				asyncSend = createAsyncTaskSendRecommendation();
				Recommendation rec = new Recommendation();
				rec.setAuthors(getIntent().getStringExtra("autor"));
				rec.setConference(getIntent().getStringExtra("conf"));
				rec.setLink(getIntent().getStringExtra("link"));
				rec.setObs(getIntent().getStringExtra("obs"));
				rec.setTitle(getIntent().getStringExtra("titulo"));
				rec.setIdDestinatarios(dest);
				rec.setIdUsuario(userlogado.getId());
				
				asyncSend.execute(rec);
				
				//Toast.makeText(ActivityFriends.this, "O artigo foi recomendado. "+t, Toast.LENGTH_SHORT).show();
				finish();
				
//				Intent intent = new Intent();
//	    	   	intent.setClass(ActivityFriends.this, RecommendationList.class);
//	    	   	startActivity(intent);	
			}
		});			
	}

	/** Holds planet data. */
	private static class mItems {
		private String name = "";
		private long id = 0;
		private boolean checked = false;

		public mItems() {
		}
		
		public mItems(User user) {
			this.name = user.getUsername();
			this.id = user.getId();
		}

		public String getName() {
			return name;
		}
		
		public long getId() {
			return id;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public String toString() {
			return name;
		}

		public void toggleChecked() {
			checked = !checked;
		}
	}

	/** Holds child views for one row. */
	private static class SelectViewHolder {
		private CheckBox checkBox;
		private TextView textView;

		public SelectViewHolder() {
		}

		public SelectViewHolder(TextView textView, CheckBox checkBox) {
			this.checkBox = checkBox;
			this.textView = textView;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public void setCheckBox(CheckBox checkBox) {
			this.checkBox = checkBox;
		}

		public TextView getTextView() {
			return textView;
		}

		public void setTextView(TextView textView) {
			this.textView = textView;
		}
	}
	
	
	
	
	
	
	
	
	
	/** Custom adapter for displaying an array of Planet objects. */
	private static class SelectArralAdapter extends ArrayAdapter<mItems> {
		private LayoutInflater inflater;

		public SelectArralAdapter(Context context, List<mItems> planetList) {
			super(context, R.layout.simplerow, R.id.rowTextView, planetList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Planet to display
			mItems planet = (mItems) this.getItem(position);

			// The child views in each row.
			CheckBox checkBox;
			TextView textView;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.simplerow, null);

				// Find the child views.
				textView = (TextView) convertView
						.findViewById(R.id.rowTextView);
				checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView.setTag(new SelectViewHolder(textView, checkBox));
				// If CheckBox is toggled, update the planet it is tagged with.
				checkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						mItems planet = (mItems) cb.getTag();
						planet.setChecked(cb.isChecked());
					}
				});
			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				SelectViewHolder viewHolder = (SelectViewHolder) convertView
						.getTag();
				checkBox = viewHolder.getCheckBox();
				textView = viewHolder.getTextView();
			}

			// Tag the CheckBox with the Planet it is displaying, so that we can
			// access the planet in onClick() when the CheckBox is toggled.
			checkBox.setTag(planet);
			// Display planet data
			checkBox.setChecked(planet.isChecked());
			textView.setText(planet.getName());
			return convertView;
		}
	}

	public Object onRetainNonConfigurationInstance() {
		return itemss;
	}

	private User[] getFriends(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/getFriends";
		
		ResponseEntity<User[]> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), User[].class);
		
		User[] result = response.getBody();
				
		return result;
	}
	
	private void configureListView(ArrayList<User> users) {
		// Find the ListView resource.
		Log.i("t", "configure list");
				mainListView = (ListView) findViewById(R.id.listViewAmigos);

				// When item is tapped, toggle checked properties of CheckBox and
				// Planet.
				mainListView
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View item,
									int position, long id) {
								mItems planet = listAdapter.getItem(position);
								planet.toggleChecked();
								SelectViewHolder viewHolder = (SelectViewHolder) item
										.getTag();
								viewHolder.getCheckBox().setChecked(planet.isChecked());

							}
						});

				// Create and populate planets.
				itemss = (mItems[]) getLastNonConfigurationInstance();
				
				for (int i = 0; i < users.size(); i++) {
					listItems.add(new mItems(users.get(i)));
				}


				// Set our custom array adapter as the ListView's adapter.
				listAdapter = new SelectArralAdapter(this, listItems);
				mainListView.setAdapter(listAdapter);
				
	}




	
	
	
	
	
	
	
	
	
	

	private AsyncTask<Recommendation, Void, Recommendation> createAsyncTaskSendRecommendation()
	{
		Log.i("tag", "creatasyntest");
		//final ProgressDialog dialog = new ProgressDialog(ActivityFriends.this);;
		AsyncTask<Recommendation, Void, Recommendation> async = new AsyncTask<Recommendation, Void, Recommendation>(){
			
			
			protected void onPreExecute() {
				super.onPreExecute();
				Log.i("tag", "preexecute");
				//dialog.setMessage("Retrieving the evaluations...");
				//dialog.show();
			}
			
			protected Recommendation doInBackground(Recommendation... recommendation) {
				Log.i("tag", "doinbackground");
				return sendRecommendation(recommendation[0]);
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(Evaluation[] result) {
				Log.i("tag", "postexecute");
//				if(dialog.isShowing())
//				{
//					dialog.dismiss();
//				}
				Toast.makeText(ActivityFriends.this, "The article was recommended successfully", Toast.LENGTH_SHORT).show();
				//finish();
				
//				Intent intent = new Intent();
//	    	   	intent.setClass(ActivityFriends.this, RecommendationList.class);
//	    	   	startActivity(intent);
			}
		};
		return async;
	} 
	
	private Recommendation sendRecommendation(Recommendation rec) 
	{
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			String url = "/recommendation/";
			ResponseEntity<Recommendation> response = restTemplate.exchange(
					Constants.URL_SERVER + url, HttpMethod.POST,
					new HttpEntity<Object>(rec, requestHeaders),
					Recommendation.class);

			Log.i("SendRecommendationClient",
					"ResponseEntity " + response.getBody());
			return new Recommendation();

		} catch (HttpClientErrorException e) {
			Log.i("SendRecommendationClient",
					"Error trying to send recommendation: " + e.getMessage());
			e.printStackTrace();
			return new Recommendation();
		}
		
		
//		Log.i("tag", "sendrecomendation");
//		return new Recommendation();
	}
	


}
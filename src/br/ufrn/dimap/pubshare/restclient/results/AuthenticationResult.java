package br.ufrn.dimap.pubshare.restclient.results;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthenticationResult {
	 	
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/*
	public JSONObject jsonObject;
	
	public AuthenticationResult(String json){
		try{
			jsonObject = new JSONObject(json);
		}catch(Exception e){
			// 
		}
	}
	
	public boolean isSuccessful(){
		try {
			return jsonObject.has("success") && jsonObject.getBoolean("success");
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
*/
}

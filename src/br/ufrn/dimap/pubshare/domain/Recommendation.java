package br.ufrn.dimap.pubshare.domain;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recommendation 
{
	@JsonProperty
	private long idUsuario = 0;
	
	@JsonProperty
	private List<Long> idDestinatarios = new ArrayList<Long>();
	
	@JsonProperty
	private String title = "";
	
	@JsonProperty
	private String conference= "";
	
	@JsonProperty
	private String authors= "";
	
	@JsonProperty
	private String link= "";
	
	@JsonProperty
	private String obs= "";
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<Long> getIdDestinatarios() {
		return idDestinatarios;
	}
	public void setIdDestinatarios(List<Long> idDestinatarios) {
		this.idDestinatarios = idDestinatarios;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getConference() {
		return conference;
	}
	public void setConference(String conference) {
		this.conference = conference;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}

	
}

package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.List;

import br.ufrn.dimap.pubshare.evaluation.domain.Evaluation;

 
/**
 *
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class Article implements Serializable {
	
	private static final long serialVersionUID = 8780135162419459741L;

	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.domain.Article?Instance";
	
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.domain.Article?List";
 
	private String title;

	private String remoteLocation;
	
	/**
	 * The evaluations made for the article
	 */
	private List<Evaluation> evaluations;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemoteLocation() {
		return remoteLocation;
	}

	public void setRemoteLocation(String remoteLocation) {
		this.remoteLocation = remoteLocation;
	}
	
	public String generateFileName(){
		return "IEEE_-_ARTICLENAME" +  System.currentTimeMillis() + ".pdf";
	}


	public List<Evaluation> getEvaluations() 
	{
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) 
	{
		this.evaluations = evaluations;
	}
	
}

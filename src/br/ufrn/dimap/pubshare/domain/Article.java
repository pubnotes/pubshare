package br.ufrn.dimap.pubshare.domain;

import java.util.List;

import br.ufrn.dimap.pubshare.evaluation.domain.Evaluation;

/**
 *
 *
 */
public class Article {
	
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.domain.Article?Instance";
	
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.domain.Article?List";
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// More fields
	/**
	 * The evaluations made for the article
	 */
	private List<Evaluation> evaluations;

	public List<Evaluation> getEvaluations() 
	{
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) 
	{
		this.evaluations = evaluations;
	}
	
}

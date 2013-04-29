package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents the evaluations made for articles
 * @author Daniel
 *
 */
public class Evaluation implements Serializable
{
	private static final long serialVersionUID = 9042871761299411144L;
	
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?List";
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?Instance";
	
	private String reviewerNotes, reviewerName;
	private float originality, contribution, relevance, readability, relatedWorks, reviewerFamiliarity;
	private Date evalDate;
	
	private int id;
	
	/** this flag indicates that the evaluation will be visible to other users**/
	private boolean published;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReviewerName() 
	{
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) 
	{
		this.reviewerName = reviewerName;
	}
	public String getReviewerNotes() 
	{
		return reviewerNotes;
	}
	public void setReviewerNotes(String reviewerNotes) 
	{
		this.reviewerNotes = reviewerNotes;
	}
	public float getOriginality() 
	{
		return originality;
	}
	public void setOriginality(float originality) 
	{
		this.originality = originality;
	}
	public float getContribution() 
	{
		return contribution;
	}
	public void setContribution(float contribution) 
	{
		this.contribution = contribution;
	}
	public float getRelevance() 
	{
		return relevance;
	}
	public void setRelevance(float relevance) 
	{
		this.relevance = relevance;
	}
	public float getReadability() 
	{
		return readability;
	}
	public void setReadability(float readability) 
	{
		this.readability = readability;
	}
	public float getRelatedWorks() 
	{
		return relatedWorks;
	}
	public void setRelatedWorks(float relatedWorks) 
	{
		this.relatedWorks = relatedWorks;
	}
	public float getReviewerFamiliarity() 
	{
		return reviewerFamiliarity;
	}
	public void setReviewerFamiliarity(float familiarity) 
	{
		this.reviewerFamiliarity = familiarity;
	}
	public Date getEvalDate() 
	{
		return evalDate;
	}
	public void setEvalDate(Date evalDate) 
	{
		this.evalDate = evalDate;
	}
	
	public boolean isPublished() 
	{
		return published;
	}
	public void setPublished(boolean published)
	{
		this.published = published;
	}
	
	public float getOverall()
	{
		return (originality+contribution+relevance+readability+relatedWorks+reviewerFamiliarity)/6.0f;
	}
}

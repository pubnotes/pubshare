package br.ufrn.dimap.pubshare.domain;

import java.util.Date;

/**
 * 
 * @author Daniel
 *
 */
public class Evaluation 
{
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?Instance";
	
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?List";
	
	private String reviewerNotes, reviewerName;
	private float originality, contribution, relevance, readability, relatedWorks, familiarity;
	private boolean awardCandidate;
	private OverallRecommendation recommendation;
	private Date evalDate;
	
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
	public float getFamiliarity() 
	{
		return familiarity;
	}
	public void setFamiliarity(float familiarity) 
	{
		this.familiarity = familiarity;
	}
	public boolean isAwardCandidate() 
	{
		return awardCandidate;
	}
	public void setAwardCandidate(boolean awardCandidate) 
	{
		this.awardCandidate = awardCandidate;
	}
	public OverallRecommendation getRecommendation() 
	{
		return recommendation;
	}
	public void setRecommendation(OverallRecommendation recommendation) 
	{
		this.recommendation = recommendation;
	}
	public Date getEvalDate() 
	{
		return evalDate;
	}
	public void setEvalDate(Date evalDate) 
	{
		this.evalDate = evalDate;
	}
	
	public float getOverall()
	{
		return (originality+contribution+relevance+readability+relatedWorks+familiarity)/6.0f;
	}
}

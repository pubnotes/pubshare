/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class represents the evaluations made for articles
 * @author Daniel
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Evaluation implements Serializable
{
	@JsonIgnore
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?List";
	@JsonIgnore
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.evaluation.domain.Evaluation?Instance";
	
	@JsonProperty
	private User user;
	
	@JsonProperty
	private Article article;
	
	@JsonProperty
	private String reviewerNotes;
	
	@JsonProperty
	private float originality, contribution, relevance, readability, relatedWorks, reviewerFamiliarity;
	
	@JsonProperty
	private Date evalDate;
	
	@JsonProperty
	private int id;
	
	/** this flag indicates that the evaluation will be visible to other users**/
	@JsonProperty
	private boolean published;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public boolean getPublished() 
	{
		return published;
	}
	public void setPublished(boolean published)
	{
		this.published = published;
	}
	
	@JsonIgnore
	public float getOverall()
	{
		return (originality+contribution+relevance+readability+relatedWorks+reviewerFamiliarity)/6.0f;
	}
}

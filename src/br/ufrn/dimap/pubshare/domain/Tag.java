package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Now this class should ecapsulate the tags
 * @author daniel
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag implements Serializable
{
	@JsonProperty
	private long id;
	
	@JsonProperty
	private String description;
	
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
}

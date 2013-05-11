package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/*
 * This class should keep the relation between friends and tags.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Friend extends User implements Serializable 
{
	@JsonProperty
	private Tag tag;

	public Tag getTag() 
	{
		return tag;
	}

	public void setTag(Tag tag) 
	{
		this.tag = tag;
	}
}

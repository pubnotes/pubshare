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
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
 
/**
 * Domain class that represents an article.
 * @author Itamir de Morais Barroca Filho <i>itamir.filho@gmail.com</i>
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article implements Serializable {
	
	@JsonIgnore
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.domain.Article?Instance";
	@JsonIgnore
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.domain.Article?List";
 
	/** daniel - added the id in order to get the evaluations from the article via rest **/
	@JsonProperty
	private long id;
	
	@JsonProperty
	private String title;

	@JsonProperty
	private String abztract;
	
	@JsonIgnore
	private List<String> authors;
	
	@JsonProperty
	private String downloadLink;
	
	@JsonProperty
	private String eventInformation;
	
	@JsonProperty
	private String remoteLocation;
	
	/**
	 * The evaluations made for the article
	 */
	@JsonIgnore
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


	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getAbztract() {
		return abztract;
	}

	public void setAbztract(String abztract) {
		this.abztract = abztract;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public String getEventInformation() {
		return eventInformation;
	}

	public void setEventInformation(String eventInformation) {
		this.eventInformation = eventInformation;
	}
	
	@Override
	public boolean equals(Object o) {
		return title.equals(((Article) o).getTitle());
	}
}

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


 
/**
 *
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class Article implements Serializable {
	
	private static final long serialVersionUID = 8780135162419459741L;

	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.domain.Article?Instance";
	
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.domain.Article?List";
 
	/** daniel - added the id in order to get the evaluations from the article via rest **/
	private long id;
	
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
	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}
	
}

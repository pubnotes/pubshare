/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Foobar is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.ufrn.dimap.pubshare.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains information from an article downloaded by the user.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 *
 */
public class ArticleDownloaded implements Serializable {
 
	private static final long serialVersionUID = 6112191778772834685L;

	private int _id;
		
	private String title;
	
	private String digitalLibrary;
	
	private Date downloadedAt;
	
	/**
	 * Absolute path of the file in the file system
	 */
	private String pathSdCard;
	
	private String mimeType;
	
	private long size;
	
	private long downloadKey;
	
	private String urlSource;
	 

	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDigitalLibrary() {
		return digitalLibrary;
	}

	public void setDigitalLibrary(String digitalLibrary) {
		this.digitalLibrary = digitalLibrary;
	}

	public Date getDownloadedAt() {
		return downloadedAt;
	}

	public void setDownloadedAt(Date downloadedAt) {
		this.downloadedAt = downloadedAt;
	}

	public String getPathSdCard() {
		return pathSdCard;
	}

	public void setPathSdCard(String pathSdCard) {
		this.pathSdCard = pathSdCard;
	}
 
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getDownloadKey() {
		return downloadKey;
	}

	public void setDownloadKey(long downloadKey) {
		this.downloadKey = downloadKey;
	}

	public String getUrlSource() {
		return urlSource;
	}

	public void setUrlSource(String urlSource) {
		this.urlSource = urlSource;
	}

	@Override
	public String toString() {
		return "ArticleDownloaded [title=" + title + ", digitalLibrary="
				+ digitalLibrary + ", downloadedAt=" + downloadedAt
				+ ", pathSdCard=" + pathSdCard + ", mimeType=" + mimeType
				+ ", size=" + size + ", downloadKey=" + downloadKey
				+ ", urlSource=" + urlSource + "]";
	}
}

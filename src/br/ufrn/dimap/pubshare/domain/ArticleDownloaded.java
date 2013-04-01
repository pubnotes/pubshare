package br.ufrn.dimap.pubshare.domain;

import java.util.Date;

/**
 * This class contains information from an article downloaded by the user.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 *
 */
public class ArticleDownloaded {
	
	/**
	 * Article downloaded. 
	 */
	private Article article;
	
	private Date downloadedAt;
	
	/**
	 * Absolute path of the file in the file system
	 */
	private String pathSdCard;
	
	/**
	 * Indicates if the user removed the file on the SD card.
	 */
	private boolean fileExistsInPathSdCard;
	
	

	public ArticleDownloaded(Article article, Date downloadedAt,
			String pathSdCard, boolean fileExistsInPathSdCard) {
		super();
		this.article = article;
		this.downloadedAt = downloadedAt;
		this.pathSdCard = pathSdCard;
		this.fileExistsInPathSdCard = fileExistsInPathSdCard;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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

	public boolean isFileExistsInPathSdCard() {
		return fileExistsInPathSdCard;
	}

	public void setFileExistsInPathSdCard(boolean fileExistsInPathSdCard) {
		this.fileExistsInPathSdCard = fileExistsInPathSdCard;
	}
	
}

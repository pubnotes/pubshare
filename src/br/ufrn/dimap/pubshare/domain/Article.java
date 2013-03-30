package br.ufrn.dimap.pubshare.domain;

/**
 *
 * @author luksrn
 */
public class Article {
	
	public static final String KEY_LIST = "br.ufrn.dimap.pubshare.domain.Article?Instance";
	
	public static final String KEY_INSTANCE = "br.ufrn.dimap.pubshare.domain.Article?List";
	
	public static final String KEY_REMOTE_URL = "br.ufrn.dimap.pubshare.domain.Article?remoteUrl";
	
	
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// More fields
	
}

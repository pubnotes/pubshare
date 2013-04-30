package br.ufrn.dimap.pubshare.parsers;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Abstract class to represent a parser. 
 * @author itamir
 *
 */
public abstract class Parser {

	/**
	 * Method to find articles by title.
	 * @return
	 */
	public abstract List<Article> findArticlesByTitle(String title);
	
	
	/**
	 * Method to find articles by author name.
	 * @param nomeAutor
	 * @return
	 */
	public abstract List<Article> findArticleByAuthor(String author);
	
	
	/**
	 * Method to extract information of an URL.
	 * @param artigos
	 * @param url
	 */
	protected abstract void parseUrl(List<Article> articles, URL url) throws IOException;
	
	
	/**
	 * Method to extract special text to processed lines.
	 * @param tituloArtigo
	 * @return
	 */
	protected abstract String replaceSpecialText(String text);
	
	
	/**
	 * Method to extract URL download.
	 * @param tituloArtigo
	 * @return
	 */
	protected abstract String urlDownloadExtract(String text);
	
}

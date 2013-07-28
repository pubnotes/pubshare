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

package br.ufrn.dimap.pubshare.parsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Abstract class to represent a parser. 
 * @author itamir
 *
 */
public abstract class Parser {

	/**
	 * Link to author search.
	 */
	protected String linkAuthorSearch;
	
	/**
	 * Link to title search.
	 */
	protected String linkTitleSearch;
	
	/**
	 * Method to find articles by title.
	 * @return
	 */
	public  List<Article> findArticlesByTitle(String title) {
		List<Article> articles = new ArrayList<Article>();
		try {
			URL url = new URL(linkTitleSearch + title);
			parseUrl(articles, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	
	/**
	 * Method to find articles by author name.
	 * @param nomeAutor
	 * @return
	 */
	public List<Article> findArticleByAuthor(String author) {
		List<Article> artigos = new ArrayList<Article>();
		try {
			URL url = new URL(linkAuthorSearch + author);
			parseUrl(artigos, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artigos;
	}
	
	
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

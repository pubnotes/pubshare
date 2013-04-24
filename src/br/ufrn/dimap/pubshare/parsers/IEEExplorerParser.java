package br.ufrn.dimap.pubshare.parsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Parser to process IEEExplorer results.
 * @author itamir
 *
 */
public class IEEExplorerParser extends Parser {

	public List<Article> findArticlesByTitle(String title) {

		List<Article> articles = new ArrayList<Article>();
		try {
			URL url = new URL("http://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&rowsPerPage=100&queryText=" + title);
			parseUrl(articles, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	
	public List<Article> findArticleByAuthor(String author) { 
		List<Article> artigos = new ArrayList<Article>();
		try {
			URL url = new URL("http://ieeexplore.ieee.org/search/searchresult.jsp?matchBoolean=true&newsearch=true&searchWithin=p_Last_Names%3A" + author);
			parseUrl(artigos, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artigos;
	}

	protected void parseUrl(List<Article> articles, URL url) throws IOException {
		Document doc = Jsoup.parse(url, Integer.MAX_VALUE);
		
		Elements links = doc.getElementsByClass("noAbstract");
		for (Element link : links) {

			Article article = new Article();
			String title = link.getElementsByTag("input").attr("title");
			title = replaceSpecialText(title);
			article.setTitle(title);
			
			Elements nomesAutores = link.getElementsByTag("a");
			for (Element element : nomesAutores) {
				if (element.getElementById("preferredName") != null) {
					String author = element.getElementById("preferredName").attr("class");
					article.getAuthors().add(author);
				}
			}
			
			articles.add(article);
		}
	}
	
	protected String replaceSpecialText(String text) {
		text = text.replace("<span class=\'snippet\'>", "");
		text = text.replace("</span>", "");
		text = text.replace("Select this article: ", "");
		return text;
	}


	@Override
	protected String urlDownloadExtract(String text) {
		return null;
	}
	
}

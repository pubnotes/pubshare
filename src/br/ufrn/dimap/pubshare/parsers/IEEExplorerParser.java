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

	/**
	 * Constructor.
	 */
	public IEEExplorerParser() {
		linkTitleSearch = "http://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&rowsPerPage=20&queryText=";
		linkAuthorSearch ="http://ieeexplore.ieee.org/search/searchresult.jsp?matchBoolean=true&newsearch=true&searchWithin=p_Last_Names%3A";
	}

	protected void parseUrl(List<Article> articles, URL url) throws IOException {
		Document doc = Jsoup.parse(url, Integer.MAX_VALUE);
		
		Elements links = doc.getElementsByClass("noAbstract");
		for (Element link : links) {

			Article article = new Article();
			article.setAuthors(new ArrayList<String>());
			
			//title
			String title = link.getElementsByTag("input").attr("title");
			title = replaceSpecialText(title);
			article.setTitle(title);
			
			//authors
			Elements nomesAutores = link.getElementsByTag("a");
			for (Element element : nomesAutores) {
				if (element.getElementById("preferredName") != null) {
					String author = element.getElementById("preferredName").attr("class");
					article.getAuthors().add(author);
				}
			}
			
			//abstract
			Elements abstracts =  link.getElementsByClass("RevealContent");
			for (Element element : abstracts) {
			  article.setAbztract(element.text());
			}

			
			Elements conferenciaAnoLinks = link.getElementsByTag("a");
			for (Element element : conferenciaAnoLinks) {
				String linkHref = element.attr("href");
				// conference
				if (linkHref.contains("mostRecentIssue")) {
					article.setEventInformation(element.text());
				}
				//download link
				if (linkHref.contains("stamp")) {
					article.setDownloadLink("http://ieeexplore.ieee.org"+element.attr("href"));
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

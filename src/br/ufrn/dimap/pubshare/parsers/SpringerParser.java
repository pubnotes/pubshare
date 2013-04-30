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
 * Parser to process Springer results.
 * @author itamir
 *
 */
public class SpringerParser extends Parser{

	@Override
	public List<Article> findArticlesByTitle(String title) {
		List<Article> articles = new ArrayList<Article>();
		try {
			URL url = new URL("http://link.springer.com/search?query="+ title +"&facet-content-type=%22Article%22");
			parseUrl(articles, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<Article> findArticleByAuthor(String author) {
		List<Article> articles = new ArrayList<Article>();
		try {
			URL url = new URL("?");
			parseUrl(articles, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	protected void parseUrl(List<Article> articles, URL url) throws IOException {
		Document doc = Jsoup.parse(url, Integer.MAX_VALUE);

		Elements divMain = doc.getElementsByClass("col-main");
		Elements links = divMain.get(0).getElementsByTag("li");

		for (Element link : links) {

			Article article = new Article();
				
			//Titulo
			Elements e = link.getElementsByClass("title");
			String tituloArtigo = e.get(0).text();
			tituloArtigo = replaceSpecialText(tituloArtigo);
			article.setTitle(tituloArtigo);

			//abstract 
			Elements abstracts = link.getElementsByClass("snippet");
			for (Element element : abstracts) {
				article.setAbztract(element.text());
			}
			
			//Autores
			Elements nomesAutores = link.getElementsByClass("authors");
			for (Element element : nomesAutores) {
				Elements autores = element.getElementsByTag("a");
				for (Element element2 : autores) {
					article.getAuthors().add(element2.text());
				}
			}
			
			//conferencia e ano
			Elements conferenciaAno = link.getElementsByClass("enumeration");
			for (Element element : conferenciaAno) {
				Elements conferencias = element.getElementsByTag("a");
				String eventInfo = "";
				for (Element element3 : conferencias) {
					eventInfo += element3.text();
				}
				
				Elements anos = element.getElementsByClass("year");
				for (Element element4 : anos) {
					eventInfo += element4.text();
				}
				
				article.setEventInformation(eventInfo);
			}
			
			//link para download
			Elements linksDownload = link.getElementsByClass("pdf-link");
			for (Element element : linksDownload) {
				article.setDownloadLink(element.attr("href"));
			}
			
			articles.add(article);
		}
	}

	@Override
	protected String replaceSpecialText(String text) {
		return text.replace("Article", "");
	}

	@Override
	protected String urlDownloadExtract(String text) {
		return null;
	}
}

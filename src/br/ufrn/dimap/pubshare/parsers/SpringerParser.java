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

	/**
	 * Constructor.
	 */
	public SpringerParser() {
		linkTitleSearch = "http://link.springer.com/search?facet-content-type=%22Article%22&query=";
		linkAuthorSearch = "http://link.springer.com/search?facet-content-type=%22Article%22&dc.creator=";
	}
	
	@Override
	protected void parseUrl(List<Article> articles, URL url) throws IOException {
		Document doc = Jsoup.parse(url, Integer.MAX_VALUE);

		Elements divMain = doc.getElementsByClass("col-main");
		Elements links = divMain.get(0).getElementsByTag("li");

		for (Element link : links) {

			Article article = new Article();
			article.setAuthors(new ArrayList<String>());
			
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

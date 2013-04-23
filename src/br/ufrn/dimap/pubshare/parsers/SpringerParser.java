package br.ufrn.dimap.pubshare.parsers;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Classe que implementa o parser da fonte Springer.
 * @author itamir
 *
 */
public class SpringerParser extends Parser{

	@Override
	public List<Article> findArticlesByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findArticleByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void parseUrl(List<Article> articles, URL url) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String retirarTextoEspecial(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String urlDownloadExtract(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;

public class ArticlesDownloadedMockFactory {

	public static List<ArticleDownloaded> makeArticleDownloadedList(){
		
		List<Article> articles = ArticleMockFactory.makeArticleList( ArticleMockFactory.articlesTittles.length / 2 );
		
		List<ArticleDownloaded> downloads = new ArrayList<ArticleDownloaded>();
		
		for( Article article : articles ){
			ArticleDownloaded articleDownloaded = new ArticleDownloaded(article, 
					new Date() , 
					"/sdcard/pubshare/downloads/file.pdf", true );
			
			downloads.add( articleDownloaded );
		}
		
		return downloads;
	}
}

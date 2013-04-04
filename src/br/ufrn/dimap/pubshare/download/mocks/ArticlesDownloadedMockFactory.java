package br.ufrn.dimap.pubshare.download.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.mocks.ArticleMockFactory;

/**
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 *
 */
public class ArticlesDownloadedMockFactory {

	public static List<ArticleDownloaded> makeArticleDownloadedList(){
		
		List<Article> articles = ArticleMockFactory.makeArticleList( ArticleMockFactory.articlesTittles.length / 2 );
		
		List<ArticleDownloaded> downloads = new ArrayList<ArticleDownloaded>();
		
		for( Article article : articles ){
			ArticleDownloaded articleDownloaded = new ArticleDownloaded();
			articleDownloaded.setTitle( article.getTitle() );
			articleDownloaded.setDownloadedAt( new Date() );
			articleDownloaded.setPathSdCard( "/sdcard/pubshare/downloads/file.pdf");
			articleDownloaded.setDigitalLibrary("IEEE");			
			
			downloads.add( articleDownloaded );
		}
		
		return downloads;
	}
}

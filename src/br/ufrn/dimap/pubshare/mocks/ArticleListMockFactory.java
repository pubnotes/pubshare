package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;

public class ArticleListMockFactory {
	
	public static String [] articlesTittles = {
		"Electromagnetic pulse protection requirments and test methods for systems",
		"Motor Starting Requirments and Their Effect on Motor Design",
		"Model Accuracy Requirments For Economic Optimizing Model Predictive Controllers - The Linear Programming Case",
		"Role of mobile augmentation in mobile application development",
		"Mobile entertainment: model development and cross services study",
		"The mapping of interconnected SOA governance and ITIL v3.0",
		"Developing a more comprehensive and expressive SOA governance framework",
		"Research on Chinese Museum Design Based on Virtual Reality",
		"Simulation modeling with artificial reality technology (SMART): an integration of virtual reality and simulation modeling",
		"ARGAMAN: Rapid Deployment Virtual Reality System for PTSD Rehabilitation",
		"Research on the Virtual Reality Simulation Engine",
		"A novel approach of automation testing on mobile devices"
	};
	
	public static List<Article> makeArticleList(){
		List<Article> articles = new ArrayList<Article>();
		
		for( int i = 0; i < articlesTittles.length ; i++ ){
			Article article = new Article();
			article.setTitle( articlesTittles[i] );
			
			articles.add(article);
		}
		
		return articles;
	}

}

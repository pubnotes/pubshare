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
package br.ufrn.dimap.pubshare.mocks;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Article;

/**
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 *
 */
public class ArticleMockFactory {
	
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
	
	
	public static List<Article> makeArticleList( int amount ){
		
		if ( amount > articlesTittles.length ){
			throw new IllegalArgumentException();
		}
		
		List<Article> articles = new ArrayList<Article>();
		
		for( int i = 0; i < amount ; i++ ){
			Article article = new Article();
			article.setId(i+1);
			article.setTitle( articlesTittles[i] );
			
			articles.add(article);
		}
		
		return articles;
	}	
	
	
	public static List<Article> makeArticleList(){
		return makeArticleList( articlesTittles.length  );		 
	}	
	
	public static Article singleArticle(){
		Article article = new Article();
		article.setId(1);
		article.setTitle("GTMV: Virtual Museum Authoring Systems");
		article.setRemoteLocation("http://www.natalnet.br/~luksrn/05068879.pdf");
		return article;
	}
}

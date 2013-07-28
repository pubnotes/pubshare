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

package br.ufrn.dimap.pubshare.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;

public class EvaluationListMockFactory 
{
	private static String[] reviewerNotes = new String[] 
			{
			"excelent bla bla bla...",
			"good bad bla bla bla...",
			"not bad bla bla bla...",
			"poor bla bla bla...",
			"really poor bla bla bla..."
			};
	
	private static float[] originality = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	private static float[] contribution = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	private static float[] relevance = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	private static float[] readability = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	private static float[] relatedWorks = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	private static float[] familiarity = new float[] 
			{
			5.0f,
			4.5f,
			3.0f,
			2.0f,
			1.0f
			};
	public static List<Evaluation> makeEvaluationList()
	{
		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		
		for(int i = 0; i < 5; i++)
		{
			Evaluation eval = new Evaluation();
			eval.setUser(UserMockFactory.makeUserList().get(i));
			eval.setReviewerNotes(reviewerNotes[i]);
			eval.setOriginality(originality[i]);
			eval.setContribution(contribution[i]);
			eval.setRelevance(relevance[i]);
			eval.setReadability(readability[i]);
			eval.setRelatedWorks(relatedWorks[i]);
			eval.setReviewerFamiliarity(familiarity[i]);
			eval.setEvalDate(new Date());
			evaluations.add(eval);
		}
		return evaluations;
	}
	
	public static Evaluation singleEvaluation()
	{
		Evaluation eval = new Evaluation();
		eval.setUser(UserMockFactory.makeUserList().get(0));
		eval.setReviewerNotes("excelent bla bla bla...");
		eval.setOriginality(4.0f);
		eval.setContribution(4.0f);
		eval.setRelevance(3.5f);
		eval.setReadability(5.0f);
		eval.setReviewerFamiliarity(4.5f);
		eval.setEvalDate(new Date());
		return eval;
	}
}

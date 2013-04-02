package br.ufrn.dimap.pubshare.evaluation.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.OverallRecommendation;

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
	private static String[] reviewerNames = new String[] 
			{
			"Alice",
			"Bob",
			"Clair",
			"David",
			"Tom"
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
	private static boolean[] awardCandidate = new boolean[] 
			{
			true,
			true,
			false,
			false,
			false
			};
	private static OverallRecommendation[] overall = new OverallRecommendation[] 
			{
			OverallRecommendation.DEFINITELY_ACCEPT,
			OverallRecommendation.PROBABLY_ACCEPT,
			OverallRecommendation.PROBABLY_ACCEPT,
			OverallRecommendation.PROBABLY_REJECT,
			OverallRecommendation.DEFINITELY_REJECT
			};	
	
	public static List<Evaluation> makeEvaluationList()
	{
		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		
		for(int i = 0; i < 5; i++)
		{
			Evaluation eval = new Evaluation();
			eval.setReviewerName(reviewerNames[i]);
			eval.setReviewerNotes(reviewerNotes[i]);
			eval.setOriginality(originality[i]);
			eval.setContribution(contribution[i]);
			eval.setRelevance(relevance[i]);
			eval.setReadability(readability[i]);
			eval.setRelatedWorks(relatedWorks[i]);
			eval.setFamiliarity(familiarity[i]);
			eval.setAwardCandidate(awardCandidate[i]);
			eval.setRecommendation(overall[i]);
			eval.setEvalDate(new Date());
			evaluations.add(eval);
		}
		return evaluations;
	}
}

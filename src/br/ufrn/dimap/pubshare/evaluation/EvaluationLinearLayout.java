package br.ufrn.dimap.pubshare.evaluation;

import br.ufrn.dimap.pubshare.domain.Evaluation;
import android.content.Context;
import android.widget.LinearLayout;

public class EvaluationLinearLayout extends LinearLayout
{
	private Evaluation evaluation;
	
	public EvaluationLinearLayout(Context context)
	{
		super(context);
	}
	
	public Evaluation getEvaluation()
	{
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation)
	{
		this.evaluation = evaluation;
	}
}

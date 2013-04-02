package br.ufrn.dimap.pubshare.evaluation.adapters;

import java.text.SimpleDateFormat;
import java.util.List;
import br.ufrn.dimap.pubshare.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.domain.Evaluation;

public class EvaluationListAdapter extends ArrayAdapter<Evaluation> 
{
	public EvaluationListAdapter(Context context, int textViewResourceId, List<Evaluation> objects)
	{
		super(context,textViewResourceId,objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if(view == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_listview_article_evaluation_list, null);
		}
		
		Evaluation item = getItem(position);
		if(item != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			TextView titleText = (TextView) view.findViewById(R.id.evaluationDate);
			titleText.setText(sdf.format(item.getEvalDate()));
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.overAll);
			ratingBar.setRating(item.getOverall());
		}
		return view;
	}
}

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
import br.ufrn.dimap.pubshare.util.DateFormat;

/**
 * Class for populating the ListView from ArticleDetailActivity
 * @author Daniel
 *
 */
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
			TextView titleText = (TextView) view.findViewById(R.id.label_row_evaluation_date);
			titleText.setText(DateFormat.iso8601Format(item.getEvalDate()));
			
			/** if notes are too long, we must truncate it */
			TextView notesText = (TextView) view.findViewById(R.id.label_row_evaluation_notes);
			if(item.getReviewerNotes().length() > 250)
			{
				notesText.setText(item.getReviewerNotes().substring(0,250).concat("..."));
			}
			else
			{
				notesText.setText(item.getReviewerNotes());
			}
			
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_row_evaluation_overall);
			ratingBar.setRating(item.getOverall());
		}
		return view;
	}
}

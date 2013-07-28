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
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.util.DateFormat;

/**
 * Class for populating the ListView from ArticleDetailActivity
 * @author Daniel
 *
 */
public class EvaluationListAdapter extends ArrayAdapter<Evaluation> 
{
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	
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
			
			evaluations.add(item);
		}
		return view;
	}
	
	public List<Evaluation> getEvaluations() 
	{
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) 
	{
		this.evaluations = evaluations;
	}
}

package br.ufrn.dimap.pubshare.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.ArticleListActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;

/**
 * ListAdapter that manages a ListView backed by an array of Articles found in previous consult.
 * 
 * @author luksrn@gmail.com
 * @see ArticleListActivity
 */
public class ArticleListAdapter extends ArrayAdapter<Article>{

	public ArticleListAdapter(Context context, int textViewResourceId,List<Article> objects) {
		super(context, textViewResourceId, objects);	 
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_listview_article_list, null);
		}

		Article item = getItem(position);
		if (item!= null) {
			TextView titleText = (TextView) view.findViewById(R.id.titleText);
			titleText.setText( item.getTitle() );
		}

		return view;
	}
}

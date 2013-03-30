package br.ufrn.dimap.pubshare.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.util.DateFormat;

public class ArticlesDownloadedListAdapter extends ArrayAdapter<ArticleDownloaded> {

	public ArticlesDownloadedListAdapter(Context context,int textViewResourceId, List<ArticleDownloaded> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_listview_article_downloaded_list, null);
		}

		ArticleDownloaded item = getItem(position);
		if (item!= null) {
			TextView titleText = (TextView) view.findViewById(R.id.titleText);
			titleText.setText( item.getArticle().getTitle() );
			
			TextView dateDownload = (TextView) view.findViewById(R.id.dateTime);
			dateDownload.setText( DateFormat.format( item.getDownloadedAt() ) );
		}

		return view;
	}

}

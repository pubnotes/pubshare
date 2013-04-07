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

package br.ufrn.dimap.pubshare.download.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.download.activity.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.util.DateFormat;

/**
 * ListAdapter that manages a ListView backed by an array of Articles downloaded by the user.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 * 
 * @see ArticlesDownloadedActivity
 */
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
			titleText.setText( item.getTitle() );
			
			TextView dateDownload = (TextView) view.findViewById(R.id.dateTime);
			dateDownload.setText( DateFormat.iso8601Format( item.getDownloadedAt() ) );
		}

		return view;
	}

}

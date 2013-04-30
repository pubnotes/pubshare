package br.ufrn.dimap.pubshare.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.User;

public class UserListAdapter extends ArrayAdapter<User> {

	public UserListAdapter(Context context, int textViewResourceId,
			List<User> objects) {
		super(context, textViewResourceId, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_listview_people_list, null);
		}

		User item = getItem(position);
		if (item!= null) {
			TextView usernameText = (TextView) view.findViewById(R.id.usernametext);
			usernameText.setText( item.getUsername() );
			
			TextView aboutmeText = (TextView) view.findViewById(R.id.aboutmetext);
			aboutmeText.setText( item.getUserprofile().getAboutme() );
			
			//Falta tratar imagem do usuario
		}

		return view;
	}
	
	

}

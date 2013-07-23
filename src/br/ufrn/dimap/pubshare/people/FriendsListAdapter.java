package br.ufrn.dimap.pubshare.people;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.User;

public class FriendsListAdapter extends ArrayAdapter<User> {
	
	public FriendsListAdapter(Context context, int textViewResourceId,
			List<User> objects) {
		super(context, textViewResourceId, objects);
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_friends_list, null);
		}

		User item = getItem(position);
		if (item!= null) {
			TextView friendnameText = (TextView) view.findViewById(R.id.friendnametext);
			friendnameText.setText( item.getUsername() );
			
			TextView friendaboutmeText = (TextView) view.findViewById(R.id.friendaboutme);
			friendaboutmeText.setText( item.getUserprofile().getAboutme() );
			
			//Falta tratar imagem do usuario
		}

		return view;
	}
	
}

package br.ufrn.dimap.pubshare.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.activity.RegisterActivity;
import br.ufrn.dimap.pubshare.activity.SearchPeopleActivity;
import br.ufrn.dimap.pubshare.activity.ShowProfileActivity;
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
		
			final TextView usernameText = (TextView) view.findViewById(R.id.usernametext);
			usernameText.setText( item.getUsername() );
			
			TextView aboutmeText = (TextView) view.findViewById(R.id.aboutmetext);
			aboutmeText.setText( item.getUserprofile().getAboutme() );
			
			ImageView add = (ImageView) view.findViewById(R.id.btnadd);
			
			add.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							Toast.makeText(UserListAdapter.this.getContext(),
									"Você adicionou " + usernameText.getText() + " ao PubShare", Toast.LENGTH_SHORT).show();
						}
					});
			
			//Falta tratar imagem do usuario
		}

		return view;
	}
	
	

}

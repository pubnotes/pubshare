	package br.ufrn.dimap.pubshare.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.activity.SearchPeopleActivity;
import br.ufrn.dimap.pubshare.activity.ShowFriendsActivity;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;

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
			User user = UserMockFactory.makeSingleUser();
			//final List<br.ufrn.dimap.pubshare.domain.Tag> tags = user.getTags();
		
			TextView friendnameText = (TextView) view.findViewById(R.id.friendnametext);
			friendnameText.setText( item.getUsername() );
			
			TextView friendaboutmeText = (TextView) view.findViewById(R.id.friendaboutme);
			friendaboutmeText.setText( item.getUserprofile().getAboutme() );
			
			//Falta tratar imagem do usuario
		}

		return view;
	}
	
}

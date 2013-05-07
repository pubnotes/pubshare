package br.ufrn.dimap.pubshare.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
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
			/*Spinner spinner2;
			spinner2 = (Spinner) view.findViewById(R.id.spinner2);
			
			//User u = UserMockFactory.makeSingleUser();
			List<String> tags = item.getTags();
			tags.add("Create New Tag");			
			ArrayAdapter<String> adapter_tag = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, tags);
			adapter_tag
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter_tag);*/
			List<String> tags = item.getTags();
			final PopupMenu popupMenu;
			popupMenu = new PopupMenu(this.getContext(), view.findViewById(R.id.addtag));
			popupMenu.getMenu().add(Menu.NONE, tags.size()+ 1, Menu.NONE, "Create New Tag");
			
			for (int i = 0; i < tags.size(); i++) {
				popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, tags.get(i));
			}
		
			view.findViewById(R.id.addtag).setOnClickListener(new View.OnClickListener() {
	           @Override
	           public void onClick(View v) {
	               popupMenu.show();
	           }
	       });
			
			
	       popupMenu.setOnMenuItemClickListener(
	               new PopupMenu.OnMenuItemClickListener() {
	           @Override
	       		public boolean onMenuItemClick(MenuItem item) {
	    	       switch (item.getItemId()) {
	    	       case 0:
	    	    	   Toast.makeText(FriendsListAdapter.this.getContext(),
	    						"Click!", Toast.LENGTH_SHORT).show();
	    	    	   	  break;
	    	       case 1:
	    	    	   Toast.makeText(FriendsListAdapter.this.getContext(),
	    						"Click!", Toast.LENGTH_SHORT).show();
	    	    	   break;
	    	       case 2:
	    	    	   Toast.makeText(FriendsListAdapter.this.getContext(),
	    						"Click!", Toast.LENGTH_SHORT).show();
	    	           break;
	    	       }
	    	       return false;
	           }
	       });
		
			
			
			TextView friendnameText = (TextView) view.findViewById(R.id.friendnametext);
			friendnameText.setText( item.getUsername() );
			
			TextView friendaboutmeText = (TextView) view.findViewById(R.id.friendaboutme);
			friendaboutmeText.setText( item.getUserprofile().getAboutme() );
			
			//Falta tratar imagem do usuario
		}

		return view;
	}
	
}

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
import br.ufrn.dimap.pubshare.activity.ShowFriendsActivity;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;

public class FriendsListAdapter extends ArrayAdapter<User> {
	
	List<String> tags;
	
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
			tags = new ArrayList<String>();
			tags = UserMockFactory.usersTags;
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
	    	       case 6:
		    	    	    LayoutInflater li = LayoutInflater.from(FriendsListAdapter.this.getContext());
		   				    View promptsView = li.inflate(R.layout.tags, null);
		    	    	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		   					FriendsListAdapter.this.getContext());
	    
			   				// set prompts.xml to alertdialog builder
			   				alertDialogBuilder.setView(promptsView);
			    
			   				final EditText userInput = (EditText) promptsView
			   						.findViewById(R.id.editTextDialogUserInput);
			    
			   				// set dialog message
			   				alertDialogBuilder
			   					.setCancelable(false)
			   					.setPositiveButton("OK",
			   					  new DialogInterface.OnClickListener() {
			   					    public void onClick(DialogInterface dialog,int id) {
			   						// get user input and set it to result
			   						// edit text
			   					    	//br.ufrn.dimap.pubshare.domain.Tag tag = new br.ufrn.dimap.pubshare.domain.Tag();
			   					    	//tag.setDescription();
			   					    	//adicionando nova tag a lista de tags do user
			   					    	tags.add(userInput.getText().toString());
			   					    	int newid = tags.size() + 1;
			   					    	popupMenu.getMenu().add(Menu.NONE, newid++, Menu.NONE, userInput.getText().toString());
			   					    	FriendsListAdapter.this.notifyDataSetChanged();
			   					    }
			   					  })
			   					.setNegativeButton("Cancel",
			   					  new DialogInterface.OnClickListener() {
			   					    public void onClick(DialogInterface dialog,int id) {
			   						dialog.cancel();
			   					    }
			   					  });
			    
			   				// create alert dialog
			   				AlertDialog alertDialog = alertDialogBuilder.create();
			    
			   				// show it
			   				alertDialog.show();
			    
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

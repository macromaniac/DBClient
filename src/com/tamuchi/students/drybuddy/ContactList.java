package com.tamuchi.students.drybuddy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tamuchi.students.drybuddy.R;
import com.tamuchi.students.drybuddy.Data.Contact;
import com.tamuchi.students.drybuddy.Data.ContactData;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactList extends Activity {
	private ContactData data;
	public final static String CONTACTFILENAME = "contacts.txt";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		ListView lv=(ListView)findViewById(R.id.contact_info);
		lv.setOnItemClickListener(new clickListener());
		data = new ContactData();
		Log.d("DEBUG", "Starting Contact List");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}
	
	@Override
	protected void onResume()
    {
    	super.onResume(); 
    	updateContacts();
    }
	public void updateContacts()
	{
		BufferedReader file;
		String check;
		try {
			file = new BufferedReader(new InputStreamReader(openFileInput(CONTACTFILENAME)));
			check = file.readLine();
		//Grab the checksum from the top of the file
		if (data.getChecksum().equals(check))	//File already up to date, so do nothing
			return;
		data = new ContactData(CONTACTFILENAME, this);
		}
		catch (FileNotFoundException e1) {
			Log.d("ERROR", "File "+CONTACTFILENAME+" does not exist");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to load contact list!", Toast.LENGTH_LONG);
			t.show();
			return;
		}
		catch (NumberFormatException e1) {
			Log.d("ERROR", "Checksum not correctly formatted");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to load contact list!", Toast.LENGTH_LONG);
			t.show();
			return;
		} catch (IOException e1) {
			Log.d("ERROR", "Failed to read in checksum");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to load contact list!", Toast.LENGTH_LONG);
			t.show();
			return;
		}
		Log.d("DEBUG", "Updating Contact List");
		ListView lv=(ListView)findViewById(R.id.contact_info);
		ArrayList<String> tmptimes = new ArrayList<String>();
		for (Contact c: data.getContacts())
		{
			String sobertime = "";
			if (c.soberYrs() > 0)
				sobertime = Integer.toString(c.soberYrs())+" Years";
			if (c.soberMon() > 0)
				sobertime += " "+Integer.toString(c.soberMon())+" Months";
			tmptimes.add(sobertime);
		}
		ArrayAdapter<String> tmp=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmptimes);
		lv.setAdapter(tmp);
	}
    private class clickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			Contact c = data.getContacts().get((int) id);	//Get contact clicked on
			Log.d("CALL", "Attempting to call "+c.getNumber());
			/*Build Intent*/
			Intent intent=new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:"+c.getNumber()));
			//Start the activity
			startActivity(intent);
		}
	}
	
	public void addcontact_callback(View v)
	{
		Intent intent = new Intent(this, AddContact.class);
    	startActivity(intent);
	}
	public void refresh_callback(View v)
	{
		updateContacts();
	}
}

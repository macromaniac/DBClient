package com.tamuchi.students.drybuddy;

import java.io.IOException;

import com.tamuchi.students.drybuddy.R;
import com.tamuchi.students.drybuddy.Data.Contact;
import com.tamuchi.students.drybuddy.Data.ContactData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
	    switch(item.getItemId())
	    {
	    case R.id.ContactList:
	    	i = new Intent(this, ContactList.class);
	    	startActivity(i);
	        break;
	    case R.id.AddContact:
	    	i = new Intent(this, AddContact.class);
	    	startActivity(i);
	        break;
	    case R.id.Main:
	    	i = new Intent(this, MainActivity.class);
	    	startActivity(i);
	        break;
	    }
	    return true;
	}
	
	public void cancelbutt_callback(View v)
	{
		finish();
	}
	public void addbutt_callback(View v)
	{
		EditText number = (EditText)findViewById(R.id.newnumber);
		EditText date = (EditText)findViewById(R.id.newsobdate);
		String phone = number.getText().toString();
		if (phone.length() != 10)	//Make sure the number is valid
		{
			Toast t = Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT);
			t.show();
			number.setText("");
			return;
		}
		String time = date.getText().toString();
		Toast t = Toast.makeText(getApplicationContext(), "Please enter a valid date", Toast.LENGTH_SHORT);
		if (time.length() > 10 || time.length() < 8)
		{
			t.show();
			date.setText("");
			return;
		}
		int mon = Integer.parseInt(time.substring(0,time.indexOf("/")));
		if (mon > 12 || mon < 1)
		{
			t.show();
			date.setText("");
			return;
		}
		time = time.substring(time.indexOf("/")+1);
		int day = Integer.parseInt(time.substring(0,time.indexOf("/")));
		if (day > 31 || day < 1)
		{
			t.show();
			date.setText("");
			return;
		}
		time = time.substring(time.indexOf("/")+1);
		int yrs = Integer.parseInt(time);
		Contact c = new Contact(yrs, mon, day, phone);
		//Get latest file//
		Intent updateIntent = new Intent(this, UpdateService.class);
		startService(updateIntent);
		//Use data from new list and add to it
		ContactData data;
		try {
			data = new ContactData(ContactList.CONTACTFILENAME, this);
		} catch (NumberFormatException e) {
			Log.d("ERROR", "A number in Contact Data was not correctly formatted");
			return;
		} catch (IOException e) {
			Log.d("ERROR", "The file "+ContactList.CONTACTFILENAME+" does not exist.");
			return;
		}
		data.addContact(c);
		try {
			data.writeData(ContactList.CONTACTFILENAME, this);
		} catch (IOException e) {
			Toast to = Toast.makeText(getApplicationContext(), "Exception when writing file! Contact Not added!", Toast.LENGTH_LONG);
			to.show();
			Log.d("ERROR", "Got an IO Exception when writing out contact data");
		}
		Intent intent = new Intent(this, ContactList.class);
    	startActivity(intent);
	}

}

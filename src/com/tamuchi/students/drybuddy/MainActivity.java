package com.tamuchi.students.drybuddy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.tamuchi.students.drybuddy.Data.Contact;
import com.tamuchi.students.drybuddy.Data.ContactData;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void numlist_callback(View v)
	{
		Intent intent = new Intent(this, ContactList.class);
    	startActivity(intent);
	}
	
	public void geoloc_callback(View v)
	{
		//TODO: Start geolocation activity
		Toast t = Toast.makeText(getApplicationContext(), "Geolocation clicked, HUZZAH", Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void panic_callback(View v)
	{
		ContactData dat = PANIC();
		if (dat == null)
			return;
		ArrayList<Contact> contacts = dat.getContacts();
		Random rand = new Random();
		String num = contacts.get(rand.nextInt(contacts.size())).getNumber();
		Log.d("PANIC", "Trying to call "+num);
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+num));
	}
	
	public ContactData PANIC()
	{
		ContactData data = null;
		try{
			data = new ContactData(ContactList.CONTACTFILENAME, this);
		}
		catch (FileNotFoundException e1) {
			Log.d("ERROR", "File "+ContactList.CONTACTFILENAME+" does not exist");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to call contacts!", Toast.LENGTH_LONG);
			t.show();
			return null;
		}
		catch (NumberFormatException e1) {
			Log.d("ERROR", "Checksum not correctly formatted");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to call contacts!", Toast.LENGTH_LONG);
			t.show();
			return null;
		} catch (IOException e1) {
			Log.d("ERROR", "Failed to read in checksum");
			Toast t = Toast.makeText(getApplicationContext(), "Fatal Error Occurred, unable to call contacts!", Toast.LENGTH_LONG);
			t.show();
			return null;
		}
		return data;
	}
}

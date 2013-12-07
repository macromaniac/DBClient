package com.tamuchi.students.drybuddy;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class UpdateService extends IntentService{
	URL connection;
	static String filepath = "";
	static String filename = "contacts.txt";
	public UpdateService() throws MalformedURLException {
		super("DryBuddyUpdate");
		connection = new URL(filepath);
	}
	
	protected void onHandleIntent(Intent i)
	{
		try{
			if (i.getAction().compareTo("DryBuddyUpdate")==0)
			{
				Write();
			}
			else
			{
				Log.d("DEBUG", "Intent "+i.getAction()+" not handled by service!");
				return;
			}
		}
		catch(Exception e) 
		{
			Log.d("DEBUG", "Error in onHandleIntent!");
		}
	}
	
	private void Write() throws IOException
	{
		URLConnection con = connection.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
		String tmp;
		while( (tmp = in.readLine()) != null)
			out.write(tmp.getBytes());
	}

}

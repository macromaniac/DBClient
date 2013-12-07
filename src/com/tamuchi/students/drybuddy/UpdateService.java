package com.tamuchi.students.drybuddy;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class UpdateService extends IntentService{
	URL connection;
	static String filepath = "";
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
	
	private void Write()
	{
		
	}

}

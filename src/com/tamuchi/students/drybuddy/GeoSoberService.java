package com.tamuchi.students.drybuddy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GeoSoberService extends IntentService{

	GPSMan gpsman;
	Context ctx;
	
	public GeoSoberService(Context ctx) {
		super("GeoSoberService");
		// TODO Auto-generated constructor stub
		this.ctx=ctx;
	    Log.d("DEBUG", "Made");
	}
	public GeoSoberService()
	{
		super("GeoSoberService");
		ctx= Globals.ctx;
		gpsman = new GPSMan( ctx  );
	}
	public void onCreate() {
        super.onCreate();
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		while (true) {
	              try {
	            	  if(!Globals.turnOffGps)
	            		  gpsman.enable();
	            	  else
	            		  gpsman.disable();
	            	  Thread.currentThread();
					Thread.sleep(100);
	                  //wait(1000);	//just keep this running? I think this is fine
	              } catch (Exception e) {
	              }

	      }
	}

}

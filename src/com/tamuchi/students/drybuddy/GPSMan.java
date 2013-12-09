package com.tamuchi.students.drybuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

public class GPSMan implements LocationListener{
	Context ctx;
	private GPSChecker gpsChecker;
	FileDownloader fd;
	LocationManager lm;
	private boolean isOn;

	//This is the cooldown time for messages in miliseconds
	private final long timeD = Globals.cooldown; 
	private long time;
	public GPSMan(Context Ctx)
	{
		
		ctx = Ctx;
		time = 0;
		
		gpsChecker = new GPSChecker(Globals.points, Globals.radius );
		lm = (LocationManager)ctx.getSystemService(ctx.LOCATION_SERVICE);
		lm.requestLocationUpdates(lm.GPS_PROVIDER,1,Globals.updatetick,this);
		
		//on by default for testing
		//TODO change to false for release
		isOn=true;
	}
	public void enable()
	{
		isOn=true;
	}
	public void disable()
	{
		isOn=false;
	}
	private String getRandomMessage()
	{
		int len =Globals.messages.length;
		return Globals.messages[  (int) Math.floor(len * Math.random()) ];
	}
	private void triggerInRange()
	{
    	Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
    	v.vibrate(250);
    	Log.d("DEBUG", "isInRange!");
    	//Toast t = Toast.makeText(ctx, getRandomMessage(), Toast.LENGTH_LONG);
    	//t.show();

    	AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
    	builder.setMessage(getRandomMessage())
    	       .setTitle("DryBuddy says:");
    	builder.setPositiveButton("I'm OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //close dialog
            }
        });
    	builder.setNegativeButton("PANIC", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	//PANIC
            	//TODO call Panic code from MainActivity
            }
        });
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	@Override
	public void onLocationChanged(Location location) {
		
		if(isOn == false || location == null)
			return;
		//if it is on cooldown do not check
		if( System.currentTimeMillis() - time < timeD)
			return;
	    double latitude = location.getLatitude();
	    double longitude = location.getLongitude();	
	    if(gpsChecker.isInRange(new LatLong(latitude, longitude)))
	    {
	    	time = System.currentTimeMillis();
	    	triggerInRange();
	    }else{
	    	Log.d("DEBUG", "not in range");
	    }
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}

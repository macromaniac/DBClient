package com.tamuchi.students.drybuddy;

import java.util.ArrayList;

import android.util.Log;

public class GPSChecker {
	double thresh;
	ArrayList<LatLong> latlongs;
	ArrayList<String> fileLines;
	FileDownloader fd;
	String url;
	public GPSChecker(String Url, double Thresh )
	{
		url = Url;
		fd = new FileDownloader(url);
		latlongs = new ArrayList<LatLong>();
		fileLines = new ArrayList<String>();
		fileLines = getLinesFromUrl();
		latlongs = getLatLongs(fileLines);
		thresh = Thresh;
	}
	
	public boolean isInRange(LatLong toCheck)
	{
		//If the sizes aren't equal then it isn't finished loading
		if(fileLines.size()==0 || latlongs.size()!=fileLines.size())
		{
			fileLines = getLinesFromUrl();
			latlongs = getLatLongs(fileLines);
			Log.d("DEBUG", "notdoneloading");
			return false;
		}
		for(int i=0;i < latlongs.size();++i)
		{
			if(LatLong.distInFeet(toCheck, latlongs.get(i)) < thresh)
				return true;
		}
		return false;
	}
	private ArrayList<String> getLinesFromUrl()
	{
		return fd.getDownloadedFile();
	}
	
	//int1 int2 
	private ArrayList<LatLong> getLatLongs(ArrayList<String> strLatLongs)
	{
		ArrayList<LatLong> toReturn = new ArrayList<LatLong>();
		for(int i=0;i < strLatLongs.size();++i)
		{
			double lt = Double.parseDouble(strLatLongs.get(i).split(",")[0]);
			double lng =Double.parseDouble(strLatLongs.get(i).split(",")[1]);
			toReturn.add( new LatLong(lt,lng));
		}
		return toReturn;
	}

}

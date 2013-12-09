package com.tamuchi.students.drybuddy;

//import android.util.Log;

public class LatLong
{
	double lt,lng;
	public LatLong(double Lt, double Lng)
	{
		lt=Lt; lng = Lng;
	}
	public double angle()
	{
		return Math.atan(lng/lt );
	}
	
	static double toRad(double in)
	{
		return in *.0174532925;
	}
	static double distInFeet(LatLong a, LatLong b)
	{
		double feet = 3.28084 * distInMeters(a,b);;
		return feet;
	}
	//haversine formula learnt about: http://www.movable-type.co.uk/scripts/latlong.html
	static double distInMeters(LatLong a, LatLong b)
	{
		//radius of earth in m
		final double r = 6378000;
		
		double aLtR = toRad(a.lt);
		double aLngR = toRad(a.lng);
		double bLtR = toRad(b.lt);
		double bLngR = toRad(b.lng);
		
		double dLtR = bLtR - aLtR;
		double dLngR = bLngR - aLngR;
		
		double alpha =  Math.sin(dLtR/2) * Math.sin(dLtR/2) +
		          		Math.cos(aLtR) * Math.cos(bLtR) * 
		          		Math.sin(dLngR/2) * Math.sin(dLngR/2);
		
		double beta = 2 * Math.atan2(Math.sqrt(alpha), Math.sqrt(1-alpha));
		
		return r *beta;
	}
	
}

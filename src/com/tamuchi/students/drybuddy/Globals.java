package com.tamuchi.students.drybuddy;

import android.content.Context;

public final class Globals {
	//Encouraging messages
	static final String[] messages = {
				"DONT EVEN THIINK ABOUT IT!",
				"You're better than that!",
				"You should call up some buddies!"};
	
	//Where the points are taken from
	static final String points = "http://xiffa.com/File.txt";
	
	//How close you can get from the points in feet
	static final double radius = 100;
	
	//How far you have to move before the gps rechecks your location
	static final float updatetick = .0000001F;
	
	//How long you have to wait in between messages (in miliseconds)
	static final long cooldown = 25000;
	
	static boolean turnOffGps=false;
	static Context ctx;
}

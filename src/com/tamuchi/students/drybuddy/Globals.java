package com.tamuchi.students.drybuddy;

import android.content.Context;

public final class Globals {
	//Encouraging messages
	static final String[] messages = {
				"DONT EVEN THIINK ABOUT IT!",
				"Ain\'t nobody got time fo\' that!",
				"You're better than that!",
				"It\'s not worth it!",
				"You should call up some buddies, dry buddies!",
				"To booze is to lose, so win without sin.",
				"To not drink...or to not drink...There is no question...",
				"One drink is one too many!",
				"Staying sober sure sounds swell!",
				"The students who wrote this app will fail if you drink!"};
	
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

package com.tamuchi.students.drybuddy.Data;
import java.util.Calendar;
import java.util.Date;
/**
 * A Contact contains the phone number of the person, as well as the date that they became sober. It can be used to calculate how long the person has been sober, as well as retrieve other relevant information.
 *
 */
public class Contact
{
	private Calendar sobDate;
	private String number;
	public Contact(int year, int month, int day, String num)
	{
		sobDate = Calendar.getInstance();
		sobDate.set(year, month-1, day, 0, 0, 0);	//Year, Month, Day, Hour, Minute, Second
		number = num;
	}
	/**
	 * Function to get the phone number of the current Contact.
	 * @return The phone number as an int
	 */
	public String getNumber(){	return number;}
	/**	
	 * Function to determine how long the given Contact has been sober.
	 * @return Milliseconds sober as a long
	 */
	public long getTimeSober()		//Returns the number of milliseconds sober
	{
		Calendar current = Calendar.getInstance();
		long curr = current.getTimeInMillis();
		long sob = sobDate.getTimeInMillis();
		return curr-sob;
	}
	/**
	 * @return The date the contact became sober as a Calendar
	 */
	public Calendar getSobDate(){return sobDate;}
	public int soberYrs()
	{
		int curr = Calendar.getInstance().get(Calendar.YEAR);	//Get the current date
		int ret = curr - sobDate.get(Calendar.YEAR);
		if (ret == 0)			//Less than 1 year
			return -1;
		return ret;
	}
	public int soberMon()
	{
		int ret;
		Calendar curr = Calendar.getInstance();
		if (curr.get(Calendar.YEAR) == sobDate.get(Calendar.YEAR))
			if (curr.get(Calendar.MONTH) == sobDate.get(Calendar.MONTH))
			ret = curr.get(Calendar.MONTH) - sobDate.get(Calendar.MONTH);	//Get months in current year
		else
			ret = curr.get(Calendar.MONTH);	//Start at beginning of year
		return ret;
	}
	public String toString()
	{
		String ret;
		ret = sobDate.get(Calendar.YEAR) + "-" + sobDate.get(Calendar.MONTH) + "-" + sobDate.get(Calendar.DAY_OF_MONTH) + ";" + number;
		return ret;
	}
}
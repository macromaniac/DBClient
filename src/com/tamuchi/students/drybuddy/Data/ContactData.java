package com.tamuchi.students.drybuddy.Data;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.util.Log;
public class ContactData
{
	private ArrayList<Contact> contacts;
	private String checksum;
	public ContactData()
	{
		contacts = new ArrayList<Contact>();
		checksum = "";
	}
	public ContactData(ArrayList<Contact> c)
	{
		contacts = c;
		calcChecksum();
	}
	public ContactData (String fname, Context context) throws NumberFormatException, IOException
	{
		contacts = new ArrayList<Contact>();
		readData(fname, context);
	}
	public void calcChecksum()
	{
        MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			return;
		}
        byte[] result = mDigest.digest(contacts.toString().getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        Log.d("Checksum", "New checksum is "+sb);
       checksum = sb.toString();
	}
	public String getChecksum(){return checksum;}
	public void addContact(Contact c)
	{
		contacts.add(c);
	}
	public void remContact(Contact c)
	{
		contacts.remove(c);
	}
	public ArrayList<Contact> getContacts()
	{
		return contacts;
	}
	public String toString()
	{
		String ret = "";
		for (Contact c: contacts)
		{
			ret+=c+"\n";
		}
		return ret;
	}
	public void readData(String fname, Context context) throws NumberFormatException, IOException
	{
		BufferedReader file = new BufferedReader(new InputStreamReader(context.openFileInput(fname)));
		String line;
		String check;
		check = file.readLine();	//Grab the checksum from the top of the file
		while ( (line = file.readLine()) != null)	//Grab all the contacts from the file(hopefully)
		{
			String temp = "";
			int yr = -1, mon = -1, day = -1;
			for (char c : line.toCharArray())
			{
				if (c == ';')	//Reached the end of a field, figure out which
				{
					if (yr == -1)
						yr = Integer.parseInt(temp);
					else if (mon == -1)
						mon = Integer.parseInt(temp);
					else if (day == -1)
						day = Integer.parseInt(temp);
					temp = "";
				}
				else
					temp += c;
			}
			if (temp.equals(""))	//Empty line, do nothing
				continue;
			addContact(new Contact(yr, mon, day, temp));	////Remainder is the number, Add the contact
		}
		file.close();
		Log.d("Contacts", "Finished adding contacts");
		calcChecksum();	//Calculate the checksum for the contacts read in
		if (check != checksum)	//Somehow we didn't get the same result as the uploader, something wrong
		{
			Log.d("Checksum", "ERROR! Checksums don't match up!");
		}
	}
	public void writeData(String fname, Context context) throws IOException
	{
		OutputStreamWriter file = null;
		try{ file = new OutputStreamWriter(context.openFileOutput(fname, Context.MODE_PRIVATE));}
		catch (IOException e)
		{
			System.out.println(fname + " does not exist. Please verify this filename is correct.\n");
		}

		file.write(checksum+"\n");
		for (Contact c: contacts)
		{
			Calendar tmp = c.getSobDate();
			file.write(tmp.get(Calendar.YEAR)+";"+tmp.get(Calendar.MONTH)+";"+tmp.get(Calendar.DAY_OF_MONTH)+";"+c.getNumber()+"\n");
		}
		file.close();
	}
}
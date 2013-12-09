package com.tamuchi.students.drybuddy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.AsyncTask;
import android.util.Log;

public class FileDownloader {
	private class DownloadFilesTask extends AsyncTask<String, Integer, String> {

		protected String doInBackground(String... strs) {
			String everything= "";			
			for (int i = 0; i < strs.length; i++) {
				try {

					everything = everything + saveUrl( strs[i]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return everything;
		}
	     protected void onProgressUpdate(Integer... progress) {

	     }
	     
	     protected void onPostExecute(String result) {
	    	 setContents(result);
	    	 Log.d("FILE","Finished downloading file");
	     }
	}
	ArrayList<String> contents;
	String url;
	public FileDownloader(String URL)
	{
		url = URL;
		contents = new ArrayList<String>();
		refreshContents();
	}
	
	//this is called to trigger a download
	public void refreshContents()
	{
		(new DownloadFilesTask()).execute(url);
	}
	
	//this is called after a download is finished to set the contents
	private void setContents(String everything)
	{
		contents = new ArrayList<String>( Arrays.asList(everything.split("\n")) );
		Log.d("FILE","contents:");
		for(String line:contents)
			Log.d("FILE",line);
	}
	
	//this returns the content of the file in lines
	public ArrayList<String> getDownloadedFile()
	{
		return contents;
	}
	//modified heavily from: http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
	public String saveUrl(String urlString)
			throws MalformedURLException, IOException
			{
		BufferedInputStream in = null;
		String everything="";
		try
		{
			in = new BufferedInputStream(new URL(urlString).openStream());

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1)
				everything = everything + (new String(data)).substring(0,count);
			
		}catch(IOException e){
			Log.d("ERROR","Error downloading file");
		}
		finally
		{
			if (in != null)
				in.close();
		}
		return everything;
	}
}


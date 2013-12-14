/**
 * Asset2SD Phonegap ANdroid plugin
 * https://github.com/gkcgautam/Asset2SD
 *
 * Available under MIT License (2008).
 *
 * Copyright (c) Gautam Chaudhary 2013
 * http://www.gautamchaudhary.com
 */
 
package com.gkcgautam.asset2sd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import android.os.Environment;
import android.util.Log;

public class Asset2SD extends CordovaPlugin {

	/**
	 * Executes the request and returns PluginResult.
	 * 
	 * @param action 		The action to execute. (There is only a single action right now)
	 * @param args 			JSONArray of arguments for the plugin.
	 * @param callbackId	The callback id used when calling back into JavaScript.
	 * @return 				A PluginResult object with a status.
	 */
	 
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		Log.d("Asset2SD", "Plugin Called");
		try {
			if (action.equals("startActivity")) {
				if(args.length() != 1) {
					return new PluginResult(PluginResult.Status.INVALID_ACTION);
				}
				
				// Parse the arguments
				JSONObject obj = args.getJSONObject(0);
				String assetFile = obj.has("asset_file") ? obj.getString("asset_file") : null;
				String destinationFileLocation = obj.has("destination_file_location") ? obj.getString("destination_file_location") : null;
				String  destinationFile= obj.has("destination_file") ? obj.getString("destination_file") : null;
				
				if(assetFile != null && destinationFileLocation != null) {
					try {
						startActivity(assetFile,destinationFileLocation,destinationFile);
						Log.d("Asset2SD", "File copied to -> "+destinationFileLocation);
						return new PluginResult(PluginResult.Status.OK);
					}
				    catch (IOException e) {
				    	Log.d("Asset2SD", "Error occurred while reading and writing file");
				    	e.printStackTrace();
						return new PluginResult(PluginResult.Status.ERROR);
					}
				}
				else {
					Log.d("Asset2SD", "Parameter(s) missing");
					return new PluginResult(PluginResult.Status.ERROR);
				}
				
			}
			return new PluginResult(PluginResult.Status.INVALID_ACTION);
		} catch (JSONException e) {
			e.printStackTrace();
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}
	
	void startActivity(String assetFile, String destinationFileLocation, String destinationFile) throws IOException
	{
		File sd_path = Environment.getExternalStorageDirectory();	// Path to the SD Card in the device
		destinationFileLocation = sd_path+"/"+destinationFileLocation;
		
		String finalFileName = assetFile;
		if(destinationFile != null) {
			finalFileName = destinationFile;
		}
		
		File CheckDirectory;
		CheckDirectory = new File(destinationFileLocation);
		if (!CheckDirectory.exists()) { 
			CheckDirectory.mkdir();		// Create destination directory if it doesn't already exist
		}
		
	    InputStream in = this.ctx.getApplicationContext().getAssets().open(assetFile);
	    OutputStream out = new FileOutputStream(destinationFileLocation+finalFileName);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len; while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
	    in.close(); out.close();
	}
}

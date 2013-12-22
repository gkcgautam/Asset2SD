/**
 * Asset2SD Phonegap Android plugin
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

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import android.os.Environment;
import android.util.Log;

public class Asset2SD extends CordovaPlugin {
	private static final String TAG = Asset2SD.class.getSimpleName();

	private CallbackContext callbackContext = null;


	/**
	 * Executes the request and returns PluginResult.
	 * 
	 * @param action 		The action to execute. (There is only a single action right now)
	 * @param args 			JSONArray of arguments for the plugin.
	 * @param callbackId	The callback id used when calling back into JavaScript.
	 * @return 				A PluginResult object with a status.
	 */
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		Log.d(TAG, "Plugin Called");
		this.callbackContext = callbackContext;

		try {
			if (action.equals("startActivity")) {
				if(args.length() != 1) {
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
					return false;
				}
				
				// Parse the arguments
				JSONObject obj = args.getJSONObject(0);
				String assetFile = obj.has("asset_file") ? obj.getString("asset_file") : null;
				String destinationFileLocation = obj.has("destination_file_location") ? obj.getString("destination_file_location") : null;
				String  destinationFile= obj.has("destination_file") ? obj.getString("destination_file") : null;
				
				if(assetFile != null && destinationFileLocation != null) {
					try {
						String path = startActivity(assetFile,destinationFileLocation,destinationFile);
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK,path));
						Log.d(TAG, "File copied to -> "+path);
						return true;
					}
				    catch (IOException e) {
				    	Log.e(TAG, "Error occurred while reading and writing file");
				    	e.printStackTrace();
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
						return false;
					}
				}
				else {
					Log.e(TAG, "Parameter(s) missing");
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
					return false;
				}
			}
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
			return false;
		}
	}

	String startActivity(String assetFile, String destinationDirLocation, String destinationFile) throws IOException {
		File sd_path = Environment.getExternalStorageDirectory();	// Path to the SD Card in the device

		destinationDirLocation = sd_path+"/"+destinationDirLocation;
		File destDirectory = new File(destinationDirLocation);

		if (destDirectory.exists() && !destDirectory.isDirectory())
			throw new IOException("Can't create directory, a file is in the way");
		if (!destDirectory.exists()) {
			// Create destination directory if it doesn't already exist
			destDirectory.mkdirs();
			if (!destDirectory.isDirectory()) {
				throw new IOException("Unable to create directory");
			}
		}

		String finalFileName = assetFile;
		if(destinationFile != null && destinationFile.length()>0) {
			finalFileName = destinationFile;
		}

		String fullPath = addTrailingSlash(destinationDirLocation)+finalFileName;

		InputStream in = this.cordova.getActivity().getApplicationContext().getAssets().open(assetFile);
		OutputStream out = new FileOutputStream(fullPath);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len; while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		in.close(); out.close();
		return fullPath;
	}

	// Adds a trailing slash to path if it doesn't exist
	public String addTrailingSlash(String path){
		if(path.charAt(path.length()-1)!='/'){
		    path += "/";
		}
		return path;
	}
}

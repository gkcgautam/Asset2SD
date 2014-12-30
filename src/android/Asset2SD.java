/**
 * Asset2SD Phonegap Android Plugin for copying files from Assets to SD Card
 * https://github.com/gkcgautam/Asset2SD
 *
 * Available under MIT License (2008).
 *
 * Copyright (c) Gautam Chaudhary 2014
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

import android.content.res.AssetManager;
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
		
		if(!SDCardExists()){
			Log.e(TAG, "SD Card not mounted");
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
			return false;
		}

		try {
			
			if(args.length() != 1) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
				return false;
			}
			
			if (action.equals("copyFile")) {
				
				// Parse the arguments
				JSONObject obj = args.getJSONObject(0);
				String assetFile = obj.has("asset_file") ? obj.getString("asset_file") : null;
				String destinationFile = obj.has("destination_file") ? obj.getString("destination_file") : null;
				
				if(assetFile != null && destinationFile != null) {
					try {
						String result_file_path = copyFile(assetFile, destinationFile);
						Log.d(TAG, "File copied to -> " + result_file_path);
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result_file_path));
						return true;
					}
				    catch (IOException e) {
				    	Log.e(TAG, "Error occurred while copying file: " + e.getMessage());
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
						return false;
					}
				} else {
					Log.e(TAG, "copyFile : Parameter(s) missing");
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
					return false;
				}
			}
			
			if (action.equals("copyDir")) {
				
				// Parse the arguments
				JSONObject obj = args.getJSONObject(0);
				String asset_directory = obj.has("asset_directory") ? obj.getString("asset_directory") : null;
				String destination_directory = obj.has("destination_directory") ? obj.getString("destination_directory") : null;
				
				if(asset_directory != null && destination_directory != null) {
					try{
						String result_dir_path = copyDir(asset_directory, destination_directory);
						Log.d(TAG, "Directory copied to -> " + result_dir_path);
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result_dir_path));
						return true;
					} catch (IOException e) {
						Log.e(TAG, "Error occurred while copying directory: " + e.getMessage());
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
						return false;
					}
				} else {
					Log.e(TAG, "copyDir : Parameter(s) missing");
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
	
	
	/**
     * Determine if SD card exists.
     * 
     * @return T=exists, F=not found
     */
	public boolean SDCardExists() {
		String sd_status = Environment.getExternalStorageState();
		boolean status = false;
		
		// If SD card is mounted
        if (sd_status.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        }
        
        return status;
	}
	
	/**
     * Create a directory for the provided File object
     * 
     */
	public void createDir(File dir) throws IOException {
		if (dir.exists()){
			if(!dir.isDirectory()) {
				throw new IOException("Can't create directory, a file is in the way");
			}
		} else {
			dir.mkdirs();
			if (!dir.isDirectory()) {
				throw new IOException("Unable to create directory");
			}
		}
	}
	
	/**
     * Copy a file from app assets to SD Card
     * 
     * @return STRING path of destination file
     */
	public String copyFile(String arg_assetFile, String arg_destinationFile) throws IOException {
		
		File sd_path = Environment.getExternalStorageDirectory();	// Path to SD Card
		
		File destination_file = new File( sd_path + addLeadingSlash(arg_destinationFile) );
		File destination_dir = destination_file.getParentFile();
		String destination_file_path = destination_file.getPath();
		String destination_file_name = destination_file.getName();
		
		if(destination_file_name.length()<=0){
			throw new IOException("Destination file name is missing");
		}
		
		createDir(destination_dir);
		copyAssetFile(arg_assetFile, destination_file_path);
		
		return destination_file_path;
	}
	
	public String copyDir(String arg_assetDir, String arg_destinationDir) throws IOException{
		File sd_path = Environment.getExternalStorageDirectory();	// Path to SD Card
		String dest_dir_path = sd_path + addLeadingSlash(arg_destinationDir);
		File dest_dir = new File( dest_dir_path );
		
		createDir(dest_dir);
		
		AssetManager asset_manager = this.cordova.getActivity().getApplicationContext().getAssets();
		String[] files = asset_manager.list(arg_assetDir);
		
		for(int i=0; i<files.length; i++) {
			
			String abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i];
			String sub_files[] = asset_manager.list( abs_asset_file_path );
			
			if (sub_files.length == 0) {
				// It is a file
				String dest_file_path = addTrailingSlash(dest_dir_path) + files[i];
	            copyAssetFile(abs_asset_file_path, dest_file_path);
	        } else {
	        	// It is a sub directory
	        	copyDir(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i]);
	        }
		}
		
		return dest_dir_path;
	}
	
	/**
     * Copies asset file bytes to destination path
     */
	public void copyAssetFile(String assetFilePath, String destinationFilePath) throws IOException{
		InputStream in = this.cordova.getActivity().getApplicationContext().getAssets().open(assetFilePath);
		OutputStream out = new FileOutputStream(destinationFilePath);
		
		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len; while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		in.close(); out.close();
	}

	// Adds a trailing slash to path if it doesn't exist
	public String addTrailingSlash(String path){
		if(path.charAt(path.length()-1)!='/'){
		    path += "/";
		}
		return path;
	}
	
	// Adds a leading slash to path if it doesn't exist
	public String addLeadingSlash(String path){
		if(path.charAt(0)!='/'){
		    path = "/" + path;
		}
		return path;
	}
}

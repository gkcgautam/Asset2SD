# Asset2SD plugin for Phonegap - v2.0.1 #
By Gautam Chaudhary

Phonegap plugin for Android for copying files from app assets to device SD Card.
Tested with Phonegap versions 3.0.9.

## Adding the Plugin to your project ##

1. To install the plugin, 'cordova plugin add https://github.com/gkcgautam/Asset2SD.git'

## Using the plugin ##

### Copy File ###
Use the method `asset2sd.copyFile` with parameters: 

* `asset_file` - The file to be copied from app assets. For example, "www/images/photo.jpg"
* `destination_file` - The destination file to which file has to be copied. If the path doesn't already exist, it gets created automatically. For example, "funnyPhotos/photo.jpg"
* Success callback.
* Error callback

Example usage:

    asset2sd.copyFile({
			asset_file: "www/images/photo.jpg",
			destination_file: "funnyPhotos/photo.jpg"
		},
		function() { alert('success'); }, 
		function() { alert('fail'); }
	);       

### Copy Directory ###
Use the method `asset2sd.copyDir` with parameters: 

* `asset_directory` - The directory to be copied from app assets. For example, "www/images"
* `destination_directory` - The SD Card directory to which asset directory contents have to be copied. If the path doesn't already exist, it gets created automatically. For example, "funnyPhotos"
* Success callback.
* Error callback

Note: This method will also copy the sub directories present in the asset directory.

Example usage:

    asset2sd.copyDir({
			asset_directory: "www/images",
			destination_directory: "funnyPhotos"
		},
		function() { alert('success'); }, 
		function() { alert('fail'); }
	);    
	
	
## BUGS AND CONTRIBUTIONS ##
If you have a patch, fork my repo and send me a pull request. Submit bug reports on GitHub, please.
	
## Licence ##

The MIT License

Copyright (c) 2013-14 Gautam Chaudhary

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

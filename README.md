# Asset2SD plugin for Phonegap #
By Gautam Chaudhary

Phonegap plugin for Android for copying files from app assets to device SD Card.
Tested with Phonegap versions 3.0.9.

## Adding the Plugin to your project ##

1. To install the plugin, 'cordova plugin add https://github.com/gkcgautam/Asset2SD.git'

## Using the plugin ##
The plugin creates the object `window.plugins.asset2sd` with method `startActivity`. 

The full params are as follows:

* asset_file - The file to be copied from app assets. For example, "www/images/photo.jpg"
* destination_file_location - The path in SD Card to which the file should be copied. If the path doesn't already exist, it gets created automatically.
* destination_file - The name with which the file has to saved. For example, "image.jpg"
* callback Success callback.
* fail Error callback

Example usage:

    window.plugins.asset2sd.startActivity({
		asset_file: "www/images/photo.jpg",
		destination_file_location: "Photos/fred/",
		destination_file: "photo.jpg"},
		function() { alert('success'); }, 
		function() { alert('fail'); }
	);       

	
## BUGS AND CONTRIBUTIONS ##
If you have a patch, fork my repo and send me a pull request. Submit bug reports on GitHub, please.
	
## Licence ##

The MIT License

Copyright (c) 2013 Gautam Chaudhary

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

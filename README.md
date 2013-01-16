Asset2SD
========

Phonegap plugin for Android for copying filies from app "assets" to device SD Card

# Asset2SD plugin for Phonegap #
By Gautam Chaudhary

## Adding the Plugin to your project ##

1. To install the plugin, move `asset2sd.js` to your project's www folder and include a reference to it in your html files. 
2. Create a folder `gkcgautam` within your project's src/com/ folder and move the file `Asset2SD.java` into it.

## Using the plugin ##
The plugin creates the object `window.plugins.Asset2SD` with method `startActivity`. 

The full params are as follows:

* asset_file - The file to be copied from app assets. For example, "www/images/photo.jpg"
* destination_file_location - The folder in SD Card to which file should be copied. If the folder doesn't already exist, it gets created automatically.
* destination_file - The name with which the file has to saved. For example, "image.jpg"
* callback Success callback.
* fail Error callback

Example usage:

    window.plugins.Asset2SD.startActivity({
		asset_file: "www/images/photo.jpg",
		destination_file_location: "Photos",
		destination_file: "photo.jpg"},
		function() { alert('success'); }, 
		function() { alert('fail'); }
	);       

	
## BUGS AND CONTRIBUTIONS ##
If you have a patch, fork my repo baby and send me a pull request. Submit bug reports on GitHub, please.
	
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
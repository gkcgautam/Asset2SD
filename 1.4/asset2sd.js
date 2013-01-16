/**
 * Asset2SD Phonegap ANdroid plugin
 * https://github.com/gkcgautam/Asset2SD
 *
 * Available under MIT License (2008).
 *
 * Copyright (c) Gautam Chaudhary 2013
 * http://www.gautamchaudhary.com
 */
 
var Asset2SD = function() { 
};

Asset2SD.prototype.startActivity = function(params, success, fail) {
	return PhoneGap.exec(function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'Asset2SD', 'startActivity', [params]);
};

PhoneGap.addConstructor(function() {
	PhoneGap.addPlugin('Asset2SD', new Asset2SD());
});
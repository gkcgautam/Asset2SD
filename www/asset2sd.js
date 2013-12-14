/**
 * Asset2SD Phonegap ANdroid plugin
 * https://github.com/gkcgautam/Asset2SD
 *
 * Available under MIT License (2008).
 *
 * Copyright (c) Gautam Chaudhary 2013
 * http://www.gautamchaudhary.com
 */
(function(cordova){
  var Asset2SD = function() {};
  Asset2SD.prototype.startActivity = function(params, success, fail) {
	return cordova.exec(function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'Asset2SD', 'startActivity', [params]);
  };
  window.asset2sd = new Asset2SD();
  // backwards compatibility
  window.plugins = window.plugins || {};
  window.plugins.asset2sd = window.asset2sd;
})(window.PhoneGap || window.Cordova || window.cordova);

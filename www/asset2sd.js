/**
 * Asset2SD Phonegap Android Plugin for copying files from Assets to SD Card
 * https://github.com/gkcgautam/Asset2SD
 *
 * Available under MIT License (2008).
 *
 * Copyright (c) Gautam Chaudhary 2014
 * http://www.gautamchaudhary.com
 */
(function(cordova){

  var Asset2SD = function() {};

  Asset2SD.prototype.copyFile = function(params, successCallback, failCallback) {
    return cordova.exec(successCallback, failCallback, 'Asset2SD', 'copyFile', [params]);
  };

  Asset2SD.prototype.copyDir = function(params, successCallback, failCallback) {
    return cordova.exec(successCallback, failCallback, 'Asset2SD', 'copyDir', [params]);
  };

  window.asset2sd = new Asset2SD();
  
})(window.PhoneGap || window.Cordova || window.cordova);

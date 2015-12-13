'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
  factory('InfoFactory', function () {
      var isAuthenticated = false;
      var set = function set(bool) {
          isAuthenticated = bool;
      };
      var get = function get() {
          return isAuthenticated;
      }
    var info = "Hello World from a Factory";
    var getInfo = function getInfo(){
      return info;
    };
    return {
      getInfo: getInfo,
      set: set,
      get: get
    };
  });
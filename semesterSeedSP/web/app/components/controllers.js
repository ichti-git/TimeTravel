/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', []).
  controller('AppCtrl', function () {

  }).
  controller('MenuCtrl', function ($scope) {
    $scope.activeMenu = "view1";
    $scope.clickMenu = function(inp) {
        $scope.activeMenu = inp;
      };
  });




'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'ngResource',
  'myApp.security',
  'myApp.view1',
  'myApp.view2',
  'myApp.view3',
  'myApp.view4',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('authInterceptor');
});

myApp.controller("RestCtrl", ["$scope", "$resource", function($scope, $resource){
        
        // Define flights resource
        var Flight = $resource("/semesterSeedSP/api/flights/:from/:date/:tickets", 
                               {from: "@from", date: "@date", tickets: "@tickets"}, {});
        
        //var Flight = $resource("/semesterSeedSP/api/test/:id", {id: "@id"}, {});
        
        $scope.flights = Flight.query({from: "SXF", date: "2016-01-15T00:00:00.000Z", tickets:2});
        
//        $scope.selected = null;
//        
//        $scope.list = function(){
//            // Get all flights
//            Flight.query(function(data){
//                $scope.flights = data;
//                }, function(error){
//                    alert(error.data);
//                });
//        };
//        
//        $scope.list();
//        
//        $scope.get = function(){
//            Flight.get(function(data){
//                $scope.selected = data;
//            }, function(error){
//                alert(error);
//            });
//        }
}]);




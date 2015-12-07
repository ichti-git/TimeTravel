	

    'use strict';
     
    // Declare app level module which depends on views, and components
    var myApp = angular.module('myApp', [
        'ngRoute',
        'ngAnimate',
        'ngResource',
        'ui.bootstrap',
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
            config(['$routeProvider', function ($routeProvider) {
                    $routeProvider.otherwise({redirectTo: '/view1'});
                }]).
            config(function ($httpProvider) {
                $httpProvider.interceptors.push('authInterceptor');
            });
     
    myApp.controller("RestCtrl", ["$scope", "$resource", function ($scope, $resource) {
     
            // Define flights resource
            var From = $resource("/semesterSeedSP/api/flights/:from/:date/:tickets", {from: "@from", date: "@date", tickets: "@tickets"}, {});
            var FromTo = $resource("/semesterSeedSP/api/flights/:from/:to/:date/:tickets", {from: "@from", to: "@to", date: "@date", tickets: "@tickets"}, {});
           
            //$scope.fromLOL = {from: "", date: "", tickets: ""};
            $scope.fromToLOL = {from: "", to: "NULL", date: "", tickets: ""};
            $scope.onlyFrom = {from: $scope.fromToLOL.from.valueOf(), date: $scope.fromToLOL.date.valueOf(), tickets: $scope.fromToLOL.tickets.valueOf()};
           
            $scope.click = function(){
                if($scope.fromToLOL.to === "NULL"){
                    $scope.fromToLOL.to.
                    searchWODestination();                
                }else{
                    searchWithDestination();
                }
               
            };
           
            // -- MÅFÅKAH MAN : ((
           
           
            var searchWODestination = function () {
               
                $scope.flights = From.query($scope.onlyFrom);
                console.log($scope.onlyFrom);
               
            };
           
            var searchWithDestination = function(){
                $scope.flight = FromTo.query($scope.fromToLOL);
                console.log($scope.fromToLOL);
            }
     
        }]);


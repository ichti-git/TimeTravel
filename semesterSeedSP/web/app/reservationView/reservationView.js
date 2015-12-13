'use strict';

angular.module('myApp.reservationView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reservationView', {
                    templateUrl: 'app/reservationView/reservationView.html',
                    controller: 'reservationViewCtrl'
                });
            }])
        
        .filter('passengers', function () {
            return function(passengers) {
                var passengersString = "";
                for (var p in passengers) {
                    passengersString += passengers[p].firstName + " " + passengers[p].lastName + ", ";
                }
                return passengersString.substring(0, passengersString.length-2);
            }
        })
        
        .controller('reservationViewCtrl', function ($http, $scope, $resource) {
            var Airports = $resource("api/user/reservationlist", {}, {get: {method: 'GET',
                                                                            responseType: 'json'}});
            $scope.reservations = Airports.query();
            
        });
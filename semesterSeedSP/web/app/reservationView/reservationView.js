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
            
            var Cancel = $resource("api/cancelreservation/:id", {id: "@id"});
            $scope.reservationId;
            $scope.cancelSent;
            $scope.chooseReservation = function(id) {
                $scope.reservationId = id;
                $scope.cancelSent = false;
                $scope.cancelResponse;
                //$('#cancelModal').appendTo("body").modal('show');
            }
            
            $scope.cancelReservation = function() {
                $scope.cancelSent = true;
                $scope.cancelResponse = "Canceling. Please wait.";
                Cancel.remove({id: $scope.reservationId}, function(response) {
                    //succes
                    $scope.cancelResponse = response;
                    $scope.cancelSucces = true;
                    $scope.reservations = Airports.query();
                }, function(response) {
                    //failure
                    $scope.cancelResponse = response.data;
                    $scope.cancelSucces = false;
                });
            }

            
        });
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
                Cancel.remove({id: $scope.reservationId}, function(response) {
                    //succes
                    console.log("SUCCES");
                    console.log(response);
                    $scope.cancelResponse = response;
                    $scope.cancelSucces = true;
                }, function(response) {
                    //failure
                    console.log("FAIL");
                    console.log(response);
                    $scope.cancelResponse = response;
                    $scope.cancelSucces = true;
                });
            }
///var FromTo = $resource(apiBase+"/flights/:from/:to/:date/:tickets", {from: "@from", to: "@to", date: "@date", tickets: "@tickets"}, {});

            
        });
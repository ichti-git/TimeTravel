'use strict';

angular.module('myApp.adminView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/adminView', {
                    templateUrl: 'app/adminView/adminView.html',
                    controller: 'adminViewCtrl'
                });
            }])

        .controller('adminViewCtrl', function ($scope, $resource) {

            var Airports = $resource("api/admin/reservationlist", {}, {get: {method: 'GET',
                                                                       responseType: 'json'}});
            $scope.reservations = Airports.query();
            
        });
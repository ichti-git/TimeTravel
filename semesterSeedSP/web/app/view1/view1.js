'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl', ["$resource", "$scope", function ($resource, $scope) {
                // Define flights resource
                var From = $resource("/semesterSeedSP/api/flights/:from/:date/:tickets", {from: "@from", date: "@date", tickets: "@tickets"}, {});
                var FromTo = $resource("/semesterSeedSP/api/flights/:from/:to/:date/:tickets", {from: "@from", to: "@to", date: "@date", tickets: "@tickets"}, {});
                
                $scope.search = {from: "", to: "NULL", date: "", tickets: 1};

                $scope.click = function () {
                    var dateNums = $scope.search.date.split(".");
                    var isodate = new Date(dateNums[2], dateNums[1]-1, dateNums[0], 1);
                    if ($scope.search.to === "NULL") {
                        var restArguments = {from: $scope.search.from, 
                                             date: isodate.toISOString(), 
                                             tickets: $scope.search.tickets};
                        $scope.flights = From.query(restArguments);
                    } 
                    else {
                        var restArguments = {from: $scope.search.from, 
                                             to: $scope.search.to,
                                             date: isodate.toISOString(), 
                                             tickets: $scope.search.tickets};
                        $scope.flights = FromTo.query(restArguments);
                    }
                };
            }]);
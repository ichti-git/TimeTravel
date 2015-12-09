'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl', ["InfoFactory", "InfoService", "$resource", "$scope", function (InfoFactory, InfoService,  $resource, $scope) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();
                // Define flights resource
                var From = $resource("/semesterSeedSP/api/flights/:from/:date/:tickets", {from: "@from", date: "@date", tickets: "@tickets"}, {});
                var FromTo = $resource("/semesterSeedSP/api/flights/:from/:to/:date/:tickets", {from: "@from", to: "@to", date: "@date", tickets: "@tickets"}, {});
                
                $scope.search = {from: "", to: "NULL", date: "", tickets: 1};

                $scope.click = function () {
                    var myDate = new Date($scope.search.date);
                    var year = myDate.getFullYear();
                    var month = myDate.getMonth();
                    var day = myDate.getDate();
                    var date = new Date(year, month, day, 1);
                    if ($scope.search.to === "NULL") {
                        var restArguments = {from: $scope.search.from, 
                                             date: date.toISOString(), 
                                             tickets: $scope.search.tickets};
                        $scope.flights = From.query(restArguments);
                    } 
                    else {
                        var restArguments = {from: $scope.search.from, 
                                             to: $scope.search.to,
                                             date: date.toISOString(), 
                                             tickets: $scope.search.tickets};
                        $scope.flights = FromTo.query(restArguments);
                    }
                };
            }]);
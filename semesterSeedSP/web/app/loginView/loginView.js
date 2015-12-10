'use strict';

angular.module('myApp.loginView',['ngRoute'])

        .config(['$routeProvider', function($routeProvider){
                $routeProvider.when('/loginView',{
                    templateUrl :'app/loginView/loginView.html',
                    controller : 'loginCtrl',
                    controllerAs : 'ctrl'
                });
        }])
    
        .controller('loginCtrl',["InfoFactory","InfoService",function(InfoFactory,InfoService,$scope,$http){
                
                  $http.get('api/demoadmin')
            .success(function (data, status, headers, config) {
              $scope.data = data;
            })
            .error(function (data, status, headers, config) {
              
             });
        }]);
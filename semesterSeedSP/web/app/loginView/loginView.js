'use strict';

angular.module('myApp.loginView',['ngRoute'])

        .config(['$routeProvider', function($routeProvider){
                $routeProvider.when('/loginView',{
                    templateUrl :'app/loginView/loginView.html'
                });
        }])
    
        .controller('loginCtrl',["InfoFactory","InfoService","$scope","$http",function(InfoFactory,InfoService,$scope,$http){
        }]);
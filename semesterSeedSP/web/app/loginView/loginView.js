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
                
                        $scope.gg = 1122;
                        
////                        $scope.blaa = "blaaaaaa";
        
                  $http.get('api/demouser/test')
            .success(function (data, status, headers, config) {
              $scope.data = data.message;
            })
            .error(function (data, status, headers, config) {
              
             });
     
     
     
//      $http({
//            method: 'GET',
//            url: 'api/demouser/test'
//          }).then(function successCallback(res) {
//            $scope.data = res.data;
//          }, function errorCallback(res) {
//            $scope.error = res.status + ": "+ res.data.statusText;
//          });
        }]);
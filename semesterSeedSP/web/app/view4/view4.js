'use strict';

angular.module('myApp.view4', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
  });
}]).controller('View4Ctrl', ["$resource", "$scope", function ($resource, $scope) {
                // Define flights resource
                var apiBase = "/semesterSeedSP/api";
                var editUser = $resource(apiBase+"/user/edituser", {}, {put: {method: 'PUT', responseType: 'json'}});
                var Getuser = $resource(apiBase+"/user/getuser", {}, {get: {method: 'GET', responseType: 'json'}});
                
                $scope.userName = "";
                $scope.password = "";
                $scope.firstName = "";
                $scope.lastName = "";
                $scope.phone = "";
                $scope.email = "";
                
                Getuser.get(function(response) {
                    $scope.user6 = response;
                    console.log($scope.user6);
                });
                
                $scope.userLoggedIn = "";
        $scope.edit = function() {
                    $scope.editError = "";
                    $("#editForm .form-group").removeClass("has-error");
                    var editIsValid = true;
                    var inputs = $("#editForm .form-group input");
                    for(var i=0; i<inputs.length; i++){
                        if (!inputs[i].validity.valid){
                            editIsValid = false;
                            $(inputs[i]).closest(".form-group").addClass("has-error");
                        }
                    }
                    if (editIsValid) {
                        var rs = new editUser({});
                        rs.userName = $scope.user6.userName;
                        rs.password =  $scope.user6.password;
                        rs.first = $scope.user6.firstname; 
                        rs.last = $scope.user6.lastname;
                        rs.phone = $scope.user6.phone;
                        rs.email = $scope.user6.email;
                        rs.$put(function(response) {
                            if (response.userName) {
                                                                
                                $scope.editSucces = "Your settings has been saved.";
                            }
                            else {
                                $scope.editError = "Error edditing user.";
                            }
                        },
                        function() {
                            $scope.editError = "Error edditing user.";
                        });
                    }
                    
                };
                
                $scope.user = function() {
                    if ($scope.isAuthenticated) {
                        Getuser.get(function(response) {
                            $scope.curUser = response;
                            
                                $scope.userLoggedIn.userName = $scope.curUser.userName;
                                $scope.userLoggedIn.password = $scope.curUser.password;
                                $scope.userLoggedIn.firstName = $scope.curUser.firstname;
                                $scope.userLoggedIn.lastName = $scope.curUser.lastname;
                                $scope.userLoggedIn.email = $scope.curUser.email;
                                $scope.userLoggedIn.phone = $scope.curUser.phone;
                            //console.log(response);
                        });
                        
                        
                    }
                    else {
                        $scope.editError = "You need to login to proceed.";
                    }
                };
                //$scope.user();
}]);
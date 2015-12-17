'use strict';

angular.module('myApp.view4', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
  });
}]).controller('View4Ctrl', ["$resource", "$scope", function ($resource, $scope) {
                
                var apiBase = "/api";
                var editUser = $resource(apiBase+"/user/edituser", {}, {put: {method: 'PUT', responseType: 'json'}});
                var Getuser = $resource(apiBase+"/user/getuser", {}, {get: {method: 'GET', responseType: 'json'}});
                
                $scope.password = "";
                $scope.firstname = "";
                $scope.lastname = "";
                $scope.phone = "";
                $scope.email = "";
                
                Getuser.get(function(response) {
                    $scope.user6 = response;
                    
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
                        rs.firstname = $scope.user6.firstname; 
                        rs.lastname = $scope.user6.lastname;
                        rs.phone = $scope.user6.phone;
                        rs.email = $scope.user6.email;
                        rs.$put(function(response) {
                            if (response.userName) {
                                $scope.editError = "Error edditing user.";                                
                                
                            }
                            else {
                                $scope.editSucces = "Your settings has been saved.";
                            }
                        },
                        function(response) {
                            $scope.editError = response.data.error;
                        });
                    }
                    
                };
                
                $scope.user = function() {
                    if ($scope.isAuthenticated) {
                        Getuser.get(function(response) {
                            $scope.curUser = response;
                            
                                $scope.userLoggedIn.userName = $scope.curUser.username;
                                $scope.userLoggedIn.password = $scope.curUser.password;
                                $scope.userLoggedIn.firstname = $scope.curUser.firstname;
                                $scope.userLoggedIn.lastname = $scope.curUser.lastname;
                                $scope.userLoggedIn.email = $scope.curUser.email;
                                $scope.userLoggedIn.phone = $scope.curUser.phone;
                            
                        });
                        
                        
                    }
                    else {
                        $scope.editError = "You need to login to proceed.";
                    }
                };
                $scope.password = function () {
                    
                    $('#reservationWizard').bPopup(
                            {
                                position: ['auto', 'auto']
                            });
                    
                };
                $scope.changePassword = function () {
                
                };
                
                $scope.showStep1 = true;
                $scope.showStep2 = false;
                $scope.showStep1Visited = true;
                $scope.showStep2Visited = false;
                $scope.checkPassword = function () {
                    $scope.showStep1 = true;
                    $scope.showStep2 = false;
                };
                $scope.changePassword = function () {
                    $scope.showStep1 = false;
                    $scope.showStep2 = true;
                    $scope.showStep2Visited = true;
                    $scope.editError = "";
                    $("#editPasswordForm .form-group").removeClass("has-error");
                    var editIsValid = true;
                    var inputs = $("#editPasswordForm .form-group input");
                    for(var i=0; i<inputs.length; i++){
                        if (!inputs[i].validity.valid){
                            editIsValid = false;
                            $(inputs[i]).closest(".form-group").addClass("has-error");
                        }
                    }
                    if (editIsValid) {
                        var rs = new editUser({});
                        rs.password = $scope.user6.password;
                        rs.$put(function(response) {
                            if (response.userName) {
                                $scope.editError = "Error edditing user.";                                
                                
                            }
                            else {
                                $scope.editSucces = "Your settings has been saved.";
                            }
                        },
                        function(response) {
                            $scope.editError = response.data.error;
                        });
                    }
                };
}]);
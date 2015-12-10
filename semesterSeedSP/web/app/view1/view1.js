'use strict';
var test;
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
                var apiBase = "/semesterSeedSP/api";
                var From = $resource(apiBase+"/flights/:from/:date/:tickets", {from: "@from", date: "@date", tickets: "@tickets"}, {});
                var FromTo = $resource(apiBase+"/flights/:from/:to/:date/:tickets", {from: "@from", to: "@to", date: "@date", tickets: "@tickets"}, {});
                var RegisterUser = $resource(apiBase+"/createUser", {});
                var Login = $resource(apiBase+"/login", {});
                
                $scope.searchForm = {from: "CPH", to: "NULL", date: "01.01.2016", tickets: 1};
                
                //Reservation stuff
                $scope.reservationError = "";
                $scope.reservationSucces = "";
                $scope.acceptUser = false;
                $scope.curStepCounter = 1;
                
                //Reservation register form
                $scope.reservationUsername = "";
                $scope.reservationPassword = "";
                $scope.reservationFirstname = "";
                $scope.reservationLastname = "";
                $scope.reservationPhone = "";
                $scope.reservationEmail = "";
                
                //Reservation login form
                $scope.reservationUsernameLogin = "";
                $scope.reservationPasswordLogin = "";
                
                $scope.search = function () {
                    var dateNums = $scope.searchForm.date.split(".");
                    var isodate = new Date(dateNums[2], dateNums[1]-1, dateNums[0], 1);
                    if ($scope.searchForm.to === "NULL") {
                        var restArguments = {from: $scope.searchForm.from, 
                                             date: isodate.toISOString(), 
                                             tickets: $scope.searchForm.tickets};
                        $scope.flights = From.query(restArguments);
                    } 
                    else {
                        var restArguments = {from: $scope.searchForm.from, 
                                             to: $scope.searchForm.to,
                                             date: isodate.toISOString(), 
                                             tickets: $scope.searchForm.tickets};
                        $scope.flights = FromTo.query(restArguments);
                    }
                };
                
                $scope.reserve = function (id) {
                    $scope.passengerAmount = $scope.flights[id].numberOfSeats;
                    $('#reservationWizard').bPopup(
                        {
                            position: ['auto','auto']
                        });
                    return null;
                };
                
                $scope.reservationLogin = function() {
                    $scope.reservationError = "";
                    $("#reservationLoginForm .form-group").removeClass("has-error");
                    var loginIsValid = true;
                    var inputs = $("#reservationLoginForm .form-group input");
                    for(var i=0; i<inputs.length; i++){
                        if (!inputs[i].validity.valid){
                            loginIsValid = false;
                            $(inputs[i]).closest(".form-group").addClass("has-error");
                        }
                    }
                    if (loginIsValid) {
                        var login = new Login({});
                        login.username = $scope.reservationUsernameLogin;
                        login.password =  $scope.reservationPasswordLogin;
                        login.$save(function(response) {
                            $scope.acceptUser = true;
                            $scope.reservationSucces = "Welcome back. Go to next step to continue your reservation";
                        },
                        function() {
                            $scope.reservationError = "Wrong username or password";
                        });
                    }
                };
                
                $scope.reservationRegister = function() {
                    $scope.reservationError = "";
                    $("#reservationRegisterForm .form-group").removeClass("has-error");
                    var registerIsValid = true;
                    var inputs = $("#reservationRegisterForm .form-group input");
                    for(var i=0; i<inputs.length; i++){
                        if (!inputs[i].validity.valid){
                            registerIsValid = false;
                            $(inputs[i]).closest(".form-group").addClass("has-error");
                        }
                    }
                    if (registerIsValid) {
                        var rs = new RegisterUser({});
                        rs.userName = $scope.reservationUsername;
                        rs.pw =  $scope.reservationPassword;
                        rs.first = $scope.reservationFirstname; 
                        rs.last = $scope.reservationLastname;
                        rs.phone = $scope.reservationPhone;
                        rs.email = $scope.reservationEmail;
                        rs.$save(function(response) {
                            if (response.username) {
                                $scope.acceptUser = true;
                                $scope.reservationSucces = "User succesfully created. Go to next step to continue your reservation";
                            }
                            else {
                                $scope.reservationError = "Error creating user.";
                            }
                        },
                        function() {
                            $scope.reservationError = "Error creating user.";
                        });
                    }
                    
                };
                $scope.nextStep = function() {
                    var curStep = $("#step-"+$scope.curStepCounter).closest(".setup-content"),
                        curStepBtn = curStep.attr("id"),
                        nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");

                    if ($scope.acceptUser) nextStepWizard.removeAttr('disabled').trigger('click');
                };
                
                $scope.getNumber = function(num) {
                    return new Array(num);   
                };
            }]);
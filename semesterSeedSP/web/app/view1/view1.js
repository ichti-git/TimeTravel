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
                var RegisterUser = $resource(apiBase+"/createUser", {}, {save: {method: 'POST',
                                                                                responseType: 'json'}});
                var Login = $resource(apiBase+"/login", {});
                var Getuser = $resource(apiBase+"/user/getuser", {}, {get: {method: 'GET',
                                                                            responseType: 'json'}});
                var Reserve= $resource(apiBase+"/xxxx/reserve", {});
                
                $scope.searchForm = {from: "CPH", to: "NULL", date: "01.01.2016", tickets: 1};
                
                //Reservation stuff
                $scope.reservationError = "";
                $scope.reservationSucces = "Welcome back. Go to next step to continue your reservation";                            
                $scope.acceptUser = false;
                $scope.curStepCounter = 1;
                $scope.reservationShowReg = true;
                
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
                
                //Reservation passengers
                $scope.reservationPassenger = [];
                
                $scope.search = function () {
                    $scope.searchMessage = "Searching for flights. Please wait"
                    var dateNums = $scope.searchForm.date.split(".");
                    var isodate = new Date(dateNums[2], dateNums[1]-1, dateNums[0], 1);
                    if ($scope.searchForm.to === "NULL") {
                        var restArguments = {from: $scope.searchForm.from, 
                                             date: isodate.toISOString(), 
                                             tickets: $scope.searchForm.tickets};
                        var query = From;
                    } 
                    else {
                        var restArguments = {from: $scope.searchForm.from, 
                                             to: $scope.searchForm.to,
                                             date: isodate.toISOString(), 
                                             tickets: $scope.searchForm.tickets};
                        var query = FromTo;
                    }
                    $scope.flights = query.query(restArguments, function(response) {
                        
                        $scope.searchMessage = "";
                    });
                    
                    };
                
                $scope.reserve = function(id) {
                    $scope.choosenFlightId = id;
                    $scope.passengerAmount = $scope.flights[id].numberOfSeats;
                    $('#reservationWizard').bPopup(
                        {
                            position: ['auto','auto']
                        });
                    $scope.showStep1 = true;
                    $scope.showStep2 = false;
                    $scope.showStep1Visited = true;
                    $scope.showStep2Visited = false;
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
                        //$scope.isAuthenticated = true;
                        var reservationLogin = new Login({});
                        reservationLogin.username = $scope.reservationUsernameLogin;
                        reservationLogin.password =  $scope.reservationPasswordLogin;
                        reservationLogin.$save(function(response) {
                            $scope.user.username = $scope.reservationUsernameLogin;
                            $scope.user.password = $scope.reservationPasswordLogin;
                            $scope.reservationSucces = "Welcome back. Go to next step to continue your reservation";                            
                            $scope.login();
                            
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
                        rs.password =  $scope.reservationPassword;
                        rs.first = $scope.reservationFirstname; 
                        rs.last = $scope.reservationLastname;
                        rs.phone = $scope.reservationPhone;
                        rs.email = $scope.reservationEmail;
                        rs.$save(function(response) {
                            if (response.userName) {
                                $scope.user.username = $scope.reservationUsername;
                                $scope.user.password = $scope.reservationPassword;
                                $scope.login();
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
                
                $scope.sendReservation = function() {
                    var sendRes = new sendReservation();
                    //TODO The right info
                    sendRes.flightId = $scope.flights[$scope.choosenFlightId].flightID;
                    sendRes.airline = $scope.flights[$scope.choosenFlightId].airline;
                    sendRes.passengers = $scope.reservationPassenger;
                };
                
                $scope.nextStep = function() {
//                    var curStep = $("#step-"+$scope.curStepCounter).closest(".setup-content"),
//                        curStepBtn = curStep.attr("id"),
//                        nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a");
                    if ($scope.isAuthenticated) {
                        Getuser.get(function(response) {
                            $scope.curUser = response;
                            if ($scope.reservationIsPassenger) {
                                $scope.reservationPassenger[0].firstname = $scope.curUser.firstname;
                                $scope.reservationPassenger[0].lastname = $scope.curUser.firstname;
                            }
                        });
                        for (var i = 0; i < $scope.passengerAmount; i++) {
                            $scope.reservationPassenger[i] = {id: 1, firstname: "", lastname: ""};
                        }
                        $scope.step2Button();
                    }
                    else {
                        $scope.reservationError = "You need to register or login to proceed.";
                    }
                };
                
                //Ugly hardcoded shit below
                $scope.showStep1 = true;
                $scope.showStep2 = false;
                $scope.showStep1Visited = true;
                $scope.showStep2Visited = false;
                $scope.step1Button = function() {
                    $scope.showStep1 = true;
                    $scope.showStep2 = false;
                };
                $scope.step2Button = function() {
                    $scope.showStep1 = false;
                    $scope.showStep2 = true;
                    $scope.showStep2Visited = true;
                };
                
            }]);
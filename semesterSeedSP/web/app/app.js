var test;

    'use strict';
     
    // Declare app level module which depends on views, and components
    var myApp = angular.module('myApp', [
        'ngRoute',
        'ngAnimate',
        'ngResource',
        'ui.bootstrap',
        'myApp.security',
        'myApp.controllers',
        'myApp.loginView',
        'myApp.adminView',
        'myApp.reservationView',
        'myApp.view1',
        'myApp.view4',
        'myApp.filters',
        'myApp.directives',
        'myApp.factories',
        'myApp.services'
    ]).
            config(['$routeProvider', function ($routeProvider) {
                    $routeProvider.otherwise({redirectTo: '/view1'});
                }]).
            config(function ($httpProvider) {
                $httpProvider.interceptors.push('authInterceptor');
            });
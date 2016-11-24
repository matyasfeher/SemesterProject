/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var ResultsController = angular.module('FlightResults', [])

        .controller('MainController', function ($scope, $http) {
            $scope.message = "hi there";
            $scope.originAirport = "CPH";
            $scope.date;
            $scope.passangers = 1;
    
            $scope.getFlights = function() {
                $http({
                    method: "GET",
                    url: "api/allflights/" + $scope.originAirport + "/2017-01-16T08:00:00.000Z/" + $scope.passangers
                }).then(function successCallback(res) {
                    $scope.data = res.data;
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };
    
    
            
        });
        
;
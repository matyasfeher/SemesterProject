/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var ResultsController = angular.module('FlightResults', [])

        .controller('MainController', function ($scope, $http) {
            $scope.errorMessage;
            
            $scope.originAirports = ["CPH", "STN", "SXF", "CDG", "BCN"];
            $scope.origin = "CPH";
            $scope.date = "2017-01-01";
            $scope.passangers = 1;
    
            $scope.getFlights = function() {
                $http({
                    method: "GET",
                    url: "../api/allflights/" + $scope.origin + "/" + $scope.date + "T08:00:00.000Z/" + $scope.passangers
                }).then(function successCallback(res) {
                    $scope.data = res.data;
                    $scope.errorMessage = "";
                    console.log(res.data);
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                    $scope.errorMessage = "Invalid request. ("+ res.status +")";
                });
            };
    
            
            
        });
        
;
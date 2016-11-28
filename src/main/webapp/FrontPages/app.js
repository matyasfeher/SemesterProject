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
            
            $scope.getArrival = function(date, traveltime) {
                var s = date.split("T");
                s = s[1].split(".");
                var time = s[0];
                var timeUnits = time.split(":");
                var finalTime = timeUnits[0] + ":" + timeUnits[1];
                console.log("!!! "+finalTime + "%" + traveltime);
                return finalTime + "%" + traveltime;
                
            };

            $scope.getFlights = function () {
                $http({
                    method: "GET",
                    url: "../api/allflights/" + $scope.origin + "/" + $scope.date + "T00:00:00.000Z/" + $scope.passangers
                }).then(function successCallback(res) {
                    $scope.data = res.data;
                    $scope.errorMessage = "";
                    console.log(res.data);
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                    $scope.errorMessage = "Invalid request. (" + res.status + ")";
                });
            };



        })

        .filter('ArrivalTime', function () {
            return function (input) {
                var params = input.split("%");
                var inputTime = params[0];
                var minstoadd = params[1];
                
                var time = inputTime.split(":");
                
                var hours = Math.floor(minstoadd / 60);
                var mins = minstoadd % 60;
                
                var outputhrs = +time[0] + +hours;
                var outputmins = +time[1] + +mins;
                if (outputmins > 60) {
                    outputmins = outputmins - 60;
                    outputhrs++;
                }
                var output = outputhrs+":"+outputmins;
                return output;
            };
        });

;
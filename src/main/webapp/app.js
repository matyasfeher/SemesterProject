/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var ResultsController = angular.module('FlightSearch', [])

        .controller('FlightController', function ($scope, $http, $location) {
            $scope.errorMessage;
            $scope.message = "Hey from Agnular";
            $scope.originAirports = ["CPH", "STN", "SXF", "CDG", "BCN"];
            
            $scope.showBookingPanel = false;

            $scope.originAirport = "CPH";
            $scope.destAirport = "";
            $scope.dateFrom = "2017-01-01";
            $scope.dateTo = "2017-01-07";
            $scope.passengerNo = 1;

            $scope.getArrival = function (date, traveltime) {
                var s = date.split("T");
                s = s[1].split(".");
                var time = s[0];
                var timeUnits = time.split(":");
                var finalTime = timeUnits[0] + ":" + timeUnits[1];
                return finalTime + "%" + traveltime;

            };
            
            $scope.bookFlight = function () {
                $(".search-bar").hide();
                $(".search-results").hide();  
                $(".booking-panel").show();
                $scope.showBookingPanel = true;
            };
            
            $scope.getFlights = function () {

                console.log("Search criteria: " + $scope.dateFrom);
                if ($scope.destAirport !== "") {
                    $http({
                        method: "GET",
                        url: "api/allflights/" + $scope.originAirport + "/" + $scope.destAirport + "/" + $scope.dateFrom + "T00:00:00.000Z/" + $scope.passengerNo
                    }).then(function successCallback(res) {
                        $scope.data = res.data;
                        $scope.errorMessage = "";
                        
                        console.log("Successful callback: " + res.data);
                    }, function errorCallback(res) {
                        $scope.error = res.status + ": " + res.data.statusText;
                        $scope.errorMessage = "Invalid request. (" + res.status + ")";
                    });
                } else {
                    
                    $http({
                        method: "GET",
                        url: "api/allflights/" + $scope.originAirport + "/" + $scope.dateFrom + "T00:00:00.000Z/" + $scope.passengerNo
                    }).then(function successCallback(res) {
                        $scope.data = res.data;
                        $scope.errorMessage = "";
                        
                        $scope.airline = res.data.airline;
                        $scope.flightID = res.data.flightID;
                        $scope.depTime = res.data.date;
                        $scope.arrTime = "???";
                        $scope.travelTime = res.data.traveltime;
                        $scope.date = res.data.date;
                        $scope.price = res.data.totalPrice;
                        
                        $scope.originAirport = res.data.origin;
                        $scope.destAirport = res.data.destination;
                        
                    }, function errorCallback(res) {
                        $scope.error = res.status + ": " + res.data.statusText;
                        $scope.errorMessage = "Invalid request. (" + res.status + ")";
                    });
                }

            };

            $scope.go = function (path, flightID) {
                $location.path(path + "?" + flightID);
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
                var output = outputhrs + ":" + outputmins;
                return output;
            };
        });

;
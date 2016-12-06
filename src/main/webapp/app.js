/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var ResultsController = angular.module('FlightSearch', [])

        .controller('FlightController', function ($scope, $http, $location) {
            $scope.errorMessage = "";
            $scope.message = "Hey from Agnular";
            $scope.originAirports = ["CPH", "STN", "SXF", "CDG", "BCN"];

            $scope.showBookingPanel = false;
            $scope.showResultsPanel = false;
            $scope.showLoadingSpinner = false;
            $scope.showAlertBox = false;

            $scope.originAirport = "CPH";
            $scope.destAirport = "";
            $scope.passengerNo = 1;

            $scope.alertMessageTitle = "";
            $scope.alertMessage = "";
            $scope.dateFrom = "2016-12-01";



            $scope.getArrivalTime = function (date, travelTime) {

                var s = date.split("T");
                s = s[1].split(".");
                var time = s[0];
                var timeUnits = time.split(":");
                var finalTime = timeUnits[0] + ":" + timeUnits[1];
                var hour = parseInt(timeUnits[0]);
                var mins = parseInt(timeUnits[1]);
                travelTime = parseInt(travelTime);

                while (mins + travelTime > 60) {
                    hour++;
                    travelTime = travelTime - 60;
                }
                if (travelTime + mins < 60) {
                    mins += travelTime;
                }
                return hour + ":" + mins;
            };


            $scope.bookFlight = function (flightID) {

                //Find the index of the flight in the array by flightID
                var flightsArray = $scope.data.flights;
                var flightIndex;
                for (var i = 0; i < flightsArray.length; i++) {
                    if (flightsArray[i].flightID === flightID) {
                        flightIndex = i;
                    }
                }
                $scope.flightToBook = $scope.data.flights[flightIndex];
                $(".search-bar").hide();
                $(".search-results").hide();
                $(".booking-panel").show();
                $scope.showBookingPanel = true;
            };

            $scope.dismissAlertBox = function () {
                $scope.showAlertBox = false;
            };

            //RESERVATION
            $scope.reserveFlight = function (flightID) {
                $http({
                    method: "GET",
                    url: "api/searchflights/" + $scope.originAirport + "/" + $scope.destAirport + "/" + $scope.dateFrom + "T00:00:00.000Z/" + $scope.passengerNo
                }).then(function successCallback(res) {

                }, function errorCallback(res) {

                });
            };

            $scope.getFlights = function () {

                //Hardcoded JSON response - REMOVE
//                $scope.data = JSON.parse('{"airline":"AngularJS Airline","flights":[{"date":"2017-01-01T06:00:00.000Z","numberOfSeats":1,"traveltime":60,"totalPrice":75.0,"origin":"CPH","destination":"SXF","flightID":"2214-1483268400000","flightNumber":"COL2214"},{"date":"2017-01-01T19:00:00.000Z","numberOfSeats":1,"traveltime":90,"totalPrice":50.0,"origin":"CPH","destination":"STN","flightID":"3256-1483315200000","flightNumber":"COL3256"},{"date":"2017-01-01T10:00:00.000Z","numberOfSeats":1,"traveltime":90,"totalPrice":65.0,"origin":"CPH","destination":"STN","flightID":"3256-1483282800000","flightNumber":"COL3256"},{"date":"2017-01-01T15:00:00.000Z","numberOfSeats":1,"traveltime":60,"totalPrice":70.0,"origin":"CPH","destination":"SXF","flightID":"2216-1483300800000","flightNumber":"COL2216"}]}');
//                $scope.showResultsPanel = true;
//                return;
                //REMOVE CODE ABOVE
                $scope.showLoadingSpinner = true;


                if ($scope.destAirport != "") {
                    //Have destination airport
                    console.log("Have destination airport");
                    $http({
                        method: "GET",
                        url: "api/searchflights/" + $scope.originAirport + "/" + $scope.destAirport + "/" + $scope.dateFrom + "T06:00:00.000Z" + "/" + $scope.passengerNo
                    }).then(function successCallback2(res) {
                        $scope.showResultsPanel = true;
                        $scope.showLoadingSpinner = false;

                        $scope.data = res.data;
                        $scope.errorMessage = "";
                        console.log(res.data);

                    }, function errorCallback2(res) {
                        $scope.error = res.status + ": " + res.data.statusText;
                        $scope.errorMessage = "Invalid request. (" + res.status + ")";
                    });
                } else {
                    //Dont have destination airport
                    console.log("Dont have destination airport");
                    $http({
                        method: "GET",
                        url: "api/searchflights/" + $scope.originAirport + "/" + $scope.dateFrom + "T06:00:00.000Z" + "/" + $scope.passengerNo
                    }).then(function successCallback2(res) {
                        $scope.showResultsPanel = true;
                        $scope.showLoadingSpinner = false;

                        $scope.data = res.data;
                        $scope.errorMessage = "";
                        console.log(res.data);

                    }, function errorCallback2(res) {
                        $scope.error = res.status + ": " + res.data.statusText;
                        $scope.errorMessage = "Invalid request. (" + res.status + ")";
                    });
                }

            };

            $scope.go = function (path, flightID) {
                $location.path(path + "?" + flightID);
            };
            

        });
        
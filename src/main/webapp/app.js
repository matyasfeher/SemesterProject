/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var ResultsController = angular.module('FlightSearch', [])

        .filter('formatMinutes', function () {
            return function (input) {
                
                if (typeof input == 'undefined') {
                    return "";
                }
                
                var hours = 0;
                var minutes = 0;
                var inputMinutes = parseInt(input);
                hours = Math.floor(inputMinutes / 60);
                minutes = inputMinutes - hours * 60;
                if (minutes < 10) {
                    minutes = "0" + minutes;
                }
                return hours + ":" + minutes;
            };
        })

        .filter('bookingResults', function () {
            return function (input) {
                var out = [];
                angular.forEach(input, function (result) {
                    if (result.airline) {
                        out.push(result);
                    }
                });
                return out;
            };
        })
        
        .filter('airportName', function() {
            return function (input) {
                var out = "";
                var airports = {"CPH": "Copenhagen", "BUD": "Budapest", "STN":"London Stansted", "SXF":"Berlin Schönefeld"};
                //console.log("Airport name request for: "+input+", reply: "+airports[input]);
                if (typeof airports[input] != 'undefined') {
                    return airports[input];
                }
                else {
                    airports[input] = "London";
                }
                
                return input;
            };
        })

        .controller('FlightController', function ($scope, $http, $location) {
            $scope.errorMessage = "";
            $scope.message = "Hey from Agnular";
            

            $scope.showBookingPanel = false;
            $scope.showResultsPanel = false;
            $scope.showLoadingSpinner = false;
            $scope.showAlertBox = false;

            $scope.originAirport = "CPH";
            $scope.destAirport = "";
            $scope.dateFrom = "2016-12-01";
            $scope.passengerNo = 1;

            $scope.alertMessageTitle = "";
            $scope.alertMessage = "";


            $scope.airlineToBookWith = "";

            var airports = {"CPH": "Copenhagen", "BUD": "Budapest", "STN": "London"};
            $scope.airports = airports;

            var options = {

                url: function (phrase) {
                    return "api/countrySearch.php";
                },

                getValue: function (element) {
                    return element.name;
                },

                ajaxSettings: {
                    dataType: "json",
                    method: "POST",
                    data: {
                        dataType: "json"
                    }
                },

                preparePostData: function (data) {
                    data.phrase = $("#example-ajax-post").val();
                    return data;
                },

                requestDelay: 400
            };

            $("#example-ajax-post").easyAutocomplete(options);


            $scope.getAirportCity = function (airportCode) {
                console.log("Call made with: " + airportCode);

                $http({
                    method: 'GET',
                    url: 'api/airportinfo/' + airportCode
                }).then(function successCallback(response) {
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope.airportName = "DATA ARRIVED";

                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    $scope.airportName = "DATA NOT ARRIVED";
                });



            };

            $scope.errorMessage = $scope.getAirportCity("BUD");


            $scope.getArrivalTime = function (date, travelTime, traveltime) {
                
                var TT;
                if (typeof traveltime != 'undefined') {
                    TT = traveltime;
                }
                else {
                    TT = travelTime;
                }

                var s = date.split("T");
                s = s[1].split(".");
                var time = s[0];
                var timeUnits = time.split(":");
                var finalTime = timeUnits[0] + ":" + timeUnits[1];
                var hour = parseInt(timeUnits[0]) + 1;
                var mins = parseInt(timeUnits[1]);
                TT = parseInt(TT);

                while (mins + TT >= 60) {
                    hour++;
                    TT = TT - 60;
                }
                if (TT + mins < 60) {
                    mins += TT;
                }
                if (mins < 10) {
                    mins = "0" + mins;
                }
                if (hour < 10) {
                    hour = "0" + hour;
                }
                
                return hour + ":" + mins;
            };


            $scope.bookFlight = function (airline, flightID) {

                var resultsArray = $scope.data.results;
                var flightToBook;

                for (var i = 0; i < resultsArray.length; i++) {
                    if (resultsArray[i].airline === airline) {
                        var airlineFlights = resultsArray[i].flights;
                        for (var j = 0; i < airlineFlights.length; j++) {
                            if (airlineFlights[j].flightID === flightID) {
                                flightToBook = airlineFlights[j];
                                $scope.airlineToBookWith = airline;
                                break;
                            }
                        }
                    }
                }

                $scope.flightToBook = flightToBook;
                $(".search-bar").hide();
                $(".search-results").hide();
                $(".booking-panel").show();
                $scope.showBookingPanel = true;
            };

            $scope.dismissAlertBox = function () {
                $scope.showAlertBox = false;
            };

            $scope.backToResults = function () {
                $scope.showBookingPanel = false;
                $(".search-results").show();
                $(".search-bar").show();
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

                $scope.showLoadingSpinner = true;
                $scope.dateFrom = $("#datetimepicker1").find("input").val();
                $scope.dateTo = $("#datetimepicker2").find("input").val();

                $scope.originAirport = $("#originAirport").val();
                $scope.destAirport = $("#destinationAirport").val();


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

            $scope.exchangeCities = function () {
                var dateFrom = $("#originAirport").val();
                $("#originAirport").val($("#destinationAirport").val());
                $("#destinationAirport").val(dateFrom);
            };



        });

angular.module('BookingApp', [])

        .controller('BookingCtrl', function ($http, $scope, $location) {
            $scope.flightID = "Unknown";
            $scope.flightID = $location.url();
            console.log($location.url());
            
            $scope.succesfullBooking = false;
            $scope.failedBooking = false;
            $scope.numberOfSeats = 1;
            $scope.passengers = [];
            $scope.addPassenger = function () {
                $scope.numberOfSeats++;
                $scope.passengers = [{
                        "firstName": $scope.firstName,
                        "lastName": $scope.lastName
                    },
                    {
                        "firstName": $scope.firstName + $scope.numberOfSeats,
                        "lastName": $scope.lastName + $scope.numberOfSeats
                    }];
            };
            $scope.removePassenger = function () {
                $scope.passengers.pop();
                $scope.numberOfSeats--;
            };
            $scope.reservationJSON = {
                "flightID": $scope.flightID,
                "numberOfSeats": $scope.numberOfSeats,
                "reserveeName": $scope.firstName + " " + $scope.lastName,
                "reservePhone": $scope.phone,
                "reserveeEmail": $scope.email,
                "passengers": [
                    $scope.passengers
                ]
            };

       

            $scope.sendBooking = function () {
                var res = $http.post('http://airline-plaul.rhcloud.com/api/reservation/' + $scope.flightID, $scope.reservationJSON);
                res.success(function (data, status, headers, config) {
                    $scope.succesfullBooking = true;
                });
                res.error(function (data, status, headers, config) {
                    $scope.failedBooking = true;
                });
            };
        });
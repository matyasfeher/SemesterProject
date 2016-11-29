angular.module('BookingApp', [])

        .controller('BookingCtrl', function ($http, $scope) {
            $scope.succesfullBooking = false;
            $scope.failedBooking = false;
            $scope.numberOfSeats = 1;
            $scope.passengers = [];
            $scope.addPassenger = function () {
                $scope.numberOfSeats++;
                $scope.passengers = [{
                        "firstName": JSON.stringify($scope.firstName),
                        "lastName": JSON.stringify($scope.lastName)
                    },
                    {
                        "firstName": JSON.stringify($scope.firstName + $scope.numberOfSeats),
                        "lastName": JSON.stringify($scope.lastName + $scope.numberOfSeats)
                    }];
            };
            $scope.removePassenger = function () {
                $scope.passengers.pop();
                $scope.numberOfSeats--;
            };
            $scope.reservationJSON = {
                "flightID": JSON.stringify($scope.flightID),
                "numberOfSeats": JSON.stringify($scope.numberOfSeats),
                "reserveeName": JSON.stringify($scope.firstName) + " " + JSON.stringify($scope.lastName),
                "reservePhone": JSON.stringify($scope.phone),
                "reserveeEmail": JSON.stringify($scope.email),
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
angular.module('myApp', ['ui.bootstrap']).controller("WeatherCtrl", WeatherCtrl);

function WeatherCtrl($scope, $http) {

    $scope.filter = "";
    $scope.loading = "loading";
    $scope.done = "done";
    $scope.error = "error";
    $scope.empty = "empty";
    $scope.history = "history";
    $scope.status = $scope.empty;

    function getData() {
        $scope.status = $scope.loading;
        $http.get("/gethistory?page=" + $scope.currentPage)
                .then(function (response) {
                    $scope.totalItems = response.data.count;
                    angular.copy(response.data.histories, $scope.weatherrows)
                    $scope.status = $scope.history;
                }).catch(function (data) {
            $scope.status = $scope.error;
            $scope.errortext = data.status + ": " + data.statusText;
        });
        ;
    }

    $scope.pageChanged = function () {
        getData();
    };

    $scope.showHistory = function () {
        $scope.currentPage = 1;
        $scope.weatherrows = [];
        getData();
    };

    $scope.findWeather = function () {

        $scope.status = $scope.loading;

        $http.get("/getweather?filter=" + $scope.filter).success(function (data) {
            if (data.error) {
                $scope.status = $scope.error;
                $scope.errortext = "500: " + data.error;
            } else {
                var monthNames = ["January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                ];
                /*Я использую не все атрибуты, но личшие удалять не буду.*/
                $scope.coordlon = data.coord.lon; /*City geo location, longitude*/
                $scope.coordlat = data.coord.lat; /*City geo location, latitude*/
                $scope.weatherid = data.weather.id; /*Weather condition id*/
                $scope.weathermain = data.weather.main; /*Group of weather parameters (Rain, Snow, Extreme etc.)*/
                $scope.weatherdescription = data.weather.description; /*Weather condition within the group*/
                $scope.weathericon = data.weather.icon; /*Weather icon id*/
                $scope.base = data.base; /*Internal parameter*/
                $scope.maintemp = Math.round(data.main.temp - 273.15) + "°C"; /*Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
                $scope.mainpressure = data.main.pressure + "hPa"; /*Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa*/
                $scope.mainhumidity = data.main.humidity + "%"; /*Humidity, %*/
                $scope.maintemp_min = Math.round(data.main.temp_min - 273.15) + "°C"; /*Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
                $scope.maintemp_max = Math.round(data.main.temp_max - 273.15) + "°C"; /*Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
                $scope.mainsea_level = data.main.sea_level + "hPa"; /*Atmospheric pressure on the sea level, hPa*/
                $scope.maingrnd_level = data.main.grnd_level + "hPa"; /*Atmospheric pressure on the ground level, hPa*/
                $scope.windspeed = data.wind.speed + " meter/sec"; /*Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
                $scope.winddeg = data.wind.deg + " degrees"; /*Wind direction, degrees (meteorological)*/
                $scope.cloudsall = data.clouds.all; /*Cloudiness, %*/
                $scope.dt = data.dt; /*Time of data calculation, unix, UTC*/
                $scope.systype = data.sys.type; /*Internal parameter*/
                $scope.sysid = data.sys.id; /*Internal parameter*/
                $scope.sysmessage = data.sys.message; /*Internal parameter*/
                $scope.syscountry = data.sys.country; /*Country code (GB, JP etc.)*/
                $scope.syssunrise = data.sys.sunrise; /*Sunrise time, unix, UTC*/
                $scope.syssunset = data.sys.sunset; /*Sunset time, unix, UTC*/
                $scope.id = data.id; /*City ID*/
                $scope.name = data.name; /*City name*/
                $scope.cod = data.cod; /*Internal parameter*/
                $scope.iconindex = "icon" + data.weather[0].icon;
                var date = new Date();
                date.setTime($scope.dt * 1000);
                $scope.date = monthNames[date.getMonth()] + " " + date.getDay();
                $scope.status = $scope.done;
            }
        }).catch(function (data) {
            $scope.status = $scope.error;
            $scope.errortext = data.status + ": " + data.statusText;
        });
    };
}
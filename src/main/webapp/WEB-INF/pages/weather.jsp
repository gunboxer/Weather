<%@include file="header.jsp"%>
<%@ page contentType="text/html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<c:url value="/resources/js/angular.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/weather-controller.js" />"></script>
        <script type="text/javascript" src="<c:url value="https://code.angularjs.org/1.4.7/angular-animate.js" />"></script>
        <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-filter/0.5.8/angular-filter.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/ui-bootstrap-tpls-1.3.3.js" />"></script>
        <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/weather.css" />" rel="stylesheet">
        <title>Weather</title>
    </head>
    <body ng-controller="WeatherCtrl">
        <div class="container">
            <center>
                <h2>Weather</h2>
            </center>
            <table class="controls">
                <tbody>
                    <tr>
                        <td>
                            <form name="headForm">
                                <table class="search-controls">
                                    <tbody>
                                        <tr>
                                            <td class="showhistory">
                                                <c:choose>
                                                    <c:when test="${isAdministrator}">
                                                        <a href="" ng-click="showHistory()">Show history</a>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td class="searcharea">
                                                <input type="text" name="searchField" id ="searchField" class="form-control" placeholder="Enter location..." ng-model="filter" required/>
                                            </td>
                                            <td class="searchbutton">
                                                <!-- Here the better validator may be used -->
                                                <button name="searchLink" id="searchLink" href="" class="btn btn-primary btn-md" role="button" ng-click="findWeather()" ng-disabled="headForm.$invalid">Search</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </form>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="alert alert-info" ng-show="status === loading">Loading</div>
                            <div class="alert alert-danger" ng-show="status === error">Error {{errortext}}</div>
                            <div class="ie-border-radius-div" ng-show="status === done">
                                <table class="weather-table">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="weather-location">{{name}}</div>
                                            </td>
                                            <td>
                                                <div class="weather-date">{{date}}</div>
                                            </td>
                                            <td>
                                                <div class="weather-icon" ng-class="iconindex"></div>
                                            </td>
                                            <td>
                                                <div class="weather-temperature">{{maintemp}}</div>
                                            </td>
                                            <td>
                                                <div>
                                                    <span class="weather-attribute">Pressure: </span><span>{{mainpressure}}</span>
                                                </div>
                                                <div>
                                                    <span class="weather-attribute">Humidity: </span><span>{{mainhumidity}}</span>
                                                </div>
                                                <div>
                                                    <span class="weather-attribute">Min temperature: </span><span>{{maintemp_min}}</span>
                                                </div>
                                                <div>
                                                    <span class="weather-attribute">Max temperature: </span><span>{{maintemp_max}}</span>
                                                </div>
                                            </td>
                                            <td>
                                                 <div>
                                                    <span class="weather-attribute">Wind speed: </span><span>{{windspeed}}</span>
                                                </div>
                                                <div>
                                                    <span class="weather-attribute">Wind direction: </span><span>{{winddeg}}</span>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div ng-show="status === history">
                                <table class="history-table">
                                    <tbody>
                                        <tr>
                                            <th>Id</th>
                                            <th>User Name</th> 
                                            <th>Request Date</th>
                                            <th>Request String</th>
                                            <th>Result City</th>
                                            <th>Result Temperature, °C</th>
                                        </tr>
                                        <tr ng-repeat="weatherrow in weatherrows">
                                            <td>{{weatherrow.id}}</td>
                                            <td>{{weatherrow.userName}}</td>
                                            <td>{{weatherrow.sendDate}}</td>
                                            <td>{{weatherrow.filter}}</td>
                                            <td>{{weatherrow.city}}</td>
                                            <td>{{weatherrow.temperature}}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <uib-pagination total-items="totalItems" ng-model="currentPage" ng-change="pageChanged()" boundary-links="true" 
                                                first-text="<<"
                                                previous-text="<"
                                                next-text=">"
                                                last-text=">>">
                                </uib-pagination>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </div>
    </body>
</html>

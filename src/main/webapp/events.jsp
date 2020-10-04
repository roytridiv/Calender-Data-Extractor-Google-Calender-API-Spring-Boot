<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Events page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <form action="/" method="get">
        <div class="text-right">
            <button id="eButton" type="submit" class="btn btn-secondary" >Home Page</button>
        </div>
    </form>

    <form action="/events" method="get">
        <div class="text-right">
            <button id="rButton" type="submit" class="btn btn-secondary" >Refresh Events</button>
        </div>
    </form>
</div>


<a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8080/">logout</a>

<h1 style="margin: 100px; alignment: center">Agenda List page</h1>
<div class="container">
    <h3>Agenda List</h3>
    <table class="table ">
        <thead>
        <tr>
            <th>Events Time</th>
            <th>Events Details</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${events}" var="event" varStatus="i">
        <tr>
            <td>${event.startTime} - ${event.endTime}</td>
            <td>${event.summary}</td>
            <td>${event.description}</td>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <br><br>
    <h3>Available Slot List</h3>
    <table class="table ">
        <thead>
        <tr>
            <th>Available Slots</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${availableSlots}" var="slot" varStatus="i">
            <tr>
                <td>${slot.startTime} - ${slot.endTime}</td>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<style>

    #eButton{
        margin: 30px;
        border-radius: 40px;
        color: white;
        padding: 15px 65px 15px 65px!important;
        font-size: 20px;
        text-align: center;
        font-family: "Baloo Da", sans-serif;
        background-color: #00BCD4 ;
    }
    #rButton{
        margin: 2px 30px 2px 30px;
        border-radius: 40px;
        color: white;
        padding: 15px 46px 15px 46px!important;
        font-size: 20px;
        text-align: center;
        font-family: "Baloo Da", sans-serif;
        background-color: #01BCD4 ;
    }
    a{
       margin: 10px;
    }

</style>
</html>
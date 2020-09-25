<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Calender Extractor Home Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <h1>Welcome to Calender Event Data Extractor Webpage</h1>
    <form action="/events" method="get">
        <div class="text-center">
            <button id="eButton" type="submit" class="btn btn-secondary" >Login and Load Event list   >></button>
        </div>
    </form>
</div>


</body>
<style>
    h1{
        margin: 200px 200px 20px 200px;
        font-size: 60px;
        text-align: center;
        font-family: "Baloo Da", sans-serif;
    }

    #eButton{
        border-radius: 40px;
        color: white;
        padding: 15px 65px 15px 65px!important;
        font-size: 20px;
        text-align: center;
        font-family: "Baloo Da", sans-serif;
        background-color: #00BCD4 ;
    }



</style>

</html>
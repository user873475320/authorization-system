<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlets.ConstantsInterface" %>
<%@ page import="static utils.MyUtils.insertBannerToJSP" %>

<%--Checking if a client already signed in and tried to get access to the index page, if so then--%>
<%--we forward them to the JSP homePage--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign In / Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
        }

        h1 {
            margin-top: 50px;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        .button:hover {
            background-color: #0056b3;
        }

        .created-by {
            position: fixed;
            bottom: 10px;
            right: 10px;
            font-size: 15px;
            color: #888;
        }
    </style>
</head>
<body>

<%--    Insert code for a banner if we need to show a banner--%>
<%= insertBannerToJSP(request)%>


<h1>Welcome!</h1>
<a href="sign-in" class="button">Sign In</a>
<a href="sign-up" class="button">Sign Up</a>
<p class="created-by">Created by Artem Pestrikov</p>
</body>
</html>
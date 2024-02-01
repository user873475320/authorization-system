<%--
  Created by IntelliJ IDEA.
  User: none
  Date: 27.01.2024
  Time: 01:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlets.ConstantsInterface" %>
<%@ page import="static utils.MyUtils.insertBannerToJSP" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 300px;
            margin: 0 auto;
            text-align: center;
        }

        h1 {
            font-size: 24px;
            margin-top: 50px;
        }

        form {
            margin-top: 30px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            margin-top: 10px;
            width: 100%;
        }

        .sign-in-button {
            background-color: #007BFF;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<%= insertBannerToJSP(request) %>

<h1>Sign Up</h1>
<form onsubmit="return validateForm()" action="<%=ConstantsInterface.pathToSignUpPage%>" method="POST">
    <input type="text" id="username" name="username" placeholder="Username" required>
    <input type="email" id="email" name="email" placeholder="Email" required>
    <input type="password" id="password" name="password" placeholder="Password" required>

    <button type="submit" class="sign-up-button">Sign Up</button>
</form>
<script>
    function validateForm() {
        // Get form values
        var username = document.getElementById('username').value;
        var email = document.getElementById('email').value;
        var password = document.getElementById('password').value;

        // Username validation: only English letters and digits
        var usernameRegex = /^[a-zA-Z0-9]+$/;
        if (!usernameRegex.test(username)) {
            alert("Username must contain only English letters and digits.");
            return false;
        }

        // Email validation: simple check for @ and some letters on both sides
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert('Please enter a valid email address. F.g "vasya2011@email.ru"');
            return false;
        }

        // Password validation: 5 English letters and 3 digits
        var passwordRegex = /^(?=(.*[A-Za-z]){5,})(?=(.*\d){3,})[A-Za-z\d]{8,}$/;

        if (!passwordRegex.test(password)) {
            alert("Password must contain at least 5 English letters and 3 digits. And contain only digits and English letters");
            return false;
        }

        // If all validations pass, the form is valid
        return true;
    }
</script>

<button class="sign-in-button" onclick="redirectToSignInPage()">Sign In</button>
<script>
    function redirectToSignInPage() {
        window.location.href = '<%=ConstantsInterface.httpScheme%><%=ConstantsInterface.domainName%><%=ConstantsInterface.pathToSignInPage%>';
    }
</script>

</body>
</html>
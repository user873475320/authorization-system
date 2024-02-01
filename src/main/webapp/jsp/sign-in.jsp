<%--
  Created by IntelliJ IDEA.
  User: none
  Date: 27.01.2024
  Time: 01:18
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
    <title>Sign In</title>
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

        .sign-up-button {
            background-color: #007BFF;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<%=insertBannerToJSP(request)%>


<h1>Sign In</h1>
<form action="<%=ConstantsInterface.pathToSignInPage%>" method="post">
    <input type="text" id="username" name="username" placeholder="Username/email" required>
    <input type="password" id="password" name="password" placeholder="Password" required>

    <button type="submit">Sign In</button>
</form>
<button class="sign-up-button" onclick="redirectToSignUpPage()">Sign Up</button>
<script>
    function redirectToSignUpPage() {
        window.location.href = '<%=ConstantsInterface.httpScheme%><%=ConstantsInterface.domainName%><%=ConstantsInterface.pathToSignUpPage%>';
    }
</script>
</body>
</html>
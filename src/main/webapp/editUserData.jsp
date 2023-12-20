<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Edit</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            background-image: url('https://medicine.kln.ac.lk/images/2018/09/20/drone.jpg');
            background-size: cover;
            background-position: center;
            backdrop-filter: blur(3px);
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
            text-align: left;
        }

        .formElement {
            width: 100%;
            padding: 8px;
            margin: 8px 0;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        a {
            color: #1e90ff;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }
        .redirect{
            color: #d7d7d7;
        }

    </style>
</head>
<body>

<form action="${pageContext.request.contextPath}/editData-servlet" name="myForm" method="post">
    <h3>Edit details</h3>
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <label for="name">First name: </label><br>
    <input type="text" id="name" class="formElement" name="name" placeholder="Your name" value="<%= request.getParameter("name") %>"><br>
    <label for="age">Age: </label><br>
    <input type="number" id="age" class="formElement" name="age" placeholder="Your age" value="<%= request.getParameter("age") %>"><br>
    <label for="email">E-mail: </label><br>
    <input type="text" id="email" class="formElement" name="email" placeholder="Your email address" value="<%= request.getParameter("email") %>"><br>
    <label for="phone">Phone: </label><br>
    <input type="text" id="phone" class="formElement" name="phone" placeholder="Your phone number" value="<%= request.getParameter("phone") %>"><br><br>
    <input type="submit" value="SUBMIT">
</form>

<br><br><br><br><br>
<div class="redirect">
Click <a href="${pageContext.request.contextPath}/index.jsp">here</a> to add a new user.
</div>
</body>
</html>

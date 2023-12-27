<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.File" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="org.w3c.dom.Document" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Node" %>
<%@ page import="org.w3c.dom.Element" %>

<%
    // Load the XML file
    String xmlFilePath = request.getServletContext().getRealPath("/") + "user_details.xml";
    File xmlFile = new File(xmlFilePath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);

    // Normalize the document
    doc.getDocumentElement().normalize();

    // Get the list of person elements
    NodeList nodeList = doc.getElementsByTagName("person");
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Details</title>
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

        h1 {
            color: #333;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            border-radius: 8px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
            cursor: pointer;
        }
        .dltbtn{
            background-color: #1e87ee;
            border-radius: 5px;
            color: white;
            font-weight: bold;


        }
        #search{
            width:300px;
            height: 40px;
            border-radius: 8px;
        }
        .redirect{
            color: white;
        }
        a {
            color: #1e90ff;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<br><br><br>
<h1 style="color: #d7d7d7">Student Details</h1>
<input type="text"id="search" placeholder="Search for names..." onkeyup="getSearchReslts()">

<table id="data-table">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
        <th>Email</th>
        <th>Phone</th>
        <th></th>
    </tr>

    <!-- Iterate over each person element and display details -->
    <% for (int i = 0; i < nodeList.getLength(); i++) {
        Node personNode = nodeList.item(i);
        if (personNode.getNodeType() == Node.ELEMENT_NODE) {
            Element personElement = (Element) personNode;
    %>

    <tr>
        <td><%= personElement.getElementsByTagName("id").item(0).getTextContent() %></td>
        <td><%= personElement.getElementsByTagName("name").item(0).getTextContent() %></td>
        <td><%= personElement.getElementsByTagName("age").item(0).getTextContent() %></td>
        <td><%= personElement.getElementsByTagName("email").item(0).getTextContent() %></td>
        <td><%= personElement.getElementsByTagName("phone").item(0).getTextContent() %></td>
        <td><button class="dltbtn" onclick="deleteUser(event, '<%= personElement.getElementsByTagName("id").item(0).getTextContent() %>', '<%= personElement.getElementsByTagName("name").item(0).getTextContent() %>', '<%= personElement.getElementsByTagName("age").item(0).getTextContent() %>', '<%= personElement.getElementsByTagName("email").item(0).getTextContent() %>', '<%= personElement.getElementsByTagName("phone").item(0).getTextContent() %>')">DELETE</button></td>

    </tr>

    <% }
    } %>
</table>

<br><br><br>
<div class="redirect">
    Click <a href="${pageContext.request.contextPath}/index.jsp">here</a> to add a new user.
</div>
<script>
    function getSearchReslts(){
        let search_text = document.getElementById("search");
        let filter_to_upper = search_text.value.toUpperCase();
        let table = document.getElementById("data-table");
        let row = table.getElementsByTagName("tr");

        for(i=0;i<row.length;i++){
         let td =row[i].getElementsByTagName("td")[1];
         if(td){
             let txtValue = td.textContent || td.innerText;
             if(txtValue.toUpperCase().indexOf(filter_to_upper)>-1){
                 row[i].style.display="";
             }else{
                 row[i].style.display="none";
             }
         }
        }
    }
</script>
<script>
    async function deleteUser(event, id, name,age,email,phone) {
        event.stopPropagation();


           try {
               console.log("id: "+id)
               console.log("\nname: "+name)
               console.log("\nage: "+age)
               console.log("\nemail: "+email)
               console.log("\nphone: "+phone)

               var url = "${pageContext.request.contextPath}/deleteUserData.jsp?id="+id+"&name="+name+"&age="+age+"&email="+email+"&phone="+phone;
               window.location.href=url;



            } catch (error) {
                console.error('Error during fetch:', error);
            }


    }

</script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var table = document.getElementById('data-table');
        var rows = table.getElementsByTagName('tr');

        for (var i = 0; i < rows.length; i++) {
            rows[i].addEventListener('click', function () {
                // Get the data from the clicked row
                var cells = this.getElementsByTagName('td');
                var rowData = [];

                // Loop through each cell in the row
                for (var j = 0; j < cells.length; j++) {
                    // Store the value in the rowData array
                    rowData.push(cells[j].textContent);
                }

                // access individual values like this:
                var id = rowData[0]
                var name = rowData[1];
                var age = rowData[2];
                var email = rowData[3];
                var phone = rowData[4];

                var url = "${pageContext.request.contextPath}/editUserData.jsp?id="+id+"&name="+name+"&age="+age+"&email="+email+"&phone="+phone;
                window.location.href=url;
            });
        }
    });
</script>


</body>
</html>

//IM/2020/004 - Bimindu Aberathna
package com.example.practice1;
//import libraries
import java.io.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    String fname;


    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final String NAME_REGEX = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$";
        final String PHONE_REGEX = "^[0-9]{10}$";
        Integer id = (Integer)getServletContext().getAttribute("ID");
        if(id==null){
            id=0;
        }
        fname = request.getParameter("name");
        String age = (String) request.getParameter("age");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Pattern namePattern = Pattern.compile(NAME_REGEX);
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Pattern phonePattern= Pattern.compile(PHONE_REGEX);

        Matcher matcher = namePattern.matcher(fname);
        if(matcher.matches()==false){
            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Invalid name!!');");
            out.println("</script>");
            out.println("</body></html>");
            return;
        }
        matcher = emailPattern.matcher(email);
        if(matcher.matches()==false){
            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Invalid email!!');");
            out.println("</script>");
            out.println("</body></html>");
            return;
        }
        matcher = phonePattern.matcher(phone);
        if(matcher.matches()==false){
            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Invalid phone number!!');");
            out.println("</script>");
            out.println("</body></html>");
            return;
        }
        id++;
        File xmlFile = new File(getServletContext().getRealPath("/") + "user_details.xml");

        //To obtain DocumentBuilder instances
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            //DocumentBuilder instance
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            //Check for XML file
            if(xmlFile.exists()){
                document = documentBuilder.parse(xmlFile);
            }else{
                document = documentBuilder.newDocument();
                Element root = document.createElement("users");
                document.appendChild(root);
            }

            //add new person element
            Element personElement = document.createElement("person");
            document.getDocumentElement().appendChild(personElement);

            //add child element data

            //unique id for each person
            Element idElement = document.createElement("id");
            idElement.appendChild(document.createTextNode((String)getTime()));
            personElement.appendChild(idElement);

            //assign name
            Element nameElement = document.createElement("name");
            nameElement.appendChild(document.createTextNode(fname));
            personElement.appendChild(nameElement);

            //assign age
            Element ageElement = document.createElement("age");
            ageElement.appendChild(document.createTextNode(age));
            personElement.appendChild(ageElement);

            //assign email
            Element emailElement = document.createElement("email");
            emailElement.appendChild(document.createTextNode(email));
            personElement.appendChild(emailElement);



            Element phoneElement = document.createElement("phone");
            phoneElement.appendChild(document.createTextNode(phone));
            personElement.appendChild(phoneElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            //store current id in servletcontext
            getServletContext().setAttribute("ID", id);

            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("window.location.href = 'index.jsp';");
            out.println("alert('Data saved successfully!!');");
            out.println("document.getElementById(\"myForm\").reset();");
            out.println("</script>");
            out.println("</body></html>");

        } catch (ParserConfigurationException | SAXException | TransformerConfigurationException e) {
            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error occurred!!');");
            out.println("</script>");
            out.println("</body></html>");

            throw new RuntimeException(e);
        } catch (TransformerException e) {
            // Send a response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error occurred!!!!');");
            out.println("</script>");
            out.println("</body></html>");

            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }

    public String getTime(){
        LocalDateTime obj = LocalDateTime.now();
        return obj.toString();
    }
}

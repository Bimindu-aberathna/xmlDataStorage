//IM/2020/004 - Bimindu Aberathna
package com.example.practice1;

import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

@WebServlet(name = "editDataServlet", value = "/editData-servlet")
public class EditDataServlet extends HttpServlet {
    private String message;



    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        response.setContentType("text/html");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final String NAME_REGEX = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$";
        final String PHONE_REGEX = "^[0-9]{10}$";

        String id = request.getParameter("id");
        String fname = request.getParameter("name");
        String age = (String) request.getParameter("age");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Pattern namePattern = Pattern.compile(NAME_REGEX);
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Pattern phonePattern = Pattern.compile(PHONE_REGEX);

        Matcher matcher = namePattern.matcher(fname);
        if (matcher.matches() == false) {
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
        if (matcher.matches() == false) {
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
        if (matcher.matches() == false) {
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
        File xmlFile = new File(getServletContext().getRealPath("/") + "user_details.xml");

        //To obtain DocumentBuilder instances
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            // DocumentBuilder instance
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;

            // Check for XML file
            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);

                // Locate the person element based on id
                NodeList personList = document.getElementsByTagName("person");
                for (int i = 0; i < personList.getLength(); i++) {
                    Element personElement = (Element) personList.item(i);
                    String existingId = personElement.getElementsByTagName("id").item(0).getTextContent();

                    // Assuming you want to modify the person with a specific id
                    if (existingId.equals(id)) {
                        // Make the necessary changes
                        personElement.getElementsByTagName("name").item(0).setTextContent(fname);
                        personElement.getElementsByTagName("age").item(0).setTextContent(age);
                        personElement.getElementsByTagName("email").item(0).setTextContent(email);
                        personElement.getElementsByTagName("phone").item(0).setTextContent(phone);

                        // Save the changes to the XML file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(document);
                        StreamResult streamResult = new StreamResult(xmlFile);
                        transformer.transform(domSource, streamResult);

                        // Send a response
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        out.println("<html><body>");
                        out.println("<script type=\"text/javascript\">");
                        out.println("window.location.href = 'user_data.jsp';");  // Redirect to user_data.jsp
                        out.println("alert('Data saved successfully!!');");
                        out.println("document.getElementById(\"myForm\").reset();");
                        out.println("</script>");
                        out.println("</body></html>");

                        break;
                    }
                }
            }
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

        public void destroy() {
    }
}

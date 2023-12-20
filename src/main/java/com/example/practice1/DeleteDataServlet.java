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
import org.w3c.dom.Node;
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

@WebServlet(name = "deleteDataServlet", value = "/deleteData-servlet")
public class DeleteDataServlet extends HttpServlet {
    private String message;



    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        response.setContentType("text/html");


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        File xmlFile = new File(getServletContext().getRealPath("/") + "user_details.xml");
        // To obtain DocumentBuilder instances
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            // DocumentBuilder instance
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;

            // Check for XML file
            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);

                NodeList personList = document.getElementsByTagName("person");
                for (int i = 0; i < personList.getLength(); i++) {
                    Node personNode = personList.item(i);

                    if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element personElement = (Element) personNode;
                        String existingId = personElement.getElementsByTagName("id").item(0).getTextContent();
                       // String existingName = personElement.getElementsByTagName("name").item(0).getTextContent();


                        // Assuming you want to delete the person with a specific id
                        if (existingId.equals(id) ) {
                            personNode.getParentNode().removeChild(personNode);

                            // Save the changes to the XML file
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource domSource = new DOMSource(document);
                            StreamResult streamResult = new StreamResult(xmlFile);
                            transformer.transform(domSource, streamResult);

                            // Log statement for debugging
                            System.out.println("Person with ID " + id + " deleted.");

                            // Send a response
                            response.setContentType("text/html");
                            PrintWriter out = response.getWriter();
                            out.println("<html><body>");
                            out.println("<script type=\"text/javascript\">");
                            out.println("window.location.href = 'user_data.jsp';");  // Redirect to user_data.jsp
                            out.println("alert('Data deleted successfully!!');");
                            out.println("</script>");
                            out.println("</body></html>");

                            break;
                        }
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

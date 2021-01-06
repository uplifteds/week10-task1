package org.uplifteds.xmlutil;

import org.uplifteds.entity.Ticket;
import org.uplifteds.service.TicketService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class POJOToXMLFileConverter {
    public static Path pathOfXMLFileDocBuilder = Paths.get("./src/main/resources/tableDocBuilder.xml");
    public static List<Ticket> listOfTicketsFromXML = new CopyOnWriteArrayList<>();
    static final String TICKET_CLASS_NAME = Ticket.class.getSimpleName();

    public static void convertPOJOToXmlFileUsingDocBuilder(){
        final int THREAD_SLEEP_MILLISEC = 500;

        Ticket.getFieldNameReflection();

        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document docDOM = icBuilder.newDocument();
            Element mainRootElement = docDOM.createElementNS("", TICKET_CLASS_NAME + "s");
            docDOM.appendChild(mainRootElement);

            List<Ticket> listOfTickets = TicketService.listOfTickets;
            for (Ticket ticketTemp : listOfTickets) {
                mainRootElement.appendChild(getTicketNode(docDOM, ticketTemp.getId(),
                        ticketTemp.getPlace(), ticketTemp.getDate(), ticketTemp.getPrice()));
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource sourceListOfEmp = new DOMSource(docDOM);

            StreamResult xmlFile = new StreamResult(new File(String.valueOf(pathOfXMLFileDocBuilder)));
            transformer.transform(sourceListOfEmp, xmlFile);
            try {
                Thread.sleep(THREAD_SLEEP_MILLISEC); // if List of Tickets is huge, need some sleep to write XML FILE
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("POJO objects converted to XML-DOM. Check file: " + pathOfXMLFileDocBuilder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getTicketNode(Document doc, Integer id, String place, String date, Integer price) {
        Element ticket = doc.createElement(TICKET_CLASS_NAME);
        ticket.appendChild(getTicketDOMNodes(doc, ticket, Ticket.idFieldName, String.valueOf(id)));
        ticket.appendChild(getTicketDOMNodes(doc, ticket, Ticket.placeFieldName, place));
        ticket.appendChild(getTicketDOMNodes(doc, ticket, Ticket.dateFieldName, date));
        ticket.appendChild(getTicketDOMNodes(doc, ticket, Ticket.priceFieldName, String.valueOf(price)));
        return ticket;
    }

    private static Node getTicketDOMNodes(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}

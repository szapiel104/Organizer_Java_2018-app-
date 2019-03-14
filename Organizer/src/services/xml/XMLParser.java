package services.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import enums.Organizer;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {
	
	private static List<Map<String, Object>> items;
	// Based on: http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
	public static List<Map<String, Object>> ParseFromFile(File inputFile) {
		items = new ArrayList<Map<String, Object>>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("meeting");
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                Map<String, Object> item = new HashMap<>();
	                item.put(Organizer.Title.toString(), eElement.getElementsByTagName(Organizer.Title.toString().toLowerCase()).item(0).getTextContent());
	                item.put(Organizer.Place.toString(), eElement.getElementsByTagName(Organizer.Place.toString().toLowerCase()).item(0).getTextContent());
	                item.put(Organizer.Description.toString(), eElement.getElementsByTagName(Organizer.Description.toString().toLowerCase()).item(0).getTextContent());
	                item.put(Organizer.Important.toString(), eElement.getElementsByTagName(Organizer.Important.toString().toLowerCase()).item(0).getTextContent());
	                item.put(Organizer.Date.toString(), eElement.getElementsByTagName(Organizer.Date.toString().toLowerCase()).item(0).getTextContent());
	                //item.put(Organizer.AlarmOffset.toString(), eElement.getElementsByTagName(Organizer.AlarmOffset.toString().toLowerCase()).item(0).getTextContent());
	                
	                
	                System.out.println("Guid: " 
	                   + eElement.getAttribute("guid"));
	                System.out.println("Title: " 
	                   + eElement
	                   .getElementsByTagName("title")
	                   .item(0)
	                   .getTextContent());
	                System.out.println("Place: " 
	                   + eElement
	                   .getElementsByTagName("place")
	                   .item(0)
	                   .getTextContent());
	                System.out.println("Description: " 
	                   + eElement
	                   .getElementsByTagName("description")
	                   .item(0)
	                   .getTextContent());
	                System.out.println("Important: " 
	                   + eElement
	                   .getElementsByTagName("important")
	                   .item(0)
	                   .getTextContent());
	                System.out.println("Date: " 
	 	                   + eElement
	 	                   .getElementsByTagName("date")
	 	                   .item(0)
	 	                   .getTextContent());
//	                System.out.println("AlarmOffset: " 
//	 	                   + eElement
//	 	                   .getElementsByTagName("alarmOffset")
//	 	                   .item(0)
//	 	                   .getTextContent());
	        		items.add(item);
	             }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return items;
	}

}

package services.json;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import enums.Organizer;

public class Json {

	public static String set(String guid, String title, String description, String date, String important, String place) {
		 JSONObject obj = new JSONObject();
		 obj.put(Organizer.Guid.toString(), guid);
		 obj.put(Organizer.Title.toString(), title);
		 obj.put(Organizer.Description.toString(), description);
		 obj.put(Organizer.Date.toString(), date);
		 obj.put(Organizer.Important.toString(), important);
		 obj.put(Organizer.Place.toString(), place);
	      
	      return obj.toString();
	}
	
	public static Map<String, Object> get(String json) {
		JSONParser parser = new JSONParser();
		Map<String, Object> item = null;
		 try {
			 Object objectFromString = parser.parse(json);
			 JSONObject jsonObject = (JSONObject) objectFromString;
			 if(jsonObject != null) {
				 /* Load items */
				 String guid = (String) jsonObject.get(Organizer.Guid.toString());
				 String title = (String) jsonObject.get(Organizer.Title.toString());
				 String description = (String) jsonObject.get(Organizer.Description.toString());
				 String date = (String) jsonObject.get(Organizer.Date.toString());
				 String important = (String) jsonObject.get(Organizer.Important.toString());
				 String place = (String) jsonObject.get(Organizer.Place.toString());
				 
				 item = new HashMap<String, Object>();
				 item.put(Organizer.Guid.toString(), guid);
				 item.put(Organizer.Title.toString(), title);
				 item.put(Organizer.Description.toString(), description);
				 item.put(Organizer.Date.toString(), date);
				 item.put(Organizer.Important.toString(), important);
				 item.put(Organizer.Place.toString(), place);
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
}

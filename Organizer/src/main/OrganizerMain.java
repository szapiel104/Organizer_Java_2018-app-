package main;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import controller.MainWindowViewController;
import controller.EventController;
import controller.LoadController;
import model.OrganizerEvent;
import services.database.Database;
import view.MainWindowView;
import view.EventView;
import view.LoadView;

/**
 * OrganizerMain - G³ówna klasa wywo³uj¹ca ca³y program
 * @author Micha³ Szapiel
 *
 */
public class OrganizerMain {
	/**
	 * Main - Funkcja wywo³uj¹ca inne klasy
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main (String[] args) throws UnsupportedEncodingException{
		System.out.print(MessageFormat.format("[{0}][Log] Application start.\n", new Date().toGMTString()));
		
		//Database
		//Initialization
		Database database = null;
		database = initializeDatabase(database);
	
		//View
		//JOrganizerView eventView = new JOrganizerView();
		MainWindowView mainWindowView = new MainWindowView();
		EventView eventView = new EventView();
		LoadView loadView = new LoadView();
		
		//Models
		OrganizerEvent mainEvent = new OrganizerEvent();
		
		mainWindowView.addListener(eventView);
	
		//Controller
		MainWindowViewController calendarController = new MainWindowViewController(database, mainWindowView, mainEvent);
		EventController mainEventController = new EventController(database, eventView, mainEvent);
		LoadController loadController = new LoadController(database, loadView, mainEvent);
	}
	/**
	 * Funkcja inicjalizuj¹ca bazê danych 
	 * @param database
	 * @return
	 */
	private static Database initializeDatabase(Database database) {
		// TODO Auto-generated method stub
		String root = "Events";
		String url = "jorganizer-46606";
		
		if (database == null) {
			database = new Database(url, root);
		}
		
		return database;
	}

}

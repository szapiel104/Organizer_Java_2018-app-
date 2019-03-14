package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.api.client.util.Strings;
import com.mindfusion.common.DateTime;

import enums.Organizer;
import model.OrganizerEvent;
import services.database.Database;
import services.guid.Guid;
import view.MainWindowView;
import view.EventView;

/**
 * LoadController - Klasa logiki/kontrolowania dodawania wydarzeñ.
 * @author Micha³ Szapiel
 *
 */
public class EventController {
	/* Classes */

	// View
	private EventView eventView;

	// Model
	private OrganizerEvent eventModel;

	// Services
	public Database database;

	// Scale frame
	public static int height;
	public static int width;

	/* Fields */
	private String url;

	public EventController(Database database, EventView meeting, OrganizerEvent eventModel) {
		try {
			this.database = database;
			this.eventView = meeting;
			this.eventModel = eventModel;

			// Add Button
			this.eventView.addButtonListener(new AddButtonListeners());

		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception"));
		}
	}

	// ActionListeners
	// Buttons
	// Add new event
	class AddButtonListeners implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				String guid = eventView.getGuid();
				Map<String, Object> item = getItem(guid);
				if(item != null) {
					String root = database.getRoot();
					String result = database.add(root, guid, item);
					//meeting.setStatus(result);
				}else {
					//meeting.setStatus("Data has not been inserted");
				}
			} catch (Exception ex) {
				System.out.println(MessageFormat.format("[{0}]{1}", new Date().toGMTString(), ex.getMessage()));
			}
		}

		private Map<String, Object> getItem(String guid) {
			try {
				String titleError = "Validation - failed";
				Map<String, Object> item = new HashMap<>();
				
				String date = eventView.getSelectedDate();
				String title = eventView.getTitle();
				String place = eventView.getPlace();
				String description = eventView.getDescription();
				String important = eventView.getImportantStatus() == true ? "YES" : "NO";
				String alarmOffset = eventView.getReminder();
				Boolean validationError = Validation(date, title, place, alarmOffset);
				if(!validationError) {
					//User provide all data
					item.put(Organizer.Guid.toString(), guid);
					item.put(Organizer.Date.toString(), date);
					item.put(Organizer.AlarmOffset.toString(), alarmOffset);
					item.put(Organizer.Title.toString(), title);
					item.put(Organizer.Place.toString(), place);
					item.put(Organizer.Description.toString(), description);
					item.put(Organizer.Important.toString(), important);
					
					return item;
				}else {
					eventView.showMessage(titleError, "Some fields are missing");
				}

				return null;

			} catch (Exception ex) {
				System.out.println(MessageFormat.format("[{0}][{1}][{2}]" + ex.getMessage(), new Date().toGMTString(),
						this.getClass().getSimpleName(), "Exception"));
			}
			return null;
		}

		private Boolean Validation(String date, String title, String place, String alarmOffset) {
			
			if (Strings.isNullOrEmpty(date) || Strings.isNullOrEmpty(title) || Strings.isNullOrEmpty(place) || Strings.isNullOrEmpty(alarmOffset)) {
				return true;
			}
			
			return false;
		}
	}

	// Getters & Setters
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}

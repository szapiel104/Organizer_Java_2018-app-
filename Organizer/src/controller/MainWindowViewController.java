package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Strings;
import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.Brushes;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;
import com.mindfusion.scheduling.model.ItemList;

import enums.Organizer;
import model.OrganizerEvent;
import services.appointment.AppointmentItem;
import services.database.Database;
import services.date.DateParser;
import services.guid.Guid;
import services.json.Json;
import view.EventView;
import view.MainWindowView;

/**
 * MainWindowViewController - G³ówna klasa logiki/kontrolowania g³ównego okna aplikacji.
 * @author Micha³ Szapiel
 *
 */
public class MainWindowViewController {
	/* Fields */
	// View
	private MainWindowView mainWindowView;
	// Model
	private OrganizerEvent eventModel;
	// Services
	public Database database;

	/* Fields */

	int width;
	int height;
	
	/**
	 * Przypisanie wszystkich wartoœci w³¹cznie z przyciskami.
	 * @author Micha³ Szapiel
	 *
	 */
	public MainWindowViewController(Database database, MainWindowView mainWindow, OrganizerEvent eventModel) {

		this.database = database;
		this.mainWindowView = mainWindow;
		this.eventModel = eventModel;

		// Buttons
		this.mainWindowView.addMeetingButtonAction(new AddMeetingButtonListeners());
		this.mainWindowView.deleteMeetingButtonAction(new DeleteMeetingButtonListeners());
		this.mainWindowView.editMeetingButtonAction(new EditMeetingButtonListeners());
		this.mainWindowView.updateMeetingButtonAction(new UpdateMeetingButtonListeners());

		// Bar buttons
		this.mainWindowView.exitButtonItemMenu(new AddExitButtonItemListeners());
		this.mainWindowView.creatorsButtonItemMenu(new AddCreatorsButtonItemListener());
		this.mainWindowView.resolutionButtonItemMenu(new AddResolutionButtonItemListener());
		
		getMeeting();
	}

	// Buttons
	// Add
	class AddMeetingButtonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainWindowView.openCalendarCreator();
		}
	}

	// Delete
	class DeleteMeetingButtonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			deleteMeeting();
		}
	}

	// Edit
	class EditMeetingButtonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);
		}
	}

	// Update
	class UpdateMeetingButtonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			getMeeting();
		}
	}

	// Bar buttons
	// Exit button
	class AddExitButtonItemListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);
		}

	}

	// Creators button
	class AddCreatorsButtonItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"SuroCrave Company" + "\n\b Version: 	1.0"
							+ "\n\b University: 	Wy¿sza Szko³a Informatyki i Umiejêtnoœci" + "\n\b Assets: [...]",
					"SuroCrave Company | Creators | Version 1.0", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	// Resolution callendar button
	class AddResolutionButtonItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			width = Integer.parseInt(JOptionPane.showInputDialog("[MIN 1000] Width:"));
			height = Integer.parseInt(JOptionPane.showInputDialog("[MIN 700] Height:"));
			mainWindowView.organizer.setSize(width, height);
		}
	}

	// Methods
	private void getMeeting() {
		try {
			this.mainWindowView.getCalendar().getSchedule().getItems().clear();
			String root = database.getRoot();
			List<Map<String, Object>> items = database.getAll(root);

			for (Iterator<Map<String, Object>> i = items.iterator(); i.hasNext();) {
				Map<String, Object> item = i.next();

				com.mindfusion.common.DateTime mindfusionDate = DateParser
						.ParseDate((String) item.get(Organizer.Date.toString()));
				if (mindfusionDate != null) {
					AppointmentItem appointmentItem = new AppointmentItem();
					Appointment calendarItem = appointmentItem.Create(item);
					this.mainWindowView.getCalendar().getSchedule().getItems().add(calendarItem);
				}
			}

		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}]{1}", new Date().toGMTString(), ex.getMessage()));
		}
	}
	
	private void deleteMeeting() {
		try {
			DateTime selectedDate = this.mainWindowView.getSelectedDate();
			if(selectedDate != null) {				
				ItemList selectedItem = this.mainWindowView.getCalendar().getSchedule().getAllItems();
				if(selectedItem != null) {
					for (Iterator<Item> i = selectedItem.iterator(); i.hasNext();) {
					    Item item = i.next();
					    Map<String, Object> convertedItem = Json.get(item.getDescriptionText());
					    DateTime time = DateParser.ParseDate(convertedItem.get(Organizer.Date.toString()).toString());
					    if(time.equals(selectedDate)) {
					    	String guid = (String) convertedItem.get(Organizer.Guid.toString());
					    	System.out.println("Item found: " + guid + " date: "  + convertedItem.get(Organizer.Date.toString()));
					    	
							String root = database.getRoot();
							String result = database.delete(root, guid, convertedItem);   
							
							System.out.println(result);
							
							if(!Strings.isNullOrEmpty(result)) {
								this.mainWindowView.getCalendar().remove(this.mainWindowView.getSelectedItemIndex());
								getMeeting();
							}
					    }
					}
				}
			}
			
			getMeeting();
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}]{1}", new Date().toGMTString(), ex.getMessage()));
		}
	}
}

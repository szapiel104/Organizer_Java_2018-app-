package services.appointment;

import java.util.Map;

import com.mindfusion.drawing.Brushes;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;

import enums.Organizer;
import services.database.Database;
import services.date.DateParser;
import services.guid.Guid;
import services.json.Json;

public class AppointmentItem {

	public Appointment Create(Map<String, Object> item) {
		try {

			/* Convert datetime from item */
			com.mindfusion.common.DateTime mindfusionDate = DateParser
					.ParseDate((String) item.get(Organizer.Date.toString()));
			if (mindfusionDate != null) {
				String guid = (String) item.get(Organizer.Guid.toString());
				String title = (String) item.get(Organizer.Title.toString());
				String description = (String) item.get(Organizer.Description.toString());
				String date = (String) item.get(Organizer.Date.toString());
				String important = (String) item.get(Organizer.Important.toString());
				String place = (String) item.get(Organizer.Place.toString());

				Appointment calendarItem = new Appointment();
				calendarItem.setStartTime(mindfusionDate);
				calendarItem.setEndTime(mindfusionDate);
				calendarItem.setHeaderText(title);

				String jsonDescription = Json.set(guid, title, description, date, important, place);
				calendarItem.setDescriptionText(jsonDescription);

				if (important.toLowerCase().compareTo("yes") == 0) {
					calendarItem.getStyle().setBrush(Brushes.Red);
				} else {
					calendarItem.getStyle().setBrush(Brushes.AliceBlue);
				}
				return calendarItem;
			}
		} catch (Exception ex) {

		}

		return null;
	}
}

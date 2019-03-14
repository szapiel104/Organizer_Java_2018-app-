package model;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

/**
 * OrganizerEvent - G³ówna klasa warstwy danych
 * @author Micha³ Szapiel
 *
 */
public class OrganizerEvent {
	
	private String title;
	
	private String place;
	
	private String description;
	
	private Boolean important;
	
	private String date;
	
	private String alarmOffset;
	
	public OrganizerEvent() {
		
	}
	/**
	 * S³owa kluczowe / instancja / konstruktor
	 * @author Micha³ Szapiel
	 *
	 */
	public OrganizerEvent(String title, String place, String description, Boolean important, String date, String alarmOffset) {
		this.title = title;
		this.place = place;
		this.description = description;
		this.important = important;
		this.date = date;
		this.alarmOffset = alarmOffset;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAlarmOffset() {
		return alarmOffset;
	}

	public void setAlarmOffset(String alarmOffset) {
		this.alarmOffset = alarmOffset;
	}
	
	//Getter & Setter
	//@overrdie
	//toString
	
	//to json
	
	//to Map <Object String>

}

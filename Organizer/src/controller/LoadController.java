package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.api.client.util.Strings;

import controller.EventController.AddButtonListeners;
import model.OrganizerEvent;
import services.database.Database;
import services.xml.XMLParser;
import view.EventView;
import view.LoadView;

/**
 * LoadController - Klasa logiki/kontrolowania zapisu do XML.
 * @author Micha³ Szapiel
 *
 */
public class LoadController {
	// View
	private LoadView loadView;

	// Model
	private OrganizerEvent eventModel;

	// Services
	public Database database;

	public LoadController(Database database, LoadView loadView, OrganizerEvent eventModel) {
		this.database = database;
		this.loadView = loadView;
		this.eventModel = eventModel;
		
		// Add Button
		this.loadView.browseButtonListener(new BrowseButtonListeners());
		this.loadView.importButtonListener(new ImportButtonListener());
	}
	
	public class BrowseButtonListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                "XML files", "xml");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            String path = chooser.getSelectedFile().getPath();
	        	loadView.setFileLocationTextField(path);
	        	
	        	System.out.println("You chose to open this file: " +
	                    chooser.getSelectedFile().getName());
	        }
		}

	}
	
	public class ImportButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String path = loadView.getFileLocationTextField();
			if(!Strings.isNullOrEmpty(path)) {
				XMLParser xml = new XMLParser();
				File selectedFile = new File(path);
				List<Map<String, Object>> items = xml.ParseFromFile(selectedFile);
			}else { 
				loadView.showMessage("Error", "Field path is missing");
			}
		}
	}
}

package services.database;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.OrganizerEvent;
import services.database.firestoredatabase.FirestoreDatabase;

public class Database {
	//Fields
	private FirestoreDatabase database;
	
	private String projectId;
	private String root;
	private String guid;

	public Database(String projectId, String root) {
		this.projectId = projectId;
		this.root = root;
		
		if(database == null) {
			database = new FirestoreDatabase(projectId);
		}
	}
	
	public String add(String root, String guid, Map<String, Object> item) {
		// TODO Auto-generated method stub
		return database.add(root, guid, item);
	}

	public String delete(String root, String guid, Map<String, Object> item) {
		// TODO Auto-generated method stub
		return database.delete(root, guid, item);
	}

	public List<Map<String, Object>> getAll(String root) {
		// TODO Auto-generated method stub
		return database.getAll(root);
	}

	
	//Getters & Setters
	public FirestoreDatabase getDatabase() {
		return database;
	}

	public void setDatabase(FirestoreDatabase database) {
		this.database = database;
	}
	
	public String getProjectId() {
		return projectId;
	}

	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
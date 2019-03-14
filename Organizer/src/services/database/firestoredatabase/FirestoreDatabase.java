/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services.database.firestoredatabase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
// [START fs_include_dependencies]
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
// [END fs_include_dependencies]
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import enums.Organizer;
import model.OrganizerEvent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.mindfusion.scheduling.model.Task;

public class FirestoreDatabase {
	// Fields
	public Firestore db;

	// Getters & Setters
	public Firestore getDb() {
		return db;
	}

	public void setDb(Firestore db) {
		this.db = db;
	}

	/**
	 * Initialize Firestore using default project ID.
	 */
	public FirestoreDatabase() {
		// [START fs_initialize]
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		// [END fs_initialize]
		this.db = db;
	}

	public FirestoreDatabase(String projectId) {
		// [START fs_initialize_project_id]
		try {
			InputStream serviceAccount = new FileInputStream("src/client.json");
			GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId(projectId)
					.build();
			FirebaseApp.initializeApp(options);
			Firestore db = FirestoreClient.getFirestore();
			// [END fs_initialize_project_id]
			this.db = db;
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception",ex.getStackTrace()[0].getLineNumber(), Thread.currentThread().getStackTrace()));
		}
	}

	public String add(String root, String guid, Map<String, Object> item) {
		String resultOperation = "";
		try {
			DocumentReference docRef = db.collection(root).document(guid);

			// asynchronously write data
			ApiFuture<WriteResult> result = docRef.set(item);
			// ...
			// result.get() blocks on response
			resultOperation = ("Insert time : " + result.get().getUpdateTime());
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception",ex.getStackTrace()[0].getLineNumber(), Thread.currentThread().getStackTrace()));
		}
		return resultOperation;
	}

	public String delete(String root, String guid, Map<String, Object> item) {
		String resultOperation = "";
		try {
			int batchSize = 1000;
			String field = Organizer.Guid.toString();
			// retrieve a small batch of documents to avoid out-of-memory errors
		    ApiFuture<QuerySnapshot> future = db.collection(root).limit(batchSize).get();
		    int deleted = 0;
		    // future.get() blocks on document retrieval
		    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		    for (QueryDocumentSnapshot document : documents) {
		    	System.out.println(document.get(field));
		    	if(document.get(field).toString().compareTo(guid) == 0) {
		    		document.getReference().delete();
			    	System.out.println("Deleted");
		    		break;
		    	}
		    	 ++deleted;
		      }
		    
//			//DocumentReference docRef = db.collection(root).whereEqualTo("Guid", guid).
//			CollectionReference itemsRef = db.collection(root);
//			Query query = itemsRef.whereEqualTo("Guid", guid);
//			
//			query.get().addListener(new OnCompleteListener<QuerySnapshot>() {
//			    @Override
//			    public void onComplete(Task<QuerySnapshot> task) {
//			        if (task.isSuccessful()) {
//			            for (DocumentSnapshot document : task.getResult()) {
//			                itemsRef.document(document.getId()).delete();
//			            }
//			        } else {
//			            System.out.println("Error getting documents: ", task.getException());
//			        }
//			    }
//			});
					
					
			
//			Query query = db.collection("test").whereEqualTo("Guid", guid);
//			System.out.println(query);
			//System.out.println(documentId.);
			
			// asynchronously delete a document
			//ApiFuture<WriteResult> writeResult = db.collection(root).document("Dupa").delete();
			
			// ...
			//resultOperation = ("Delete time : " + writeResult.get().getUpdateTime());
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception",ex.getStackTrace()[0].getLineNumber(), Thread.currentThread().getStackTrace()));
		}
		return resultOperation;
	}

	public List<Map<String, Object>> getAll(String root) {
		List<Map<String, Object>>  result = new ArrayList<Map<String, Object>>();
		try {
			// asynchronously retrieve all users
			ApiFuture<QuerySnapshot> query = db.collection(root).get();

			// query.get() blocks on response
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				String guid = Organizer.Guid.toString();
				String title = Organizer.Title.toString();
				String place = Organizer.Place.toString();
				String description = Organizer.Description.toString();
				String important = Organizer.Important.toString();
				String date = Organizer.Date.toString();
				String alarmOffset = "NOT YET";
				
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put(Organizer.Guid.toString() , document.getString(guid));
				item.put(Organizer.Title.toString() , document.getString(title));
				item.put(Organizer.Place.toString() , document.getString(place));
				item.put(Organizer.Description.toString() , document.getString(description));
				item.put(Organizer.Important.toString() , document.getString(important));
				item.put(Organizer.Date.toString() , document.getString(date));
				item.put(Organizer.AlarmOffset.toString() , document.getString(alarmOffset));
				
				System.out.println("--------------------------------");
				System.out.println(Organizer.Guid.toString() + " - " + document.getString(guid));
				System.out.println(Organizer.Title.toString() + " - " + document.getString(title));
				System.out.println(Organizer.Place.toString()  + " - " + document.getString(place));
				System.out.println(
						Organizer.Description.toString()  + " - " + document.getString(description));
				System.out.println(Organizer.Important.toString()  + " - " + document.getString(important));
				System.out.println(Organizer.Date.toString()  + " - " + document.getString(date));
				System.out.println(
					Organizer.AlarmOffset.toString()  + " - " + document.getString(alarmOffset));
				System.out.println("--------------------------------");
				
				result.add(item);
			}
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception",ex.getStackTrace()[0].getLineNumber(), Thread.currentThread().getStackTrace()));
		}
		
		return result;
	}

	public void getItem(String root, String guid) {
		// asynchronously get data
		try {
			// asynchronously retrieve all users
			ApiFuture<DocumentSnapshot> query = db.collection(root).document(guid).get();

			DocumentSnapshot querySnapshot = query.get();
			List<DocumentSnapshot> documents = (List<DocumentSnapshot>) querySnapshot.getData();
			for (DocumentSnapshot document : documents) {
				System.out.println(Organizer.Title.toString() + document.getString(Organizer.Title.toString()));
				System.out.println(Organizer.Place.toString() + document.getString(Organizer.Place.toString()));
				System.out.println(
						Organizer.Description.toString() + document.getString(Organizer.Description.toString()));
				System.out.println(Organizer.Important.toString() + document.getString(Organizer.Important.toString()));
				System.out.println(Organizer.Date.toString() + document.getString(Organizer.Date.toString()));
				System.out.println(
						Organizer.AlarmOffset.toString() + document.getString(Organizer.AlarmOffset.toString()));
			}

		} catch (InterruptedException | ExecutionException ex) {
			// TODO Auto-generated catch block
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception",ex.getStackTrace()[0].getLineNumber(), Thread.currentThread().getStackTrace()));
		}
	}

}

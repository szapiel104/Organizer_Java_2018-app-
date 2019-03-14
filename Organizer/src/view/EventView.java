package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.common.base.Strings;
import com.mindfusion.common.DateTime;

import interfaces.EventWindowListener;
import services.guid.Guid;
/**
 * EventView - W klasie zawiera siê interfejs/widok/GUI dodawania wydarzeñ.
 * @author Micha³ Szapiel
 *
 */
public class EventView  extends JFrame implements EventWindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Labels */
	//Guid
	private JLabel guidLabel = new JLabel("Guid:");
	//Date from Calendar
	private JLabel selectedDateLabel = new JLabel("Selected Date:");
	//Title of meeting
	private JLabel titleLabel = new JLabel("Title:");
	//Place of meeting
	private JLabel placeLabel = new JLabel("Label:");
	//Alarm of meeting
	private JLabel reminderLabel = new JLabel("Reminder me (min):");
	//Important
	private JLabel importantLabel = new JLabel("Important:");
	//Description of meeting
	private JLabel descriptionLabel = new JLabel("Description:");
	
	/* Fields */
	//Guid
	private JTextField guidTextField = new JTextField();
	//Date from Calendar
	private JTextField selectedDateTextField = new JTextField();
	//Title of meeting
	private JTextField titleTextField = new JTextField();
	//Place of meeting
	private JTextField placeTextField  = new JTextField();
	//Alarm of meeting
	private JTextField reminderTextField  = new JTextField();

	/* Text area */
	private JTextArea descriptionTextArea = new JTextArea();

	/* Buttons */
	private JButton addButton = new JButton("Add");

	// RadioButton
	JRadioButton importantRadioButton;

	/* Helper fields */
	private Font myFont;
	private String text;
	private String selectedDate;
	

	public EventView() {
		super("EventView");
		mainInitialize();
	}

	private void mainInitialize() {
		this.setTitle("Event Creator");
		this.myFont = new Font("Consolas", Font.BOLD, 14);
		this.setVisible(false);
		// Information about date
		initializeCalendarField();

		// Fields
		initializeGuidField();
		initializeTitleField();
		initializePlaceField();

		// Radio button
		initializeImportantRadioButton();
		initializeLayout();
	
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(600, 350));
		pack();
		
	}

	// Fields
	//Guid
	private void initializeCalendarField() {
		selectedDateLabel = new JLabel("Selected date");
		selectedDateLabel.setFont(this.myFont);

		selectedDateTextField = new JTextField();
		this.selectedDateTextField.setText(this.selectedDate);
		this.selectedDateTextField.setFont(myFont);
		this.selectedDateTextField.setEditable(false);
	}
	
	private void initializeGuidField() {
		this.guidLabel.setFont(this.myFont);
		this.guidTextField.setFont(this.myFont);
		
		this.guidTextField.setEditable(false);
	}
	
	private void initializeTitleField() {
		this.text = "title...";

		titleLabel = new JLabel("Title");
		titleLabel.setFont(this.myFont);

		this.titleTextField = new JTextField();
		this.titleTextField.setText(this.text);
		this.titleTextField.setFont(myFont);
	}

	private void initializePlaceField() {
		this.text = "title...";

		placeLabel = new JLabel("Place");
		placeLabel.setFont(this.myFont);

		this.placeTextField = new JTextField();
		this.placeTextField.setText(this.text);
		this.placeTextField.setFont(myFont);
	}

	// Important
	// Radio Button
	private void initializeImportantRadioButton() {
		this.importantRadioButton = new JRadioButton();
	}

	// Layout
	private void initializeLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addGap(10)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
											.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
													.addComponent(guidLabel)
													.addComponent(selectedDateLabel)
													.addComponent(titleLabel)
													.addComponent(placeLabel)
													.addComponent(reminderLabel)
													.addComponent(importantLabel)
													.addComponent(descriptionLabel)
										)
						.addGap(15)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(guidTextField)
								.addComponent(selectedDateTextField)
								.addComponent(titleTextField)
								.addComponent(placeTextField)
								.addComponent(reminderTextField)
								.addComponent(importantRadioButton)
								.addComponent(descriptionScrollPane)
								.addGroup(layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(addButton)
										.addGap(10))
										)))
				.addGap(10)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup()
										//Guid
										.addGap(20)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(guidLabel)
												.addComponent(guidTextField))
										//Selected Date
										.addGap(20)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(selectedDateLabel)
												.addComponent(selectedDateTextField))
										
										//Title
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(titleLabel)
												.addComponent(titleTextField))
										
										//Place
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(placeLabel)
												.addComponent(placeTextField))
										//Reminder
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(reminderLabel)
												.addComponent(reminderTextField))
										//Important radio button
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(importantLabel)
												.addComponent(importantRadioButton))
										
										//Description
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(descriptionLabel)
												.addComponent(descriptionScrollPane, 20, 60, Short.MAX_VALUE))
										//Buttons
										.addGap(15)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(addButton)
										.addGap(15)
										
									)
							)
					);
												
		layout.linkSize(addButton);

		getRootPane().setDefaultButton(addButton);
	}

	// Listeners

	
	// Buttons
	// Add new event
	public void addButtonListener(ActionListener addButtonListeners) {
		this.addButton.addActionListener(addButtonListeners);
	}
	
	//Getters & Setters
	public String getGuid() {
		return guidTextField.getText();
	}

	public void setGuid(String guid) {
		this.guidTextField.setText(guid);
	}
	
	public String getSelectedDate() {
		return selectedDateTextField.getText();
	}

	public void setSelectedDate(DateTime selectedDateTextField) {
		if(!Strings.isNullOrEmpty(selectedDateTextField.toString())) {
			this.selectedDateTextField.setText(selectedDateTextField.toString());
		}
	}

	public String getTitle() {
		return titleTextField.getText();
	}

	public void setTitle(String titleTextField) {
		this.titleTextField.setText(titleTextField);
	}

	public String getPlace() {
		return placeTextField.getText();
	}

	public void setPlace(String placeTextField) {
		this.placeTextField.setText(placeTextField);
	}

	public String getReminder() {
		return reminderTextField.getText();
	}

	public void setReminder(String reminderTextField) {
		this.reminderTextField.setText(reminderTextField);
	}


	public String getDescription() {
		return descriptionTextArea.getText();
	}

	public void setDescription(String descriptionTextArea) {
		this.descriptionTextArea.setText(descriptionTextArea);
	}
	
	public Boolean getImportantStatus() {
		return importantRadioButton.isSelected();
	}
	
	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	private void GetGuid() {
		this.guidTextField.setText(Guid.get());
	}

	@Override
	public void onOpen(DateTime longDateString) {
		// TODO Auto-generated method stub
		GetGuid();
		this.setVisible(true);
		this.setSelectedDate(longDateString);
	}
}

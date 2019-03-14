package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Event2 - W klasie zawiera siê interfejs/widok/GUI dodawania wydarzeñ [Wy³¹czone].
 * @author Micha³ Szapiel
 *
 */
public class Event2 extends JFrame {

	/* Labels */
	private JLabel selectedDateLabel = new JLabel("Selected Date:");
	private JLabel titleLabel = new JLabel("Title:");
	private JLabel placeLabel = new JLabel("Label:");
	private JLabel importantLabel = new JLabel("Important:");
	private JLabel descriptionLabel = new JLabel("Description:");

	/* Fields */
	private JTextField selectedDateTextField;
	private JTextField titleTextField;
	private JTextField placeTextField;

	/* Text area */
	private JTextArea descriptionTextArea;

	/* Buttons */
	private JButton addButton;
	private JButton deleteButton;

	// RadioButton
	JRadioButton importantRadioButton;

	/* Helper fields */
	private Font myFont;
	private String text;
	private String selectedDate;

//	private JTextField fullNameTextField = new JTextField();
//	private JTextField emailIDTextField = new JTextField();
//	private JTextArea addressTextArea = new JTextArea();
	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");

	public Event2() {
		new Event2().setVisible(true);
		mainInitialize();
	}

	private void mainInitialize() {
		this.myFont = new Font("Consolas", Font.BOLD, 14);

		// Information about date
		initializeCalendarField();

		// Fields
		initializeTitleField();
		initializePlaceField();

		// Radio button
		initializeImportantRadioButton();

		// Buttons
		initializeAddButton();

		initializeLayout();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);

		setPreferredSize(new Dimension(600, 600));

		pack();
	}

	// Fields
	private void initializeCalendarField() {
		selectedDateLabel = new JLabel("Selected date");
		selectedDateLabel.setFont(this.myFont);

		selectedDateTextField = new JTextField();
		this.selectedDateTextField.setText(this.selectedDate);
		this.selectedDateTextField.setColumns(10);
		this.selectedDateTextField.setFont(myFont);
		this.selectedDateTextField.setEditable(false);
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
		this.importantRadioButton.setBounds(10, 340, 104, 24);
	}

	// Buttons
	private void initializeAddButton() {
		this.text = "ADD";
		this.addButton = new JButton(this.text);
		this.addButton.setBounds(288, 344, 92, 28);
	}


	// Layout
	private void initializeLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(10)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(selectedDateLabel).addComponent(titleLabel).addComponent(placeLabel))
						.addGap(15)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(descriptionLabel).addComponent(descriptionTextArea)
								.addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(submitButton).addGap(10).addComponent(cancelButton)))))
				.addGap(10)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addGap(10)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(selectedDateLabel).addComponent(selectedDateTextField))
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(titleLabel).addComponent(titleTextField))
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(placeLabel).addComponent(placeTextField))
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(importantLabel).addComponent(importantRadioButton))
										.addGap(5)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(descriptionLabel)
												.addComponent(descriptionTextArea, 20, 60, Short.MAX_VALUE))
										.addGap(15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(submitButton).addComponent(cancelButton))
										.addGap(10)));

		layout.linkSize(submitButton, cancelButton);

		getRootPane().setDefaultButton(submitButton);
	}

	// Listeners

	// Buttons
	// Add new event
	public void addButtonListener(ActionListener addButtonListeners) {
		this.addButton.addActionListener(addButtonListeners);
	}

	//Getters & Setters
	
	public Boolean getImportantStatus() {
		return importantRadioButton.isSelected();
	}
	
	public JTextField getSelectedDateTextField() {
		return selectedDateTextField;
	}

	public void setSelectedDateTextField(JTextField selectedDateTextField) {
		this.selectedDateTextField = selectedDateTextField;
	}

	public JTextField getTitleTextField() {
		return titleTextField;
	}

	public void setTitleTextField(JTextField titleTextField) {
		this.titleTextField = titleTextField;
	}

	public JTextField getPlaceTextField() {
		return placeTextField;
	}

	public void setPlaceTextField(JTextField placeTextField) {
		this.placeTextField = placeTextField;
	}

	public JTextArea getDescriptionTextArea() {
		return descriptionTextArea;
	}

	public void setDescriptionTextArea(JTextArea descriptionTextArea) {
		this.descriptionTextArea = descriptionTextArea;
	}

	public JRadioButton getImportantRadioButton() {
		return importantRadioButton;
	}

	public void setImportantRadioButton(JRadioButton importantRadioButton) {
		this.importantRadioButton = importantRadioButton;
	}

	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
}

package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * LoadView - W klasie zawiera siê interfejs/widok/GUI zapisywania. [Wy³¹czone]
 * @author Micha³ Szapiel
 *
 */
public class LoadView extends JFrame {

	/* Description: Load Window */
	// Fields
	private JLabel fileTextLabel;

	private JTextField fileLocationTextField;

	private JButton browseButton;
	private JButton importFromFileButton;
	private JButton saveButton;

	private JTable tableWithDataFromFile;

	public LoadView() {
		mainInitialize();
	}

	private void mainInitialize() {
		// TODO Auto-generated method stub
		this.setTitle("Load file");

		this.fileTextLabel = new JLabel("File: ");

		this.fileLocationTextField = new JTextField();
		this.fileLocationTextField.setEditable(false);

		this.browseButton = new JButton("Browse");
		this.importFromFileButton = new JButton("Import");
		this.saveButton = new JButton("Save");
		this.tableWithDataFromFile = new JTable();
		this.tableWithDataFromFile
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		initializeLayout();

		this.setPreferredSize(new Dimension(600, 750));
		this.setResizable(false);
		this.setLocationByPlatform(true);
		//this.setVisible(true);
		pack();
	}

	private void initializeLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(10)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(fileTextLabel))
						.addGap(15)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(fileLocationTextField)
								.addGap(10)
								.addGroup(layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE).addComponent(browseButton).addGap(15)
										.addGap(0, 0, Short.MAX_VALUE).addComponent(importFromFileButton).addGap(15)
								))))
				.addGap(10)));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				// Selected Date
				.addGap(20)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fileTextLabel)
						.addComponent(fileLocationTextField))
				// Buttons
				.addGap(15)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(browseButton))
						.addComponent(importFromFileButton))
				.addGap(15));

		layout.linkSize(browseButton, importFromFileButton);

		getRootPane().setDefaultButton(browseButton);
	}

	// Buttons
	// Add new event
	public void browseButtonListener(ActionListener browseButtonListeners) {
		this.browseButton.addActionListener(browseButtonListeners);
	}
	
	public void importButtonListener(ActionListener importButtonListeners) {
		this.importFromFileButton.addActionListener(importButtonListeners);
	}
	
	//Getters & Setters
	public String getFileLocationTextField() {
		return fileLocationTextField.getText();
	}

	public void setFileLocationTextField(String fileLocationText) {
		this.fileLocationTextField.setText(fileLocationText);
	}
	
	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
}

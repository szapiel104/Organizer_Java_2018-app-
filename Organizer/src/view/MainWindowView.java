package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.Brush;
import com.mindfusion.drawing.Brushes;
import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.CalendarListener;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.CustomDrawElements;
import com.mindfusion.scheduling.DrawEvent;
import com.mindfusion.scheduling.ItemSelection;
import com.mindfusion.scheduling.ResourceDateEvent;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.DaysOfWeek;
import com.mindfusion.scheduling.model.Item;
import com.mindfusion.scheduling.model.Recurrence;

import interfaces.EventWindowListener;

/**
 * MainWindowView - W klasie zawiera siê ca³y interfejs/widok/GUI.
 * @author Micha³ Szapiel
 *
 */
public class MainWindowView extends JFrame {

	/* Fields */
	private static final long serialVersionUID = 1L;
	private List<EventWindowListener> listeners = new ArrayList<EventWindowListener>();

	public JFrame organizer;
	private AwtCalendar calendar;
	private DateTime selectedDate;
	private int selectedItemIndex;

	private Recurrence recurrence;

	// Label
	private JLabel label;

	// Menu Bar
	private JMenuBar optionMenuBar;

	// Buttons
	private JButton addMeetingButton;
	private JButton deleteMeetingButton;
	private JButton editMeetingButton;
	private JButton updateMeetingButton;

	// Menu
	private JMenu startMenu;
	private JMenu preferencesMenu;
	private JMenu aboutMenu;

	// Menu item
	private JMenuItem saveMenuItemButton;
	private JMenuItem newMenuItemButton;
	private JMenuItem loadMenuItemButton;
	private JMenuItem exitMenuItemButton;
	private JMenuItem resolutionMenuItemButton;
	private JMenuItem creatorsMenuItemButton;

	// Frame
	public EventView meeting;

	private String text;

	/**
	 * Konstruktor.
	 * @author Micha³ Szapiel
	 *
	 */
	// Constructor
	public MainWindowView() {
		try {
			mainInitialize();
		} catch (Exception message) {
			System.out.println(message.getMessage());
			JOptionPane.showMessageDialog(organizer, message.getMessage(), "Initialize Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Inicjalizacja ogólna.
	 * Dokumenty poni¿ej znajdziemy inicjalizacjê dla ka¿dego obiektu, elementu interfejsu.
	 * @author Micha³ Szapiel
	 *
	 */
	private void mainInitialize() {
		try {
			initializeWindow();
			initializeMenuBar();
			initializeCalendar();
			initializeOperationButtonsl();
		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}]" + ex.getMessage(), new Date().toGMTString(),
					this.getClass().getSimpleName(), "Exception"));
		}
	}

	/* Initialization */
	// Window
	private void initializeWindow() {
		this.text = "Organizer | Alpha Version";
		this.organizer = new JFrame();
		this.organizer.setTitle(text);
		this.organizer.setMinimumSize(new Dimension(1000, 700));
		this.organizer.setSize(new Dimension(1000, 700));
		this.organizer.setExtendedState(NORMAL);
		this.organizer.setResizable(false);
		this.organizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.organizer.set

		this.organizer.getContentPane().setLayout(null);
	}

	private void initializeOperationButtonsl() {
		JPanel buttons = new JPanel();

		this.text = "Add meeting";
		this.addMeetingButton = new JButton(this.text);
		buttons.add(this.addMeetingButton, BorderLayout.NORTH);

		this.text = "Delete meeting";
		this.deleteMeetingButton = new JButton(this.text);
		buttons.add(this.deleteMeetingButton, BorderLayout.NORTH);

		this.text = "Edit meeting";
		this.editMeetingButton = new JButton(this.text);
		buttons.add(this.editMeetingButton, BorderLayout.NORTH);

		this.text = "Update meeting";
		this.updateMeetingButton = new JButton(this.text);
		buttons.add(this.updateMeetingButton, BorderLayout.NORTH);

		this.organizer.getContentPane().add(buttons, BorderLayout.NORTH);
	}

	// Calendar
	private void initializeCalendar() {
		BorderLayout layout = new BorderLayout();
		this.organizer.getContentPane().setLayout(layout);

		// Calendar initialization start
		this.calendar = new AwtCalendar();
		calendar.beginInit();
		// set the current time
		calendar.setCurrentTime(DateTime.now());
		DateTime today = DateTime.today();
		// set the current date
		calendar.setDate(today);
		// Select the current date
		calendar.getSelection().set(DateTime.today());

		calendar.setCurrentView(CalendarView.SingleMonth);
		calendar.setCustomDraw(CustomDrawElements.CalendarItem);
		calendar.getMonthSettings().getDaySettings().setHeaderSize(20);
		calendar.getItemSettings().setSize(32);
		calendar.endInit();

		 calendar.addCalendarListener(new CalendarAdapter(){
	            public void dateClick(ResourceDateEvent e) {
	                onDateClicked(e);
	            }

	        });
		 
		this.organizer.getContentPane().add(calendar, BorderLayout.CENTER);
		// arrange the calendar
		// getContentPane().add(calendar, BorderLayout.CENTER);

		this.organizer.pack();
		this.organizer.setVisible(true);
	}

	protected void onDateClicked(ResourceDateEvent e) {
		this.selectedDate = e.getDate();
		this.selectedItemIndex = e.getIndex();
	}

	// Menu Bar
	private void initializeMenuBar() {
		optionMenuBar = new JMenuBar();

		// 1-st option: Start
		this.text = "Start";
		this.startMenu = new JMenu(text);

		// 1-st option: Start - menu Items
		this.text = "Save";
		this.saveMenuItemButton = new JMenuItem(text);
		saveMenuItemButton.setIcon(
				new ImageIcon(MainWindowView.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));

		this.text = "New";
		this.newMenuItemButton = new JMenuItem(text);
		newMenuItemButton.setIcon(
				new ImageIcon(MainWindowView.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));

		this.text = "Load";
		this.loadMenuItemButton = new JMenuItem(text);
		loadMenuItemButton.setIcon(new ImageIcon(MainWindowView.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Bullets-rtl.png")));

		this.text = "Exit";
		this.exitMenuItemButton = new JMenuItem(text);
		exitMenuItemButton.setIcon(new ImageIcon(
				MainWindowView.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaVolumeThumb.png")));

		this.startMenu.add(this.saveMenuItemButton);
		// this.startMenu.add(this.newMenuItemButton);
		this.startMenu.add(this.loadMenuItemButton);
		this.startMenu.add(this.exitMenuItemButton);

		this.optionMenuBar.add(startMenu);

		// 2-st option: Preferences
		this.text = "Preferences";
		this.preferencesMenu = new JMenu(text);

		// 2-st option: Preferences - menu items
		this.text = "Resolution Callendar";
		this.resolutionMenuItemButton = new JMenuItem(text);
		this.resolutionMenuItemButton.setIcon(new ImageIcon(
				MainWindowView.class.getResource("/com/toedter/calendar/images/JDateChooserColor32.gif")));

		this.preferencesMenu.add(this.resolutionMenuItemButton);
		this.optionMenuBar.add(preferencesMenu);

		// 3-st option: About
		this.text = "About";
		this.aboutMenu = new JMenu(text);

		// 3-st option: menu items
		this.text = "Creators";
		this.creatorsMenuItemButton = new JMenuItem(text);

		this.aboutMenu.add(this.creatorsMenuItemButton);
		this.optionMenuBar.add(aboutMenu);

		this.organizer.setJMenuBar(this.optionMenuBar);
	}

	// Buttons
	private void initializeUpdateButton() {

	}
	
	/* Getters & Setters */
	public AwtCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(AwtCalendar calendar) {
		this.calendar = calendar;
	}
	
	public DateTime getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(DateTime selectedDate) {
		this.selectedDate = selectedDate;
	}
	
	public int getSelectedItemIndex() {
		return selectedItemIndex;
	}

	public void setSelectedItemIndex(int selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}

	/* Private methods */

	/*
	 * Listeners
	 */
	// Calendar
	public void addMeetingButtonAction(ActionListener addButton) {
		this.addMeetingButton.addActionListener(addButton);
	}

	public void deleteMeetingButtonAction(ActionListener deleteButton) {
		this.deleteMeetingButton.addActionListener(deleteButton);
	}

	public void editMeetingButtonAction(ActionListener editButton) {
		this.editMeetingButton.addActionListener(editButton);
	}

	public void updateMeetingButtonAction(ActionListener updateButton) {
		this.updateMeetingButton.addActionListener(updateButton);
	}

	// Buttons
	public void exitButtonItemMenu(ActionListener exitButtonItem) {
		this.exitMenuItemButton.addActionListener(exitButtonItem);
	}

	public void creatorsButtonItemMenu(ActionListener creatorsButtonItem) {
		this.creatorsMenuItemButton.addActionListener(creatorsButtonItem);
	}

	public void resolutionButtonItemMenu(ActionListener resolutionButtonItem) {
		this.resolutionMenuItemButton.addActionListener(resolutionButtonItem);
	}
	
	public void addListener(EventWindowListener toAdd) {
        listeners.add(toAdd);
    }

    public void openCalendarCreator() {
        // Notify everybody that may be interested.
    	
        for (EventWindowListener hl : listeners)
            hl.onOpen(this.selectedDate);
    }
}

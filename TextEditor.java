package labs.lab9;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;



public class TextEditor extends JFrame {
	
	private static final int frameWidth = 900;
	private static final int frameHeight = 600;
	
	private JPanel tempPanel;
	
	private JPanel text_format_Panel;
	private JPanel formattingPanel;
	private JPanel clearPanel;
	private JFrame mainFrame;
	private JPanel fontPanel;
	private JPanel stylePanel;
	private JPanel sizePanel;
	private ActionListener listener;
	private JTextArea userText;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JComboBox<String> fontDropdown;
	private JCheckBox italic;
	private JCheckBox bold;
	private Stack<ArrayList<Object>> undoStack;
	private Stack<ArrayList<Object>> redoStack;
	
	private JRadioButton button1;
	private JRadioButton button2;
	private JRadioButton button3;
	private JRadioButton button4;
	private JRadioButton button5;
	
	
	private Font font;
	private int fontSize;
	private String fontType;
	private int fontStyle;
	private volatile boolean isSetupGUI = false;
	
	public static void main(String args[]) {
		TextEditor trial = new TextEditor();
	}
	
	public TextEditor() {

		this.listener = new ButtonListener();
		
		createMain();
		createMenu();
		createPanels();

		
		//Should also set size in frame constructor
		setSize(frameWidth, frameHeight);
		
		this.undoStack = new Stack<ArrayList<Object>>();
		this.redoStack = new Stack<ArrayList<Object>>();
	}
	
	private void createMain() {
		this.mainFrame = new JFrame();
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setTitle("Emily Lee - 94863973");		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new BorderLayout());
		
		
		
	}
	
	private void createMenu() {
		this.menuBar = new JMenuBar();
		this.menuBar.setSize(frameWidth, 50);
		
		this.fileMenu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		
		this.editMenu = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		editMenu.add(undo);
		editMenu.add(redo);
		
		undo.addActionListener(listener);
		redo.addActionListener(listener);
		exit.addActionListener(listener);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		mainFrame.add(this.menuBar, BorderLayout.NORTH);

	}
	
	private void createPanels() {
		this.formattingPanel = new JPanel();
		formattingPanel.setLayout(new GridLayout(3,1));
		
		this.text_format_Panel = new JPanel();
		text_format_Panel.setLayout(new GridLayout(2,1));
		
		
		this.fontPanel = new JPanel();
		createComboBox();
		this.stylePanel = new JPanel();
		this.stylePanel.setLayout(new FlowLayout());
		createCheckbox();
		this.sizePanel = new JPanel();
		this.sizePanel.setLayout(new FlowLayout());
		createRadioButton();
		
		createTextField();
		
		this.clearPanel = new JPanel();
		createClear();
		
		fontPanel.setSize(frameWidth, frameHeight);
		
		formattingPanel.add(fontPanel);
		formattingPanel.add(stylePanel);
		formattingPanel.add(sizePanel);
		
		
		text_format_Panel.add(formattingPanel);
		text_format_Panel.add(tempPanel);
		mainFrame.add(text_format_Panel, BorderLayout.CENTER);
		mainFrame.add(clearPanel, BorderLayout.SOUTH);
	}

	
	private void createComboBox() {
		this.fontDropdown = new JComboBox();
		fontDropdown.addItem("Serif");;
		fontDropdown.addItem("SansSerif");
		fontDropdown.addItem("Monospaced");
		fontDropdown.setEditable(false);
		fontDropdown.addActionListener(listener);
		
		fontPanel.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
		
		fontPanel.add(fontDropdown);
	}
	
	private void createCheckbox() {
		this.italic = new JCheckBox("Italic");
		this.bold = new JCheckBox("Bold");
		italic.addActionListener(listener);
		bold.addActionListener(listener);
		
		stylePanel.add(italic);
		stylePanel.add(bold);
		stylePanel.setBorder(new TitledBorder(new EtchedBorder(), "Style"));
	}
	
	private void createRadioButton() {
		this.button1 = new JRadioButton("8 pt.");
		this.button2 = new JRadioButton("16 pt.");
		this.button3 = new JRadioButton("24 pt.");
		this.button4 = new JRadioButton("32 pt.");
		this.button5 = new JRadioButton("40 pt.");		
		
		this.button1.addActionListener(listener);
		this.button2.addActionListener(listener);
		this.button3.addActionListener(listener);
		this.button4.addActionListener(listener);
		this.button5.addActionListener(listener);
		
		this.button2.setSelected(rootPaneCheckingEnabled);  //set as default
		
		ButtonGroup group = new ButtonGroup();
		group.add(this.button1);
		group.add(this.button2);
		group.add(this.button3);
		group.add(this.button4);
		group.add(this.button5);
		
		sizePanel.add(this.button1);
		sizePanel.add(this.button2);
		sizePanel.add(this.button3);
		sizePanel.add(this.button4);
		sizePanel.add(this.button5);		
		
		sizePanel.setBorder(new TitledBorder(new EtchedBorder(), "Size"));		


	}
	
	private void createTextField() {
		this.tempPanel = new JPanel();
		this.tempPanel.setLayout(new BorderLayout());
		
		this.userText = new JTextArea();
		this.userText.setLineWrap(true);
		this.userText.setBorder(new TitledBorder(new EtchedBorder()));
		this.userText.setEditable(true);

		JScrollPane scrollPane = new JScrollPane(this.userText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.fontSize = 16;
		this.fontType = "Serif";
		this.fontStyle = Font.PLAIN;
		this.font = new Font(fontType, fontStyle, fontSize);
		this.userText.setFont(font);
		
		tempPanel.add(scrollPane);

	}
	
	private void createClear() {
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(listener);
		clearPanel.add(clearButton);
	}
	
	private void SetupGUI(String type_t, int style_t, int size_t) {
		isSetupGUI = true;
		//do type here	
		fontDropdown.setSelectedItem(type_t);		
		
		//do style here
		bold.setSelected((style_t & 1) == 1? true: false);
		italic.setSelected((style_t & 2) == 2? true: false);
		
		
		//do size here		
		switch (size_t) {
		case 8: 
			button1.setSelected(true); 
			break;
		case 16: 
			button2.setSelected(true); 
			break;
		case 24: 
			button3.setSelected(true); 
			break;
		case 32: 
			button4.setSelected(true); 
			break;
		case 40: 
			button5.setSelected(true); 
			break;
		default:
			break;
		}
		
	}
	
	
	//event listener and event source
	class ButtonListener implements ActionListener {
		
		public void actionPerformed (ActionEvent eventName) {
			String event = eventName.getActionCommand();
			System.out.println("event = " + event);
			System.out.println("text= " + userText.getText());

			
			if (isSetupGUI) {
				isSetupGUI = false;
//				System.out.println("isSetupGUI is now false");
				return;
			}
			
			
			if (event.equals("Undo")) {				
				if (!undoStack.isEmpty()) {
					ArrayList<Object> redoState = new ArrayList <Object>(Arrays.asList(fontType, fontStyle, fontSize, userText.getText()));
					redoStack.push(redoState);
					ArrayList prevState = undoStack.pop();
					
					System.out.println("stack looks like: "+undoStack);
//					System.out.println("prevState index 0 = "+prevState.get(0) + " " +prevState.get(1)+" "+prevState.get(2));									
					font = new Font((String) prevState.get(0), (Integer) prevState.get(1), (Integer) prevState.get(2));
					userText.setText((String) prevState.get(3));
					userText.setFont(font);
					
					// new add here
					fontType = font.getName();// how to get type?
					fontStyle = font.getStyle();
					fontSize = font.getSize();	
					//set GUI here
					SetupGUI(fontType, fontStyle, fontSize);	
					
				}
				else
					System.out.println("Nothing to undo");
			}
				
			else if (event.equals("Redo")) {
				if (!redoStack.isEmpty()) {
					ArrayList<Object> undoState = new ArrayList <Object>(Arrays.asList(fontType, fontStyle, fontSize, userText.getText()));
					
					undoStack.push(undoState);
					ArrayList redoState = redoStack.pop();
					font = new Font((String) redoState.get(0), (Integer) redoState.get(1), (Integer) redoState.get(2));
					userText.setText((String) redoState.get(3));
					userText.setFont(font);					
					
					// new add here
					fontType = font.getName();
					fontStyle = font.getStyle();
					fontSize = font.getSize();
					//set GUI here
					SetupGUI(fontType, fontStyle, fontSize);
				}
				else
					System.out.println("Nothing to redo");
			}
			
			else if (event.equals("Clear")) {

				userText.setText("");
				ArrayList<Object> currentState = new ArrayList <Object>(Arrays.asList(fontType, fontStyle, fontSize, userText.getText()));
				undoStack.add(currentState);
				//set GUI to default
				
				
			}
			
			else {
			
			ArrayList<Object> currentState = new ArrayList <Object>(Arrays.asList(fontType, fontStyle, fontSize, userText.getText()));
			undoStack.add(currentState);
//			System.out.println("adding fontType: "+fontType+ " Style: "+fontStyle + " Size: "+ fontSize);
			
			if (event.equals("Bold")) {
				if (bold.isSelected()) {
					fontStyle = fontStyle | Font.BOLD;
				}
				else {
					fontStyle = fontStyle & (~Font.BOLD);
				}
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("Italic")) {
				if (italic.isSelected()) {
					fontStyle = fontStyle | Font.ITALIC;
				}
				else {
					fontStyle = fontStyle & (~Font.ITALIC);
				}
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("8 pt.")) {
				fontSize = 8;
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("16 pt.")) {
				fontSize = 16;
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("24 pt.")) {
				fontSize = 24;
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("32 pt.")) {
				fontSize = 32;
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("40 pt.")) {
				fontSize = 40;
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
			
			else if (event.equals("comboBoxChanged")) {
				fontType = (String) fontDropdown.getSelectedItem();
				System.out.println(fontType);
				font = new Font(fontType, fontStyle, fontSize);
				userText.setFont(font);
			}
						
			else if (event.equals("Exit")) {
				mainFrame.dispose();
			}
			
			}
		}
		
	}
	
}

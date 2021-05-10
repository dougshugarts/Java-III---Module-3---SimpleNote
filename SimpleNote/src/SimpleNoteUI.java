//view

import javax.swing.JFrame; 
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class SimpleNoteUI {
	
	private JFrame mainWindow = new JFrame("SimpleNote");
	private JSplitPane notesView;
	private JList <String> notesTitles;
	private JTextArea noteBlurb = new JTextArea(15,40);
	private JButton newNote = new JButton("+");
	private JButton deleteNote = new JButton("Delete");
	private JTextField titleEntry = new JTextField(15);

	private DefaultListModel <String> dModel = new DefaultListModel<>();
	
	private Font universalFont = new Font("system", Font.PLAIN, 14);
	
	public SimpleNoteUI (String[] notesTitles) {
		
		// initialize GridBagLayout
		Container cont = this.mainWindow.getContentPane();
		GridBagConstraints cst = new GridBagConstraints();
		cont.setLayout(new GridBagLayout());

		cst.fill = GridBagConstraints.NONE;
		cst.insets = new Insets(10,10,10,10);
		cst.gridx = 0;
		cst.gridy = 0;
		cst.gridheight = 1;
		cst.gridwidth = 1;
		cst.weightx = 0.0;
		cst.weighty = 0.0;
		
		this.titleEntry.setFont(universalFont);
		this.titleEntry.setText("Add a note...");
		this.titleEntry.setMargin(new Insets(2,2,3,2));
		cont.add(this.titleEntry, cst);
		
		cst.gridx = 1;
		cst.insets = new Insets(0,0,0,0);
		cont.add(this.newNote, cst);	
		
		this.notesView = new JSplitPane();
		this.notesTitles = new JList<String>(this.dModel);
		this.setNoteTitles(notesTitles);
		
		this.noteBlurb.setFont(this.universalFont);
		this.noteBlurb.setMargin(new Insets(10,10,10,10));
		this.noteBlurb.setLineWrap(true);
		this.noteBlurb.setWrapStyleWord(true);
		this.noteBlurb.setText("Note blurb here");
		
		JScrollPane leftScroll = new JScrollPane(this.notesTitles);
		JScrollPane rightScroll = new JScrollPane(this.noteBlurb);
		
		this.notesView.setLeftComponent(leftScroll);
		this.notesView.setRightComponent(rightScroll);
		this.notesView.setDividerLocation(175);
		this.notesView.setDividerSize(6);

		cst.insets = new Insets(10,10,10,10);
		cst.fill = GridBagConstraints.BOTH;
		cst.gridx = 0;
		cst.gridy = 1;
		cst.gridwidth = 10;
		cst.weightx = 1.0;
		cst.weighty = 1.0;
		cont.add(this.notesView, cst);
		
		JPanel testPanel = new JPanel();
		testPanel.setSize(new Dimension(400,80));
		FlowLayout testLayout = new FlowLayout(FlowLayout.RIGHT);
		testPanel.setLayout(testLayout);
		
		testPanel.add(this.deleteNote);
		testPanel.setVisible(true);
		
		//cst.fill = GridBagConstraints.NONE;
		cst.gridx = 7;
		cst.gridy = 0;
		cst.weightx = 0;
		cst.weighty = 0;
		//cst.insets = new Insets(0,0,0,0);
		cont.add(testPanel, cst);

		this.mainWindow.setMinimumSize(new Dimension(750,450));	
		this.mainWindow.pack();
		//this.mainWindow.setMinimumSize(new Dimension(620,500));
		this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		

	}

	// method to set list of note titles at launch
	public void setNoteTitles (String[] titles) {
		
		for (int count = 0; count < titles.length; count++) {
			
			this.dModel.addElement(titles[count]);
		}
		
		this.notesTitles.setFont(new Font("system", Font.PLAIN, 14));
		this.notesTitles.setFixedCellHeight(25);
		this.notesTitles.setFixedCellWidth(100);
		this.notesTitles.setBorder(new EmptyBorder(10,10,10,10));
	}
	
	// button getters
	public JButton getNewNoteButton () {
		
		return this.newNote;
	}
	
	public JButton getDeleteButton () {
		
		return this.deleteNote;
	}
	
	
	public String getNoteText() {
		
		return this.noteBlurb.getText();
	}
	
	// window getters	
	public JFrame getMainWindow () {
	
		return this.mainWindow;
	}
	
	public JList<String> getNoteTitles() {
		
		return this.notesTitles;
	}
	
	public DefaultListModel<String> getDModel() {
		
		return this.dModel;
	}
	
	public void setNoteText (SimpleNote note) {
		
		this.noteBlurb.setText(note.getNoteBody());
	}
	
	public JTextField getTitleEntry () {
		
		return this.titleEntry;
	}
	
	public JTextArea getNoteBlurb () {
		
		return this.noteBlurb;
	}
}

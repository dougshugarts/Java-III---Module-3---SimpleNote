//controller

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SimpleNoteController implements ActionListener, ListSelectionListener, MouseListener, KeyListener, FocusListener {
	
	// TreeMap to hold notes, default notes below
	private static TreeMap <String, SimpleNote> noteList = new TreeMap<>();
	
	// default note
	private SimpleNote welcome = new SimpleNote("Welcome","SimpleNote is a basic Swing-based notes app I created for Module 3, Java3.\n\nI included the text below to show the app's scroll bars and readability when the window is re-sized.\n\n--------------------------------\n\nFour score and seven years ago our fathers brought forth on this continent a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.\r\n"
			+ "\r\n"
			+ "Now we are engaged in a great civil war, testing whether that nation or any nation so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this.\r\n"
			+ "\r\n"
			+ "But, in a larger sense, we can not dedicate—we can not consecrate—we can not hallow—this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us—that from these honored dead we take increased devotion to that cause for which they gave the last full measure of devotion—that we here highly resolve that these dead shall not have died in vain—that this nation, under God, shall have a new birth of freedom—and that government of the people, by the people, for the people, shall not perish from the earth.");

	// load the app's window
	private SimpleNoteUI appUI;;
	
	// SimpleNote instance assigned to active note for event-handling
	private SimpleNote activeNote;
	
	// method to populate list of note titles at launch
	private String[] loadNotes() {
		
		ArrayList <String> noteTitles = new ArrayList<>();
		
		Set <Entry <String, SimpleNote>> notes = noteList.entrySet();
		Iterator <Entry <String, SimpleNote>> notesIterator = notes.iterator();
		
		while (notesIterator.hasNext()) {
			
			String title = notesIterator.next().getValue().getNoteTitle();
			noteTitles.add(title);
		}
		
		return noteTitles.toArray(new String[noteTitles.size()]);
	}
	
	
	private void addNote(SimpleNote note) {
		
		noteList.put(note.getNoteTitle(), note);
	}
	
	// remove note from above TreeMap
	private void deleteNote (SimpleNote note) {
		
		noteList.remove(note.getNoteTitle());
	}
	
	// remove note call for events
	private void deleteNote2 () {
		
		if ((noteList.size() > 0) && (this.appUI.getNoteTitles().getSelectedValue()!=null)){
			
		int num = this.appUI.getNoteTitles().getSelectedIndex();
		
		this.activeNote = noteList.get(this.appUI.getNoteTitles().getSelectedValue());
		this.appUI.getNoteTitles().removeListSelectionListener(this);
		this.appUI.getDModel().removeElement(this.activeNote.getNoteTitle());
		this.deleteNote(noteList.get(this.activeNote.getNoteTitle()));
		this.appUI.getNoteBlurb().setText("");
		this.appUI.getNoteTitles().addListSelectionListener(this);
		
		// sets note above deleted note as selected value in JList
		if (num > 0) {
			
			this.appUI.getNoteTitles().setSelectedValue(this.appUI.getDModel().get(num-1), true);
			this.appUI.getNoteTitles().requestFocus();
			}
		
		else if ((num == 0) && (this.appUI.getDModel().size() >= 1)) {
			
			this.appUI.getNoteTitles().setSelectedValue(this.appUI.getDModel().get(0), true);
			this.appUI.getNoteTitles().requestFocus();
		}
		
		}
	}
	
	
	// launch app and set listeners
	public static void main(String[] args) {
		
		SimpleNoteController launchApp = new SimpleNoteController();
		launchApp.addNote(launchApp.welcome);
		launchApp.appUI = new SimpleNoteUI (launchApp.loadNotes());
		
		launchApp.appUI.setNoteText(noteList.get("Welcome"));
		launchApp.appUI.getNoteTitles().setSelectedValue(launchApp.appUI.getDModel().lastElement(),true);
		
		launchApp.activeNote = noteList.get("Welcome");
		
		launchApp.appUI.getNewNoteButton().addActionListener(launchApp);
		
		launchApp.appUI.getNoteTitles().addListSelectionListener(launchApp);
		launchApp.appUI.getNoteTitles().addKeyListener(launchApp);
		launchApp.appUI.getNoteTitles().addMouseListener(launchApp);
		
		launchApp.appUI.getTitleEntry().addActionListener(launchApp);
		launchApp.appUI.getTitleEntry().addMouseListener(launchApp);
		
		launchApp.appUI.getNoteBlurb().addMouseListener(launchApp);
		launchApp.appUI.getNoteBlurb().addFocusListener(launchApp);
		launchApp.appUI.getNoteBlurb().addKeyListener(launchApp);
		launchApp.appUI.getNoteBlurb().requestFocus();
			
		launchApp.appUI.getDeleteButton().addActionListener(launchApp);
	}

	
	// event handling for new note button
	@Override
	public void actionPerformed(ActionEvent e) {
			
		if (e.getSource().equals(this.appUI.getTitleEntry()) || (e.getSource().equals(this.appUI.getNewNoteButton()))) {
		
			SimpleNote newNote = new SimpleNote(this.appUI.getTitleEntry().getText(), "");
			this.addNote(newNote);
			
			this.appUI.getNoteTitles().removeListSelectionListener(this);
			this.appUI.setNoteText(newNote);
			this.appUI.getDModel().addElement(newNote.getNoteTitle());
			this.appUI.getNoteTitles().setSelectedValue(this.appUI.getDModel().lastElement(),true);
			this.activeNote = newNote;
			this.appUI.getTitleEntry().setText("Add a note...");
			this.appUI.getNoteBlurb().requestFocus();
			this.appUI.getNoteTitles().addListSelectionListener(this);
		}
			
		else if (e.getSource().equals(this.appUI.getDeleteButton())) {
			
			this.deleteNote2();
		}	
	}


	// event-handling for note titles list (JList)
	@Override
	public void valueChanged(ListSelectionEvent e) {
	
		if (noteList.size() > 0) {
		
		this.activeNote = noteList.get(this.appUI.getNoteTitles().getSelectedValue());
		this.appUI.setNoteText(this.activeNote);
		}
	}

	// sets click event to clear note title field
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource().equals(this.appUI.getTitleEntry()) && (this.appUI.getTitleEntry().getText().equals("Add a note..."))) {
			this.appUI.getTitleEntry().setText("");
		}
	}

	// unimplemented mouse events
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}


	// saves note body text
	@Override
	public void keyTyped(KeyEvent e) {

		if (this.appUI.getNoteBlurb().hasFocus()) {
			this.activeNote.setNoteBody(this.appUI.getNoteBlurb().getText());
		}
	}

	// set 'DELETE' and 'BACKSPACE' keys to delete selected note
	@Override
	public void keyPressed(KeyEvent e) {
		
		if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) && this.appUI.getNoteTitles().hasFocus()) {
			
			this.deleteNote2();			
		}
	}

	// unimplemented key and focus methods
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

}

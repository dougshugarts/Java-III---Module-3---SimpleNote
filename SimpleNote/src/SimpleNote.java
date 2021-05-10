//model
public class SimpleNote {
	
	private String noteTitle;
	private String noteBody;
	
	
	public SimpleNote() {
		
		this("Untitled","Enter note text here.");
	}
	
	public SimpleNote (String title, String body) {
		
		this.setNoteTitle(title);
		this.setNoteBody(body);
	}

	
	public void setNoteTitle (String title) {
		
		this.noteTitle = title;
	}
	
	public String getNoteTitle () {
		
		return this.noteTitle;
	}
	
	public void setNoteBody (String body) {
		
		this.noteBody = body;
	}
	
	public String getNoteBody() {
		
		return this.noteBody;
	}
	
	
}

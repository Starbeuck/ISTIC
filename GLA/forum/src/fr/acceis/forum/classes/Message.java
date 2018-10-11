package fr.acceis.forum.classes;

public class Message {
	private String author;
	private String text;
	private int idThread;

	public Message(String aut, String txt, int idT) {

		this.author = aut;
		this.text = txt;
		this.idThread = idT;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIdThread() {
		return idThread;
	}

	public void setIdThread(int idThread) {
		this.idThread = idThread;
	}
}

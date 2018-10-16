package fr.acceis.forum.classes;

public class FilThread {
	private String title;
	private String author;	
	private int nbMessage;
	
	public FilThread(String tit, String aut, int nbMess) {
		this.title = tit;
		this.author = aut;
		this.nbMessage = nbMess;
	}

	public FilThread(String tit, String aut) {
		this.title = tit;
		this.author = aut;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNbMessage() {
		return nbMessage;
	}

	public void setNbMessage(int nbMessage) {
		this.nbMessage = nbMessage;
	}

}

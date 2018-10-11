package fr.acceis.forum.classes;

import java.util.List;
import java.util.ArrayList;

public class FilThread {
	private String title;
	private String author;
	private List<Message> listMessage = new ArrayList<Message>();	
	
	public FilThread(String tit, String aut) {
		this.title = tit;
		this.author = aut;
		this.listMessage = new ArrayList<Message>();
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

	public List<Message> getListMessage() {
		return listMessage;
	}

	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}
}

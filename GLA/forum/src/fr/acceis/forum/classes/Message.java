package fr.acceis.forum.classes;


// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 */
/**
 * @author solenn
 *
 */
public class Message {
	
	/** The author. */
	private User author;
	
	/** The text. */
	private String text;
	
	/** The id thread. */
	private int idThread;

	/**
	 * Instantiates a new message.
	 *
	 * @param aut the aut
	 * @param txt the txt
	 * @param idT the id T
	 */
	public Message(User aut, String txt, int idT) {
		this.author = aut;
		this.text = txt;
		this.idThread = idT;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the id thread.
	 *
	 * @return the id thread
	 */
	public int getIdThread() {
		return idThread;
	}
}

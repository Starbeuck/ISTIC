package fr.acceis.forum.classes;

// TODO: Auto-generated Javadoc
/**
 * The Class FilThread.
 */
/**
 * @author solenn
 *
 */
public class FilThread {
	
	/** The title. */
	private String title;
	
	/** The author. */
	private String author;
	
	/** The nb message. */
	private int nbMessage;

	/**
	 * Instantiates a new fil thread.
	 *
	 * @param tit the tit
	 * @param aut the aut
	 * @param nbMess the nb mess
	 */
	public FilThread(String tit, String aut, int nbMess) {
		this.title = tit;
		this.author = aut;
		this.nbMessage = nbMess;
	}

	/**
	 * Instantiates a new fil thread.
	 *
	 * @param tit the tit
	 * @param aut the aut
	 */
	public FilThread(String tit, String aut) {
		this.title = tit;
		this.author = aut;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Gets the nb message.
	 *
	 * @return the nb message
	 */
	public int getNbMessage() {
		return nbMessage;
	}

}

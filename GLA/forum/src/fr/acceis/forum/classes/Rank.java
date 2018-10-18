package fr.acceis.forum.classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Rank.
 *
 * @author solenn
 */
public class Rank {

	/** The ranked. */
	private String ranked;

	/** The URL badge. */
	private String URLBadge;

	/**
	 * Instantiates a new rank.
	 *
	 * @param nbMessages the nb messages
	 */
	public Rank(int nbMessages) {
		if (nbMessages < 20) {
			setRanked("Novice");
			setURLBadge("fichiers/imgs/Rank/JJBinks.png");
		} else if (21 < nbMessages & nbMessages < 40) {
			setRanked("Padawan");
			setURLBadge("fichiers/imgs/Rank/Skywalker.png");
		} else if (41 < nbMessages & nbMessages < 60) {
			setRanked("Chevalier Jedi");
			setURLBadge("fichiers/imgs/Rank/Windu.png");
		} else if (61 < nbMessages & nbMessages < 95) {
			setRanked("Maître Jedi");
			setURLBadge("fichiers/imgs/Rank/Kenobi.png");
		} else {
			setRanked("Grand Maître Jedi");
			setURLBadge("fichiers/imgs/Rank/Yoda.png");
		}
	}

	/**
	 * Gets the ranked.
	 *
	 * @return the ranked
	 */
	public String getRanked() {
		return ranked;
	}

	/**
	 * Sets the ranked.
	 *
	 * @param ranked the new ranked
	 */
	public void setRanked(String ranked) {
		this.ranked = ranked;
	}

	/**
	 * Gets the URL badge.
	 *
	 * @return the URL badge
	 */
	public String getURLBadge() {
		return URLBadge;
	}

	/**
	 * Sets the URL badge.
	 *
	 * @param uRLBadge the new URL badge
	 */
	public void setURLBadge(String uRLBadge) {
		URLBadge = uRLBadge;
	}
}

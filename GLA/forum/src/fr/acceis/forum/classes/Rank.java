package fr.acceis.forum.classes;

public class Rank {
	private String ranked;
	private String URLBadge;

	public Rank(int nbMessages) {
		if (nbMessages < 20) {
			setRanked("Novice");
			setURLBadge("fichiers/imgs/JJBinks.png");
		} else if (21 < nbMessages & nbMessages < 40) {
			setRanked("Padawan");
			setURLBadge("fichiers/imgs/Skywalker.png");
		} else if (41 < nbMessages & nbMessages < 60) {
			setRanked("Chevalier Jedi");
			setURLBadge("fichiers/imgs/Windu.png");
		} else if (61 < nbMessages & nbMessages < 95) {
			setRanked("Maître Jedi");
			setURLBadge("fichiers/imgs/Kenobi.png");
		} else {
			setRanked("Grand Maître Jedi");
			setURLBadge("fichiers/imgs/Yoda.png");
		}
	}

	public String getRanked() {
		return ranked;
	}

	public void setRanked(String ranked) {
		this.ranked = ranked;
	}

	public String getURLBadge() {
		return URLBadge;
	}

	public void setURLBadge(String uRLBadge) {
		URLBadge = uRLBadge;
	}
}

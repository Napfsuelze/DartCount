package Model;

/**
 * Repräsentiert einen Dartwurf
 */
public class Dart {
	private int score;
	private boolean doub;
	private boolean trip;

    /**
     * Konstruktor
     */
	public Dart() {
		this(0, false, false);
	}

    /**
     * Konstruktor
     * @param score
     *          getroffenes Feld
     * @param doub
     *          Double
     * @param trip
     *          Triple
     */
	public Dart(int score, boolean doub, boolean trip) {
		this.setScore(score);
		this.setDoub(doub);
		this.setTrip(trip);
	}

    /**
     * Gibt den Score OHNE Double oder Triple Multiplikation wieder
     * @return
     */
	public int getScore() {
		return score;
	}

    /**
     * Setzt den Score OHNE Double oder Triple Multiplikation
     * @param score
     */
	public void setScore(int score) {
		this.score = score;
	}

	public boolean isDoub() {
		return doub;
	}

	public void setDoub(boolean doub) {
		this.doub = doub;
	}

	public boolean isTrip() {
		return trip;
	}

	public void setTrip(boolean trip) {
		this.trip = trip;
	}

    /**
     * Gibt den Score MIT Double oder Triple Multiplikation wieder
     * @return
     */
	public int getTotalDartScore() {
		int points = this.getScore();
		if (this.isDoub())
			return points * 2;
		if (this.isTrip())
			return points * 3;
		return points;
	}

    /**
     * Stringrepräsentation
     * @return
     *      D/T <score>
     */
	public String toString() {
		if (doub)
			return "D" + score;
		if (trip)
			return "T" + score;
		else
			return String.valueOf(score);
	}
}

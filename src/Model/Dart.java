package Model;

public class Dart {
	private int score;
	private boolean doub;
	private boolean trip;
	
	public Dart() {
		this(0, false, false);
	}
	
	public Dart(int score, boolean doub, boolean trip) {
		this.setScore(score);
		this.setDoub(doub);
		this.setTrip(trip);
	}

	public int getScore() {
		return score;
	}

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
	
	public int getTotalDartScore() {
		int points = this.getScore();
		if (this.isDoub())
			return points * 2;
		if (this.isTrip())
			return points * 3;
		return points;
	}
	
	public String toString() {
		if (doub)
			return "D" + score;
		if (trip)
			return "T" + score;
		else
			return String.valueOf(score);
	}
}

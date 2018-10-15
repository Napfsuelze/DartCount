package model;

import java.util.ArrayList;
import java.util.List;

import constant.AppConstants;
import model.Model.ScoreInputType;

/**
 * Repräsentiert ein DartGame für einen Spieler
 */
public class Game {
	
	int points;
	int type;
	Player player;
	List<Dart> thrownDarts;
	List<Dart> removedDarts;
	int throwCount;
	private boolean isActive;
	private boolean finish;
	private ScoreInputType inputType;

    /**
     * Konstruktor
     * @param points
     *          Punkte von denen herunter gespielt wird
     * @param type
     *          Straight- oder Doubleout
     * @param player
     *          Der Spieler
     */
	public Game(int points, int type, Player player) {
		this.points = points;
		this.type = type;
		this.player = player;
		this.thrownDarts = new ArrayList<>();
		this.removedDarts = new ArrayList<>();
		this.throwCount = 0;
		this.setActive(false);
		this.setFinish(false);
	}

    /**
     * Simuliert einen Dartwurf
     * @param dart
     */
	public void throwDart(Dart dart) {
		if (throwCount == 3) {
			nextRound();
			return;
		}
		
		thrownDarts.add(dart);
		throwCount += 1;
		
		int score = dart.getTotalDartScore();
		
		if (this.points - score < 0) {
			resetScore();
			return;
		}
		if (this.points - score != 0) {
			this.points -= score;
			//TODO: Statistik
			return;
		}
		
		if (this.points - score == 0) {
			boolean win = checkValidWin(dart.isDoub());
			if (win)
				endGame();
			if(!win)
				resetScore();
		}
	}

    /**
     * Setzt den letzten Dartwurf zurück
     * @return
     */
	public int removeLastDart() {
	    if (throwCount == 0)
	        return 0;
	    throwCount -= 1;
	    Dart lastDart = thrownDarts.get(thrownDarts.size() - 1);
	    removedDarts.add(lastDart);
	    int lastScore = lastDart.getTotalDartScore();
	    this.points =+ lastScore;
	    thrownDarts.remove(lastDart);

	    return lastScore;
    }

    /**
     * Setzt den Score zurück falss z.B. überworfen wurde
     */
	private void resetScore() {
		if (inputType == ScoreInputType.DARTSEPERATED) {
			for (int i = 0; i < throwCount; i++) {
				Dart dart = thrownDarts.get(thrownDarts.size() - 1);
				this.points = this.points + dart.getTotalDartScore();
				System.out.println(dart.getTotalDartScore() + " zurückgesetzt");
			}
			throwCount = 0;
		} else if(inputType == ScoreInputType.DIRECT) {
			Dart dart = thrownDarts.get(thrownDarts.size() - 1);
			this.points = this.points + dart.getTotalDartScore();
		}
	}

    /**
     * Es kann wieder 3 Mal geworfen werden, setzt das Spiel auf InActive
     */
	private void nextRound() {
		throwCount = 0;
		this.setActive(false);
		//gameview BEscheid geben dass nächste dran ist. Active Flag?
	}

    /**
     * Setzt isfinished auf true
     */
	private void endGame() {
		//gameView Bescheidgeben dass dieses Spiel fertig ist.
		this.setFinish(true);
	}

    /**
     * Prüft jenach Spiel Typ die Gültigkeit des Sieges
     * @param doub
     *          Flag Doublefeld
     * @return
     *      true wenn ja, false sonst
     */
	private boolean checkValidWin(boolean doub) {
		if (this.type == AppConstants.GAME_TYPE_DOUBLE_OUT && doub)
			return true;
		if (this.type == AppConstants.GAME_TYPE_DOUBLE_OUT && !doub)
			return false;
		return true;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getPoints() {
		return points;
	}
	
	@Override
	public boolean equals(Object o) {
		Game compare = ((Game) o);
		if (this.player == compare.getPlayer() && this.points == compare.getPoints())
			return true;
		return false;
	}
	
}

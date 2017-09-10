package Model;

import java.util.ArrayList;
import java.util.List;

import Model.Model.ScoreInputType;

public class Game {
	
	int points;
	int type;
	Player player;
	List<Dart> thrownDarts;
	int throwCount;
	private boolean isActive;
	private boolean finish;
	private ScoreInputType inputType;
	
	public Game(int points, int type, Player player) {
		this.points = points;
		this.type = type;
		this.player = player;
		this.thrownDarts = new ArrayList<>();
		this.throwCount = 0;
		this.setActive(false);
		this.setFinish(false);
	}
	
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
	
	private void nextRound() {
		throwCount = 0;
		this.setActive(false);
		//gameview BEscheid geben dass nächste dran ist. Active Flag?
	}
	
	private void endGame() {
		//gameView Bescheidgeben dass dieses Spiel fertig ist.
		this.setFinish(true);
	}
	
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

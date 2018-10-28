package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * model der Anwendung. Speichert Spieler, aktive Spieler, Settings und die Spiele
 */
public class Model extends Observable {

	private ObservableList<Player> playerList;
	private List<Player> activePlayerList;
	
	private int gameType;
	private int finishType;
	
	private Game activeGame;
	private List<Game> games;
	
	public enum ScoreInputType {
		DIRECT, DARTSEPERATED, OPENCV
	}
	
	private ScoreInputType scoreInputType;
	private int playerCount;
	private int highestOrder;

    /**
     * Konstruktor
     */
	public Model() {
		this.playerList = FXCollections.observableArrayList();
		Player test = new Player("TestPlayer");
		//test.setActive(true);
		playerList.add(test);
		playerList.add(new Player("Niklas"));
		playerList.add(new Player("Arne"));
		this.activePlayerList = new Vector<Player>();
		games = new ArrayList<>();
		createInitialConfig();
	}

    /**
     * Initialie Settings
     */
	private void createInitialConfig() {
		gameType = 301;
		finishType = 0;
		//scoreInputType = ScoreInputType.DARTSEPERATED;
	}

	public void addPlayer(Player player) {
		playerList.add(player);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
		removeActivePlayer(player);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addActivePlayer(Player player) {
		activePlayerList.add(player);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void removeActivePlayer(Player player) {

		if (player == null)
		{
			return;
		}

		activePlayerList.remove(player);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Prüft ob es aktive Spieler gibt Erstellt für jeden Spieler ein Spiel -> Game ist spielbereit
	 * @return true wenn Spieler aktiv sind
	 * 			false wenn nicht
	 */
	public boolean startDartGame() {

		/*activePlayerList.clear();

		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).isActive())
			{
				addActivePlayer(playerList.get(i));
			}
		}*/

		//DEBUG
		for (Player player : this.activePlayerList)
		{
			System.out.println(player.getName() + " " + player.getOrder());
		}

		if (this.activePlayerList.isEmpty())
		{
			return false;
		}

		for (int i = 0; i < this.activePlayerList.size(); i++) {
			Game game = new Game(this.gameType, this.finishType, this.activePlayerList.get(i));

			if (i == 0)
			{
				this.activeGame = game;
			}

			this.games.add(game);
		}

		this.playerCount = this.games.size();
		
		setChanged();
		notifyObservers(this.games);
		return true;
	}
	
	public void throwDart(Dart dart) {
		this.activeGame.throwDart(dart);
		setChanged();
		notifyObservers(games);
		
	}

	public int removeLastDart() {
	    int lastScore = activeGame.removeLastDart();
	    this.notifyObservers();
	    return lastScore;
    }
	
	public void addObserver(Observable o) {
		this.addObserver(o);
	}
	
	public ObservableList<Player> getPlayerList() {
		return this.playerList;
	}
	
	public List<Player> getActivePlayerList() {
		return this.activePlayerList;
	}


	public void setGameType(int i) {
		gameType = i;
	}
	
	public int getGameType() {
		return gameType;
	}
	
	public void setFinishType(int i) {
		finishType = i;
	}
	
	public int getFinishType() {
		return finishType;
	}

	public ScoreInputType getScoreInputType() {
		return scoreInputType;
	}

	public void setScoreInputType(ScoreInputType scoreInputType) {
		this.scoreInputType = scoreInputType;
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<Game> getGames() {
		return this.games;
	}

	public void closeActualGame() {
		this.activeGame = null;
		//this.activePlayerList.clear();
		this.games.clear();
	}

	public boolean isActiveGameisFinished() {
		return activeGame.isFinish();
	}

	public void rotatePlayer() {
		if (games.indexOf(activeGame) == playerCount - 1)
		{
			activeGame = games.get(0);
		}
		else
		{
			activeGame = games.get(games.indexOf(activeGame) + 1);
		}
	}

	public Game getActiveGame()
	{
		return this.activeGame;
	}

	public void setHighestOrder(int order)
	{
		this.highestOrder = order;
	}

	public int getHighestOrder()
	{
		return this.highestOrder;
	}

	public void incrementHighestOrder()
	{
		this.highestOrder += 1;
	}

	public void decrementHighestOrder()
	{
		if (this.highestOrder == 0)
		{
			return;
		}

		this.highestOrder -= 1;
	}
}

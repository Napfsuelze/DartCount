package Controller;

import Model.Model;
import Model.Player;
import View.setting.AudioSettingView;
import View.setting.GameSettingView;
import View.GameView;
import View.MenuView;
import View.PlayerView;
import View.setting.SettingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Controller der Anwendung. Speichert die Stage, das Model und die Views.
 * Bindet die Komponenten mit Listener
 */
public class Controller {
	private Model model;
	private MenuView menuView;
	private PlayerView playerView;
	private Stage primaryStage;
	private SettingView settingView;
	private GameSettingView gameSettingView;
	private AudioSettingView audioSettingView;
	private GameView gameView;

    /**
     * Konstruktor
     * @param primaryStage
     *          Stage der Anwendung
     */
	public Controller(Stage primaryStage) {
		this.model = new Model();
		this.menuView = new MenuView(model);
		this.playerView = new PlayerView(model);
		this.settingView = new SettingView(model, this);
		this.gameSettingView = new GameSettingView(model, this);
		this.audioSettingView = new AudioSettingView(model, this);
		this.gameView = new GameView(model, this);
		model.addObserver(gameView);
		this.primaryStage = primaryStage;
		
		
		addListener();
	}

    /**
     * Setzt die Listener der Views um diese zu binden
     */
	private void addListener() {
		menuView.setGameListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.startDartGame();
				showGameView();
				//TODO: View anzeigen, dass Spieler aktiv sein m�ssen
			}
		});
		
		menuView.setPlayerListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showPlayerView();
			}

			
		});
		
		menuView.setSettingListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSettingView();
			}

			
		});
		
		playerView.setBackListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showStartView();
			}
			
		});
		
		playerView.setAddPlayerListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String name = playerView.getAddPlayerName();
				if (name == null)
					return;
				model.addPlayer(new Player(name));
			}
			
		});
		
		settingView.setBackListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showStartView();
			}
			
		});
		
		gameView.addBackListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showStartView();
			}
			
		});
		
		gameView.addCloseGameListener(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Bestätigung -> Wollen Sie wirklich das aktuelle Spiel beenden?
				showStartView();
				model.closeActualGame();
			}
			
		});
	}

    /**
     * Zeigt den Start/Menu View
     */
	public void showStartView() {
		primaryStage.setScene(menuView.getScene());
		primaryStage.setTitle("Count your Darts");
	}

    /**
     * Zeigt den Player View
     */
	public void showPlayerView() {
		primaryStage.setScene(playerView.getScene());
		primaryStage.setTitle("Count your Darts - Player");
	}

    /**
     * Zeigt den Setting View
     */
	public void showSettingView() {
		primaryStage.setScene(settingView.getScene());
		primaryStage.setTitle("Count your Darts - Setting");
	}

    /**
     * Zeigt den Game View
     */
	public void showGameView() {
		primaryStage.setScene(gameView.getScene());
		primaryStage.setTitle("Count your Darts - Game");
	}

    /**
     * Läd einen bestimmten Input View für den Game View
     * @param type
     *          Typ der Eingabe
     */
	public void loadScoreInputView(Model.ScoreInputType type) {
        model.setScoreInputType(type);
	    gameView.loadInputView();
		addListener();
	}

    /**
     * Prüft ob ein Spiel beendet wurde und wechselt in den Start View
     * @return
     */
	public boolean checkFinish() {
		if (!model.isActiveGameisFinished()) {
			model.rotatePlayer();
			return false;
		}
		//TODO Extra View*Gratulation*Restart*Zurück zum Neu*
		model.closeActualGame();
		showStartView();
		return true;
	}

    /**
     * Aktualisiert die Setting Einstellungen in den Views
     */
	public void refreshActualSettingData() {
		gameSettingView.refreshSettingData();
		audioSettingView.refreshSettingData();
	}
	
}

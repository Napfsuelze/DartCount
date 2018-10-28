package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.Model;
import model.Player;
import view.setting.AudioSettingView;
import view.setting.GameSettingView;
import view.GameView;
import view.MenuView;
import view.PlayerView;
import view.setting.SettingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.List;


/**
 * controller der Anwendung. Speichert die Stage, das model und die Views.
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
		this.model.addObserver(gameView);
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

				if (checkActiveGame())
				{
					showContinueGamePopup();
				}
				else
				{
					if (activePlayers())
					{
						Controller.this.model.startDartGame();
						showGameView();
					}
					else
					{
						showMissingPlayersPopup();
					}
				}
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
				{
					return;
				}

				Player newPlayer = new Player(name);

				addActivePlayerListener(newPlayer);

				Controller.this.model.addPlayer(newPlayer);
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
				showStartView();
				model.closeActualGame();
			}
		});

		List<Player> playerList = this.model.getPlayerList();

		for (Player player : playerList)
		{
			addActivePlayerListener(player);
		}

	}

	private void addActivePlayerListener(Player player)
	{
		player.onProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
				{
					Controller.this.model.addActivePlayer(player);
				}
				else
				{
					Controller.this.model.removeActivePlayer(player);
				}
			}
		});
	}

    /**
     * Zeigt den Start/Menu view
     */
	public void showStartView() {
		primaryStage.setScene(menuView.getScene());
		primaryStage.setTitle("Count your Darts");
	}

    /**
     * Zeigt den Player view
     */
	public void showPlayerView() {
		primaryStage.setScene(playerView.getScene());
		primaryStage.setTitle("Count your Darts - Player");
	}

    /**
     * Zeigt den Setting view
     */
	public void showSettingView() {
		primaryStage.setScene(settingView.getScene());
		primaryStage.setTitle("Count your Darts - Settings");
	}

    /**
     * Zeigt den Game view
     */
	public void showGameView() {
		primaryStage.setScene(gameView.getScene());
		primaryStage.setTitle("Count your Darts - Game");
	}

    /**
     * Läd einen bestimmten Input view für den Game view
     * @param type
     *          Typ der Eingabe
     */
	public void loadScoreInputView(Model.ScoreInputType type) {
        model.setScoreInputType(type);
	    gameView.loadInputView();
		addListener();
	}

    /**
     * Prüft ob ein Spiel beendet wurde und wechselt in den Start view
     * @return
     */
	public boolean checkFinish() {
		if (!model.isActiveGameisFinished())
		{
			model.rotatePlayer();
			return false;
		}

		showGratulationPopup();
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

	public boolean checkActiveGame()
	{
		if (this.model.getGames().size() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private void showContinueGamePopup()
	{
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);

		GridPane gridPane = new GridPane();
		Text text = new Text("Möchten Sie das aktuelle Spiel wirklich abbrechen?");

		Button btnCofnirm = new Button("Ja");
		Button btnCancel = new Button("Nein");

		btnCofnirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();

				if (!activePlayers())
				{
					showMissingPlayersPopup();
					return;
				}

				Controller.this.model.closeActualGame();
				Controller.this.model.startDartGame();
				showGameView();
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
				showGameView();
			}
		});

		gridPane.add(text, 0, 0, 2, 1);
		gridPane.add(btnCofnirm, 0, 1);
		gridPane.add(btnCancel, 1, 1);

		Scene dialogScene = new Scene(gridPane, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	private boolean activePlayers()
	{
		return !this.model.getActivePlayerList().isEmpty();
	}

	private void showMissingPlayersPopup()
	{
		buildTextDialog("Keine Spieler ausgewählt").show();
	}

	private void showGratulationPopup()
	{
		buildTextDialog("Herzlichen Glückwunsch - " + this.model.getActiveGame().getPlayer().getName() + " hat gewonnen.").show();
	}

	private Stage buildTextDialog(String text)
	{
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);

		GridPane pane = new GridPane();

		pane.getChildren().add(new Text(text));

		Scene dialogScene = new Scene(pane, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();

		return dialog;
	}

	private void restructureOrder(int deletedOrder)
	{
		List<Player> playerList = this.model.getPlayerList();

		for (Player player : playerList)
		{
			int playerOrder = player.getOrder();

			if (playerOrder > deletedOrder)
			{
				player.setOrder(playerOrder - 1);
			}
		}
	}

	/*private void addOrderChangeListener(Player player)
	{
		player.onProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				int highestOrder = Controller.this.model.getHighestOrder();

				if (newValue)
				{
					player.setOrder(highestOrder);
					Controller.this.model.incrementHighestOrder();
				}
				else
				{
					if (player.getOrder() == highestOrder)
					{
						Controller.this.model.decrementHighestOrder();
						player.setOrder(0);
					}
					else
					{
						restructureOrder(player.getOrder());
					}
				}
			}
		});
	}*/
}

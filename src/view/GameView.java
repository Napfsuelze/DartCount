package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import model.Game;
import model.Model;
import view.scoreinput.DartSeperatedInputView;
import view.scoreinput.DirectInputView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
//TODO Bereits getätigte Eingabe korrigieren

/**
 * Game view der Anwendung. Beinhaltet als subScene einen InputView
 */
public class GameView implements Observer {
	
	private Model model;
	private Controller controller;
	private GridPane mainPane;
	private Scene scene;
	private Button btnBack;
	private SubScene scoreInput;
	private VBox scores;
	private Button btnCloseGame;

    /**
     * Konstruktor
     * @param model
     *          Referenz zum model
     * @param controller
     *          Referenz zum controller
     */
	public GameView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		buildLayout();
	}

    /**
     * Update Routine des Views
     * @param arg0
     * @param arg1
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        if (arg1 instanceof List<?>) {
            getActualModelData();
        }
    }
	
	private void buildLayout() {
		mainPane = new GridPane();
		GridPane inputPane = new GridPane();
		
		btnBack = new Button("Back");
        btnBack.getStyleClass().add("button-navigate");
		btnCloseGame = new Button("Cancel Game");
        btnCloseGame.getStyleClass().add("button-navigate");
		
		HBox bottomHbox = new HBox(btnBack, btnCloseGame);
		HBox.setMargin(btnBack, new Insets(10));
		HBox.setMargin(btnCloseGame, new Insets(10));

		
		//Score Input
		//-------------------
		if (model.getScoreInputType() == Model.ScoreInputType.OPENCV) {
			
		} else if (model.getScoreInputType() == Model.ScoreInputType.DARTSEPERATED) {
			DartSeperatedInputView inputView = new DartSeperatedInputView(model, controller);
			scoreInput = inputView.getScene();
		} else { //Direct is standart
			DirectInputView inputView = new DirectInputView(model, controller);
			scoreInput = inputView.getScene();
		}

		inputPane.add(scoreInput, 0, 0);
		GridPane.setHalignment(inputPane, HPos.CENTER);
		GridPane.setValignment(inputPane, VPos.CENTER);

		//Player and Scores
		//------------------
		scores = new VBox();

		mainPane.add(scores, 0, 0);
        mainPane.add(inputPane, 0,1);
		mainPane.add(bottomHbox, 0, 2);

		
		scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/styleclass.css").toString());
	}
	
	public Scene getScene() {
        return scene;
	}
	
	public void addBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}
	
	public void addCloseGameListener(EventHandler<ActionEvent> e) {
		btnCloseGame.setOnAction(e);
	}
	
	public void loadInputView() {
		refreshScene();
	}
	
	private void refreshScene() {
		buildLayout();
		getActualModelData();
	}

    /**
     *  Holt sich die aktuellen Spiel Daten
     */
	private void getActualModelData() {
		List<Game> games = this.model.getGames();
		scores.getChildren().clear();
		for (int i = 0; i < games.size(); i++) {
			Game game = games.get(i);
			Label label = new Label(game.getPlayer().getName() + " " + game.getPoints());
			scores.getChildren().add(label);
		}
	}
}

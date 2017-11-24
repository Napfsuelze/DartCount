package View;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Controller.Controller;
import Model.Game;
import Model.Model;
import View.scoreinput.DartSeperatedInputView;
import View.scoreinput.DirectInputView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//TODO Bereits getätigte Eingabe korrigieren

/**
 * Game View der Anwendung. Beinhaltet als subScene einen InputView
 */
public class GameView implements Observer {
	
	private Model model;
	private Controller controller;
	private BorderPane mainPane;
	private Scene scene;
	private Button btnBack;
	private SubScene scoreInput;
	private VBox scores;
	private Button btnCloseGame;

    /**
     * Konstruktor
     * @param model
     *          Referenz zum Model
     * @param controller
     *          Referenz zum Controller
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
		mainPane = new BorderPane();
		
		btnBack = new Button("<- Back");
		btnCloseGame = new Button("Cancel Game");
		
		HBox topHBox = new HBox(btnBack, btnCloseGame);
		HBox.setMargin(btnBack, new Insets(10));
		HBox.setMargin(btnCloseGame, new Insets(10));
		BorderPane.setAlignment(topHBox, Pos.CENTER);
		BorderPane.setMargin(topHBox, new Insets(10));
		
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
		
		//Player and Scores
		//------------------
		scores = new VBox();
		
		
		mainPane.setLeft(scores);
		mainPane.setTop(topHBox);
		mainPane.setCenter(scoreInput);
		
		
		
		scene = new Scene(mainPane);
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

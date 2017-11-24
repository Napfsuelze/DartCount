package View;

import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Repräsentiert das Menu
 */
public class MenuView {
	
	private Scene scene;
	private GridPane mainPane;
	private Button btnStart;
	private Button btnPlayer;
	private Button btnSetting;
	private Model model;

    /**
     * Konstruktor
     * @param model
     */
	public MenuView(Model model) {
		this.model = model;
		buildLayout();
	}
	

	private void buildLayout() {
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		
		btnStart = new Button("Spiel starten");
		btnStart.setPrefWidth(200);
		mainPane.add(btnStart, 0, 0);
		GridPane.setHalignment(btnStart, HPos.CENTER);
		
		btnPlayer = new Button("Spieler");
		btnPlayer.setPrefWidth(200);
		mainPane.add(btnPlayer, 0, 1);
		GridPane.setHalignment(btnPlayer, HPos.CENTER);

		
		btnSetting = new Button("Einstellungen");
		btnSetting.setPrefWidth(200);
		mainPane.add(btnSetting, 0, 2);
		GridPane.setHalignment(btnSetting, HPos.CENTER);
		
		mainPane.setHgap(10);
		mainPane.setVgap(10);
		
		scene = new Scene(mainPane, 300, 300);
	}
	
	public void setPlayerListener(EventHandler<ActionEvent> e) {
		btnPlayer.setOnAction(e);
	}
	
	public void setSettingListener(EventHandler<ActionEvent> e) {
		btnSetting.setOnAction(e);
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setGameListener(EventHandler<ActionEvent> eventHandler) {
		btnStart.setOnAction(eventHandler);
	}	
}
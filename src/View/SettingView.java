package View;

import Model.Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SettingView {
	
	private Model model;
	private Scene scene;
	private GridPane mainPane;
	private Button btnBack;
	
	public SettingView(Model model) {
		this.model = model;
		buildLayout();
	}
	
	private void buildLayout() {
		//Pane__________________________
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
				
		//UI Komponenten________________
		btnBack = new Button("<- Back");
		GridPane.setHalignment(btnBack, HPos.LEFT);
		GridPane.setValignment(btnBack, VPos.CENTER);
		GridPane.setMargin(btnBack, new Insets(10));
		
		//Scene_________________________
		mainPane.add(btnBack, 0, 0);
		scene = new Scene(mainPane, 300, 300);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}
}

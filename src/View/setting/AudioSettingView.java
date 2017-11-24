package View.setting;

import Controller.Controller;
import Model.Model;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AudioSettingView {
	private Model model;
	private Controller controller;
	private SubScene subScene;
	
	public AudioSettingView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		buildLayout();
	}

	private void buildLayout() {
		Label label = new Label("Noch kein Audio am Start");
		BorderPane pane = new BorderPane();
		pane.setCenter(label);
		
		subScene = new SubScene(pane, 500, 500);
	}
	
	public SubScene getScene() {
		return subScene;
	}

	public void refreshSettingData() {

	}
}

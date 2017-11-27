package view.setting;

import controller.Controller;
import javafx.scene.layout.GridPane;
import model.Model;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * View für AudioSettings
 */
public class AudioSettingView {
	private Model model;
	private Controller controller;
	private SubScene subScene;
	private GridPane mainPane;

    /**
     * Konstruktor
     * @param model
     * @param controller
     */
	public AudioSettingView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		buildLayout();
	}

	private void buildLayout() {
		Label label = new Label("Noch kein Audio am Start");
        mainPane = new GridPane();
		mainPane.add(label, 0,0);
		
		subScene = new SubScene(mainPane, 500, 500);
	}
	
	public SubScene getScene() {
		return subScene;
	}

	public GridPane getMainPane() {
	    return mainPane;
    }
	public void refreshSettingData() {

	}
}

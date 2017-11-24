package View.setting;

import Model.Model;
import Controller.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SettingView {
	
	private Model model;
	private Controller controller;
	private Scene scene;
	private GridPane mainPane;
	private BorderPane pane;
	private Button btnBack;
	private SubScene subScene;
	
	public SettingView(Model model, Controller controller) {
		this.model = model;
		this.controller =  controller;
		buildLayout();
	}
	
	private void buildLayout() {
		//Pane__________________________
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);

		pane = new BorderPane();
		MenuBar menu = new MenuBar();
		Menu menuGameSetting = new Menu("Game");

		//CRAZY WORKAROUND
		MenuItem gameItem = new MenuItem();
		menuGameSetting.getItems().add(gameItem);
		menuGameSetting.addEventHandler(Menu.ON_SHOWN, event -> menuGameSetting.hide());
		menuGameSetting.addEventHandler(Menu.ON_SHOWING, event -> menuGameSetting.fire());

		menuGameSetting.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showGameSettingView();
			}
		});

		Menu menuAudioSetting = new Menu("Audio");

		//CRAZY WORKAROUND
		MenuItem audioItem = new MenuItem();
		menuAudioSetting.getItems().add(audioItem);
		menuAudioSetting.addEventHandler(Menu.ON_SHOWN, event -> menuAudioSetting.hide());
		menuAudioSetting.addEventHandler(Menu.ON_SHOWING, event -> menuAudioSetting.fire());

		menuAudioSetting.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showAudioSettingView();
			}
		});

		Menu menuOpenCVSetting = new Menu("OpenCV");

		//CRAZY WORKAROUND
		MenuItem opencvItem = new MenuItem();
		menuOpenCVSetting.getItems().add(opencvItem);
		menuOpenCVSetting.addEventHandler(Menu.ON_SHOWN, event -> menuOpenCVSetting.hide());
		menuOpenCVSetting.addEventHandler(Menu.ON_SHOWING, event -> menuOpenCVSetting.fire());

		menu.getMenus().addAll(menuGameSetting, menuAudioSetting, menuOpenCVSetting);
		pane.setTop(menu);
				
		//UI Komponenten________________
		btnBack = new Button("<- Back");
		GridPane.setHalignment(btnBack, HPos.LEFT);
		GridPane.setValignment(btnBack, VPos.CENTER);
		GridPane.setMargin(btnBack, new Insets(10));
		
		//Scene_________________________
		mainPane.add(btnBack, 0, 0);
		mainPane.add(pane, 0, 1);
		if (subScene == null)
			subScene = new GameSettingView(model, controller).getScene();
		pane.setCenter(subScene);


		scene = new Scene(mainPane, 300, 300);


	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}

	protected void showGameSettingView() {
		pane.getChildren().remove(subScene);
		GameSettingView view = new GameSettingView(model, controller);
		subScene = view.getScene();
		pane.setCenter(subScene);
		refreshActualSettingData();
	}

	protected void showAudioSettingView() {
		pane.getChildren().remove(subScene);
		AudioSettingView view = new AudioSettingView(model, controller);
		subScene = view.getScene();
		pane.setCenter(subScene);
		refreshActualSettingData();
	}

	private void refreshActualSettingData() {
		controller.refreshActualSettingData();
	}
}

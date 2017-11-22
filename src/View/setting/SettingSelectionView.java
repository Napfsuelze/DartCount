package View.setting;

import Controller.Controller;
import Model.Model;
import View.setting.AudioSettingView;
import View.setting.GameSettingView;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SettingSelectionView {
	
	private Model model;
	private Button btnGame;
	private GridPane mainPane;
	private Scene scene;
	private Button btnDetection;
	private Button btnAudio;
	private Button btnBack;
	private Controller controller;
	private SubScene subScene;

	public SettingSelectionView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		buildLayout();
	}

	private void buildLayout() {
		//Pane__________________________
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		
		
		BorderPane pane = new BorderPane();
		MenuBar menu = new MenuBar();
		Menu menuGameSetting = new Menu("Game");
		Menu menuAudioSetting = new Menu("Audio");
		Menu menuOpenCVSetting = new Menu("OpenCV");
		menu.getMenus().addAll(menuGameSetting, menuAudioSetting, menuOpenCVSetting);
		pane.setTop(menu);
		if (subScene == null)
			subScene = new GameSettingView(model, controller).getScene();
		pane.setLeft(subScene);
		
		menuGameSetting.setOnShowing(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				showGameSettingView();
				System.out.println("Test");
			}
			
		});
		
		menuAudioSetting.setOnShowing(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				showAudioSettingView();
			}
			
		});
		menuGameSetting.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showGameSettingView();
			}
			
		});
		
		menuAudioSetting.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showAudioSettingView();
			}
			
		});
		
		
		
		//UI Komponenten________________
		btnBack = new Button("<- Back");
		GridPane.setHalignment(btnBack, HPos.CENTER);
		GridPane.setValignment(btnBack, VPos.CENTER);
		GridPane.setMargin(btnBack, new Insets(10));
		
		btnGame = new Button("Game Settings");
		GridPane.setHalignment(btnGame, HPos.CENTER);
		GridPane.setValignment(btnGame, VPos.CENTER);
		GridPane.setMargin(btnGame, new Insets(10));
		
		btnDetection = new Button("Score Detection");
		GridPane.setHalignment(btnDetection, HPos.CENTER);
		GridPane.setValignment(btnDetection, VPos.CENTER);
		GridPane.setMargin(btnDetection, new Insets(10));
		
		btnAudio = new Button("Audio");
		GridPane.setHalignment(btnAudio, HPos.CENTER);
		GridPane.setValignment(btnAudio, VPos.CENTER);
		GridPane.setMargin(btnAudio, new Insets(10));
		
		//Scene_________________________
		mainPane.add(btnBack, 0, 0);
		mainPane.add(btnGame, 0, 1);
		mainPane.add(btnDetection, 0, 2);
		mainPane.add(btnAudio, 0, 3);
		scene = new Scene(pane);
	}
	
	protected void showAudioSettingView() {
		AudioSettingView view = new AudioSettingView(model, controller);
		subScene = view.getScene();
		refreshScene();
		System.out.println("hi");
	}

	protected void showGameSettingView() {
		System.out.println("hi");
		GameSettingView view = new GameSettingView(model, controller);
		subScene = view.getScene();
		refreshScene();
	}

	public Scene getScene() {
		return scene;
	}
	
	private void refreshScene() {
		buildLayout();
		getActualModelData();
		
	}
	
	private void getActualModelData() {
		// TODO Auto-generated method stub
		
	}

	public void addGameListener(EventHandler<ActionEvent> e) {
		btnGame.setOnAction(e);
	}
	
	public void addDetectionListener(EventHandler<ActionEvent> e) {
		btnDetection.setOnAction(e);
	}
	
	public void addAudioListener(EventHandler<ActionEvent> e) {
		btnAudio.setOnAction(e);
	}
	
	public void addBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}
}

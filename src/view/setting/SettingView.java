package view.setting;

import model.AppConstants;
import model.Model;
import controller.Controller;

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
import javafx.scene.layout.GridPane;

/**
 * View für das SettingView. Beinhaltet als SubScene die verschiedenen Settings
 * TODO Back Button Realisation???
 */
public class SettingView {
	
	private Model model;
	private Controller controller;
	private Scene scene;
	private GridPane mainPane;
	private Button btnBack;
	private SubScene subScene;
	private MenuBar menu;

    /**
     * Konstruktor
     * @param model
     *          Referenz zum Model
     * @param controller
     *          Referenz zum Controller
     */
	public SettingView(Model model, Controller controller) {
		this.model = model;
		this.controller =  controller;
		buildLayout();
	}
	
	private void buildLayout() {
		//Pane__________________________
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setGridLinesVisible(true);


        menu = new MenuBar();
        GridPane.setFillWidth(menu, true);

		Menu menuBack = new Menu("Back");

        //CRAZY WORKAROUND
        MenuItem backItem = new MenuItem();
        menuBack.getItems().add(backItem);
        menuBack.addEventHandler(Menu.ON_SHOWN, event -> menuBack.hide());
        menuBack.addEventHandler(Menu.ON_SHOWING, event -> menuBack.fire());

        menuBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.showStartView();
            }
        });

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

		menu.getMenus().addAll(menuBack, menuGameSetting, menuAudioSetting, menuOpenCVSetting);
				
		//UI Komponenten________________
		btnBack = new Button("<- Back");
		GridPane.setHalignment(btnBack, HPos.LEFT);
		GridPane.setValignment(btnBack, VPos.CENTER);
		GridPane.setMargin(btnBack, new Insets(10));
		
		//Scene_________________________
        mainPane.add(menu, 0, 0);


		if (subScene == null)
			subScene = new GameSettingView(model, controller).getScene();
        mainPane.add(subScene, 0, 1);

		scene = new Scene(mainPane);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}

	protected void showGameSettingView() {
		mainPane.getChildren().remove(subScene);
		GameSettingView view = new GameSettingView(model, controller);
		subScene = view.getScene();
        //GridPane mainPane = view.getMainPane();
        //subScene = new SubScene(mainPane, AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT - menu.getHeight());
        mainPane.add(subScene, 0, 1);
		refreshActualSettingData();
	}

	protected void showAudioSettingView() {
        mainPane.getChildren().remove(subScene);
		AudioSettingView view = new AudioSettingView(model, controller);
		subScene = view.getScene();
        mainPane.add(subScene, 0, 1);
		refreshActualSettingData();
	}

	private void refreshActualSettingData() {
		controller.refreshActualSettingData();
	}
}

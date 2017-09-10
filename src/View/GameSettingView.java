package View;

import Model.Model;
import Controller.Controller;
import Model.AppConstants;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameSettingView {
	
	private Model model;
	private Controller controller;
	private GridPane mainPane;
	private RadioButton game301;
	private RadioButton game501;
	private final ToggleGroup gameGroup, outGroup;
	private SubScene scene;
	private RadioButton straightOut;
	private RadioButton doubleOut;
	private Button btnBack;
	private ToggleGroup scoreInput;
	private RadioButton inputDirect;
	private RadioButton inputDartSeperated;
	private RadioButton inputOpenCV;

	public GameSettingView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		gameGroup = new ToggleGroup();
		outGroup = new ToggleGroup();
		scoreInput = new ToggleGroup();
		buildLayout();
	}

	private void buildLayout() {
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		
		btnBack = new Button("<- Back");
		GridPane.setHalignment(btnBack, HPos.CENTER);
		GridPane.setValignment(btnBack, VPos.CENTER);
		GridPane.setMargin(btnBack, new Insets(10));
		
		game301 = new RadioButton("301");
		game501 = new RadioButton("501");
		game301.setToggleGroup(gameGroup);
		game301.selectedProperty().set(true);
		game501.setToggleGroup(gameGroup);
		gameGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton btn = (RadioButton) newValue;
				model.setGameType(Integer.valueOf(btn.getText()));
			}

		});
		GridPane.setMargin(game301, new Insets(10));
		GridPane.setMargin(game501, new Insets(10));
		
		straightOut = new RadioButton("Straight Out");
		doubleOut = new RadioButton("Double Out");
		straightOut.setToggleGroup(outGroup);
		straightOut.selectedProperty().set(true);
		doubleOut.setToggleGroup(outGroup);
		outGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				RadioButton btn = (RadioButton) arg2;
				if (btn.getText().equals("Straight Out"))
					model.setFinishType(AppConstants.GAME_TYPE_STRAIGHT_OUT);
				if (btn.getText().equals("Double Out"))
					model.setFinishType(AppConstants.GAME_TYPE_DOUBLE_OUT);
			}
			
		});
		GridPane.setMargin(straightOut, new Insets(10));
		GridPane.setMargin(doubleOut, new Insets(10));
		
		inputDirect = new RadioButton("Direct");
		inputDartSeperated = new RadioButton("Dart Seperated");
		inputOpenCV = new RadioButton("OpenCV");
		inputDirect.setToggleGroup(scoreInput);
		inputDirect.selectedProperty().set(true);
		inputDartSeperated.setToggleGroup(scoreInput);
		inputOpenCV.setToggleGroup(scoreInput);
		
		scoreInput.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				RadioButton btn = (RadioButton) arg2;
				if (btn.getText().equals("Direct")) {
					model.setScoreInputType(Model.ScoreInputType.DIRECT);
					controller.loadScoreInputView(Model.ScoreInputType.DIRECT);
				}
				if (btn.getText().equals("Dart Seperated")) {
					model.setScoreInputType(Model.ScoreInputType.DARTSEPERATED);
					controller.loadScoreInputView(Model.ScoreInputType.DARTSEPERATED);
				}
				if (btn.getText().equals("OpenCV")) {
					model.setScoreInputType(Model.ScoreInputType.OPENCV);
					controller.loadScoreInputView(Model.ScoreInputType.OPENCV);
				}
				
			}	
		});
		
		VBox input = new VBox();
		input.getChildren().addAll(inputDirect, inputDartSeperated, inputOpenCV);
		GridPane.setMargin(input, new Insets(10));
		
		VBox out = new VBox();
		out.getChildren().addAll(straightOut, doubleOut);
		GridPane.setMargin(out, new Insets(10));
		
		VBox points = new VBox();
		points.getChildren().addAll(game301, game501);
		GridPane.setMargin(points, new Insets(10));
		
		mainPane.add(btnBack, 0, 1);
		mainPane.add(points, 0, 0);
		mainPane.add(out, 1, 0);
		mainPane.add(input, 2, 0);
		scene = new SubScene(mainPane, 600, 400);
	}
	
	public SubScene getScene() {
		return scene;
	}
	
	public void addBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}
}
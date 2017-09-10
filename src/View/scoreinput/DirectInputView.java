package View.scoreinput;

import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Model.Dart;
import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DirectInputView {
	private Model model;
	private Controller controller;
	private SubScene scene;
	private List<Button> buttonList;
	private Label lbScore;
	
	public DirectInputView(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		buttonList = new ArrayList<>();
		lbScore = new Label("");
		buildLayout();
	}

	private void buildLayout() {
		// Keyboard
		//-----------------------------------
		for (int i = 0; i <= 9; i++) {
			Button button = new Button(String.valueOf(i));
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					if (lbScore.getText().length() < 3) {
						lbScore.setText(lbScore.getText() + String.valueOf(button.getText()));
					}
				}
				
			});
			buttonList.add(button);
		}
		
		Button delete = new Button("<");
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				lbScore.setText(lbScore.getText().substring(0, lbScore.getText().length() - 1));
			}
			
		});
		Button enter = new Button("Submit");
		enter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!lbScore.getText().equals("")) {
					model.throwDart(new Dart(Integer.valueOf(lbScore.getText()), false, false));
					controller.checkFinish();
					
				}
				lbScore.setText(new String(""));
			}			
		});
		
		HBox upper = new HBox();
		HBox middle = new HBox();
		HBox lower = new HBox();
		HBox controll = new HBox();
		
		for (int i = 1; i < 4; i++)
			upper.getChildren().add(buttonList.get(i));
		for (int i = 4; i < 7; i++)
			middle.getChildren().add(buttonList.get(i));
		for (int i = 7; i < 10; i++)
			lower.getChildren().add(buttonList.get(i));
		
		controll.getChildren().add(delete);
		controll.getChildren().add(buttonList.get(0)); // 0 Button
		controll.getChildren().add(enter);
		
		VBox keyboard = new VBox();
		keyboard.getChildren().addAll(lbScore, upper, middle, lower, controll);
		
		scene = new SubScene(keyboard, 200, 300);

	}
	
	public SubScene getScene() {
		return scene;
	}
}

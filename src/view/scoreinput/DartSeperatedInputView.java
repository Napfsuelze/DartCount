package view.scoreinput;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Dart;
import model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Input view zur Eingabe jedes einzelnen geworfenen Darts
 */
public class DartSeperatedInputView {
	private Model model;
	private Controller controller;
	private SubScene scene;
	private Label lbScore1;
	private Label lbScore2;
	private Label lbScore3;
	private int dartCount;
	private boolean isDouble;
	private boolean isTriple;
	private Dart dart1;
	private Dart dart2;
	private Dart dart3;
	private Dart activeDart;
	private boolean dartCountend;

    /**
     * Konstruktor
     * @param model
     *          Referenz zum model
     * @param controller
     *          Referenz zum controller
     */
	public DartSeperatedInputView(Model model, Controller controller){
		this.model = model;
		this.controller = controller;
		lbScore1 = new Label("");
		lbScore2 = new Label("");
		lbScore3 = new Label("");
		dart1 = new Dart();
		dart2 = new Dart();
		dart3 = new Dart();
		activeDart = dart1;
		isDouble = false;
		isTriple = false;
		dartCountend = false;
		dartCount = 1;
		buildLayout();
	}

	private void buildLayout() {
		List<Button> buttonList = new ArrayList<>();
		for (int i = 1; i < 21; i++) {
			Button button = new Button(String.valueOf(i));
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					defineDart(Integer.valueOf(button.getText()));
				}	
			});
			button.setMinSize(40, 30);
			buttonList.add(button);
		}
		
		VBox keyboard = new VBox();
		HBox labelBox = new HBox(lbScore1, lbScore2, lbScore3);
		HBox.setMargin(lbScore1, new Insets(20));
		HBox.setMargin(lbScore2, new Insets(20));
		HBox.setMargin(lbScore3, new Insets(20));
		keyboard.getChildren().addAll(labelBox);
		
		Button btnDouble = new Button("Double");
		Button triple = new Button("Triple");
		btnDouble.setMinSize(100, 30);
		triple.setMinSize(100, 30);
		btnDouble.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (isDouble) {
					isDouble = false;
					activeDart.setDoub(false);
					updateLabels();
					return;
				}
				isDouble = true;
				isTriple = false;
				activeDart.setTrip(false);
				activeDart.setDoub(true);
				updateLabels();
			}
		});
		
		triple.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (isTriple) {
					isTriple = false;
					activeDart.setTrip(false);
					updateLabels();
					return;
				}
				if (activeDart.getScore() == 25)
					return;
				isDouble = false;
				activeDart.setDoub(false);
				activeDart.setTrip(true);
				isTriple = true;
				updateLabels();
			}		
		});
		
		keyboard.getChildren().addAll(new HBox(btnDouble, triple));
		
		
		for (int i = 0; i < buttonList.size(); i += 5) {
			HBox row = new HBox();
			row.getChildren().addAll(buttonList.get(i), buttonList.get(i + 1), buttonList.get(i + 2), buttonList.get(i + 3), buttonList.get(i + 4));
			keyboard.getChildren().add(row);
		}
		
		Button btn25 = new Button("25");
		btn25.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				defineDart(Integer.valueOf(btn25.getText()));
			}
			
		});
		
		Button btn0 = new Button("Miss");
		btn0.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				defineDart(0);
			}
		});
		
		Button nextDart = new Button("Next");
		nextDart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (dartCount == 1) {
					dartCount++;
					activeDart = dart2;
					resetBooleans();
					return;
				} else if (dartCount == 2) {
					dartCount++;
					dartCountend = true;
					activeDart = dart3;
					resetBooleans();
					return;
				}
			}
			
		});
		
		Button prevDart = new Button("Previous");
		prevDart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (dartCount == 2) {
					dartCount--;
					activeDart = dart1;
					resetBooleans();
					return;
				}
				
				if (dartCount == 3) {
					dartCount--;
					activeDart = dart2;
					resetBooleans();
					return;
				}
			}
			
		});
		
		
		HBox row = new HBox();
		row.getChildren().addAll(btn25, btn0, nextDart, prevDart);
		Button submit = new Button("Submit Darts");
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (dartCountend) {
					model.throwDart(dart1);
					model.throwDart(dart2);
					model.throwDart(dart3);
					controller.checkFinish();
					clearDarts();
					updateLabels();
				}
			}
			
		});
		keyboard.getChildren().add(row);
		keyboard.getChildren().add(submit);
		
		scene = new SubScene(keyboard, 300, 400);
	}
	
	protected void defineDart(Integer score) {
		if (isDouble) {
			activeDart.setTrip(false);
			activeDart.setScore(score);
			activeDart.setDoub(true);
		} else if (isTriple) {
			activeDart.setDoub(false);
			activeDart.setScore(score);
			activeDart.setTrip(true);
		} else {
			activeDart.setDoub(false);
			activeDart.setTrip(false);
			activeDart.setScore(score);
		}
		updateLabels();
	}
	
	private void updateLabels() {
		lbScore1.setText(dart1.toString());
		lbScore2.setText(dart2.toString());
		lbScore3.setText(dart3.toString());
	}
	
	private void resetBooleans() {
		isDouble = false;
		isTriple = false;
	}
	
	private void clearDarts() {
		dart1.setDoub(false);
		dart1.setTrip(false);
		dart1.setScore(0);
		dart2.setDoub(false);
		dart2.setTrip(false);
		dart2.setScore(0);
		dart3.setDoub(false);
		dart3.setTrip(false);
		dart3.setScore(0);
		activeDart = dart1;
	}

	public SubScene getScene() {
		return scene;
	}
}

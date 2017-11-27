package view;

import model.Model;
import model.Player;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class PlayerView {
	
	private Scene scene;
	private Button btnAdd, btnBack;
	private GridPane mainPane;
	private Model model;
	private ListView<Player> playerView;
	private TextField txtAdd;
	private Button btnRemove;
	
	public PlayerView(Model model) {
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
		
		btnAdd = new Button("Add Player");
		GridPane.setHalignment(btnAdd, HPos.CENTER);
		GridPane.setValignment(btnAdd, VPos.CENTER);
		GridPane.setMargin(btnAdd, new Insets(10));
		
		btnRemove = new Button("Remove Selected Player");
		GridPane.setHalignment(btnRemove, HPos.CENTER);
		GridPane.setValignment(btnRemove, VPos.CENTER);
		GridPane.setMargin(btnRemove, new Insets(10));
		GridPane.setColumnSpan(btnRemove, 2);
		
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.removePlayer(playerView.getSelectionModel().getSelectedItem());
			}
		});
		
		playerView = new ListView<>();
		playerView.setMinSize(200, 100);
		playerView.setCellFactory(CheckBoxListCell.forListView(new Callback<Player, ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(Player arg0) {
				return arg0.onProperty();
			}
			
		}));

		
		GridPane.setHalignment(playerView, HPos.CENTER);
		GridPane.setValignment(playerView, VPos.CENTER);
		GridPane.setMargin(playerView, new Insets(10));
		GridPane.setColumnSpan(playerView, 2);
		bindListView(model.getPlayerList());
		
		txtAdd = new TextField("Name");
		GridPane.setHalignment(txtAdd, HPos.CENTER);
		GridPane.setValignment(txtAdd, VPos.CENTER);
		GridPane.setMargin(txtAdd, new Insets(10));
		
		//Scene_________________________
		mainPane.add(btnAdd, 1, 2);
		mainPane.add(playerView, 0, 1);
		mainPane.add(btnBack, 0, 0);
		mainPane.add(txtAdd, 0, 2);
		mainPane.add(btnRemove, 0, 3);
		
		scene = new Scene(mainPane);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void setBackListener(EventHandler<ActionEvent> e) {
		btnBack.setOnAction(e);
	}
	
	public void bindListView(ObservableList<Player> items) {
		playerView.setItems(items);
	}
	
	public void setAddPlayerListener(EventHandler<ActionEvent> e) {
		btnAdd.setOnAction(e);
	}
	
	public String getAddPlayerName() {
		return txtAdd.getText();
	}
}

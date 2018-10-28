package view;

import javafx.scene.layout.VBox;
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
	private TextField txtAdad;
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
        playerView = new ListView<>();
        playerView.setMinSize(200, 100);
        playerView.setCellFactory(CheckBoxListCell.forListView(new Callback<Player, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(Player arg0) {
				int highestOrder = PlayerView.this.model.getHighestOrder();
				arg0.setOrder(highestOrder);

                return arg0.onProperty();
            }

        }));


        GridPane.setHalignment(playerView, HPos.CENTER);
        GridPane.setValignment(playerView, VPos.CENTER);
        GridPane.setMargin(playerView, new Insets(10));
        GridPane.setColumnSpan(playerView, 2);
        bindListView(model.getPlayerList());

        txtAdad = new TextField("Name");
        GridPane.setMargin(txtAdad, new Insets(10));
        GridPane.setHalignment(txtAdad, HPos.CENTER);
        GridPane.setValignment(txtAdad, VPos.CENTER);

		btnAdd = new Button("Add Player");
        btnAdd.getStyleClass().add("button-navigate");
		GridPane.setHalignment(btnAdd, HPos.CENTER);
		GridPane.setValignment(btnAdd, VPos.CENTER);
		GridPane.setMargin(btnAdd, new Insets(10));

		
		btnRemove = new Button("Remove Selected Player");
        btnRemove.getStyleClass().add("button-navigate");
		GridPane.setHalignment(btnRemove, HPos.CENTER);
		GridPane.setValignment(btnRemove, VPos.CENTER);
		GridPane.setMargin(btnRemove, new Insets(10));
		
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.removePlayer(playerView.getSelectionModel().getSelectedItem());
			}
		});

        btnBack = new Button("Back");
        btnBack.getStyleClass().add("button-navigate");
        GridPane.setMargin(btnBack, new Insets(10));

        VBox buttonBox = new VBox(btnRemove, btnBack);
        buttonBox.setPrefWidth(300);

        btnRemove.setPrefWidth(buttonBox.getPrefWidth());
        btnBack.setPrefWidth(buttonBox.getPrefWidth());

		GridPane controlPane = new GridPane();
        controlPane.add(txtAdad, 0, 0);
        controlPane.add(btnAdd, 1, 0);
        controlPane.add(buttonBox, 0, 1);
        GridPane.setColumnSpan(buttonBox, 2);




        //Scene_________________________
        mainPane.add(playerView, 0, 0);
        mainPane.add(controlPane, 0, 1);



		
		scene = new Scene(mainPane);
        System.out.println(scene.getWidth());
        scene.getStylesheets().add(getClass().getResource("/styleclass.css").toString());
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
		return txtAdad.getText();
	}
}



import controller.Controller;
import javafx.application.*;
import javafx.stage.Stage;

public class DartCountApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Controller controller = new Controller(primaryStage);
		primaryStage.setTitle("Count your Darts");
		primaryStage.setHeight(1080);
		primaryStage.setWidth(1920);
		controller.showStartView();
		primaryStage.show();
	}

}

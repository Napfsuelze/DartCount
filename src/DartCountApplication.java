

import controller.Controller;
import javafx.application.*;
import javafx.stage.Stage;
import model.AppConstants;

public class DartCountApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Controller controller = new Controller(primaryStage);
		primaryStage.setTitle("Count your Darts");
		primaryStage.setHeight(AppConstants.APP_HEIGHT);
		primaryStage.setWidth(AppConstants.APP_WIDTH);
		controller.showStartView();
		primaryStage.show();
	}

}

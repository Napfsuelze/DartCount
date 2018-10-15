
import controller.Controller;
import javafx.application.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DartCountApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Controller controller = new Controller(primaryStage);

		//Add the css style global
		/*Application.setUserAgentStylesheet(null);
		StyleManager.getInstance().addUserAgentStylesheet(STYLESHEET_MODENA);*/
        //StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("styleclass.css").toString());

		primaryStage.setTitle("Count your Darts");
        Font.loadFont(getClass().getResource("/fontawesome-webfont.ttf").toExternalForm(), 12);
        controller.showStartView();
		primaryStage.show();
	}

}

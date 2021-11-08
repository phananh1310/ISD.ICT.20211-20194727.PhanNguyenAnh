package homeScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;  
import javafx.scene.*;

public class Main extends Application{
    
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeScreen/HomeScreen.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Home Screen");
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}

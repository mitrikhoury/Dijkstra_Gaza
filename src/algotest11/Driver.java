package algotest11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    launch(args);
    
	}
	

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/myScene.fxml"));
		Scene s =new Scene(root);
		arg0.setScene(s);
		arg0.show();
	}

}

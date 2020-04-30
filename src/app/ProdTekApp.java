package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import app.ProdTekAppController;
import javafx.stage.Stage;



public class ProdTekApp extends Application{
	Stage stage;
	@Override
	public void start(final Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		String scene = "ProdTekApp.fxml";
		stage.setTitle(scene);
		Scene secScene =  new Scene(FXMLLoader.load(getClass().getResource(scene)));
		stage.setScene(secScene);
		stage.show();	
	
	}

	public Stage getStage(){
		return stage;
	}
	
	public static void main(final String[] args) {
		ProdTekApp.launch(args);
	}

	
}


package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AppLauncher {
	static String staticpath = "-1";
	boolean launchedProdTekApp = false;
	Application app;
	ProdTekApp papp = new ProdTekApp();
	
	
	public AppLauncher(){
		app.launch(DragAndDrop.class);
		ProdTekApp papp = new ProdTekApp();
					
	}
	
	synchronized void launchProdTekApp(){
		//venter til en path er hentet inn
		while(staticpath.equals("-1")){try{wait();}catch(InterruptedException e){e.printStackTrace();}}
		ProdTekAppController.setspmPath(staticpath); 				//setter path i controller
		System.out.println("applauncher mottok path: "+staticpath); //kjører bra hit
		Stage secondaryStage = new Stage();				//henter stage fra DragAndDropinstanse
		try {papp.start(secondaryStage);}catch (Exception e) {e.printStackTrace();}
		
		/*
		String scene = "ProdTekApp.fxml";							//bytter utseende til prodtek
		
		secondaryStage.setTitle(scene);
		try {
			Scene secScene = new Scene(FXMLLoader.load(getClass().getResource(scene)));
			secondaryStage.setScene(secScene);
			secondaryStage.show();
		} catch (IOException e1) {e1.printStackTrace();}
		*/
	}
	
	
	static void setPath(String path){
		System.out.println("path : "+path);
		staticpath = path;
	}
	
	public static void main(String[] args) {
		AppLauncher a = new AppLauncher();
	}
		
}

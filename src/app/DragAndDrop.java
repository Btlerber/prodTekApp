package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragAndDrop extends Application {
	static String path = "-1";
	boolean success = false;
	StackPane root = new StackPane();
	Scene scene = new Scene(root, 500, 250);
	static Stage stage;
	
    @Override
    public void start(Stage primaryStage) {
    	stage = primaryStage;
        Label label = new Label("Dra 'savefile.txt' filen inn i dette vinduet"
        		+ "\n ved velykket operasjon vil vinduet lukkes automagisk");
        Label dropped = new Label("");
        VBox dragTarget = new VBox();
        dragTarget.getChildren().addAll(label,dropped);
        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != dragTarget
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        
        //funksjon når man klarer å slippe filen
        dragTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
              
                if (db.hasFiles() && !success) {
                	String lokasjon = db.getFiles().toString();
                    dropped.setText(lokasjon);
                    success = true;
                    path = lokasjon;
                    lokasjon = lokasjon.substring(1,lokasjon.length()-1);
                    System.out.println("DragAndDrop sier: "+lokasjon);
                    AppLauncher.setPath(lokasjon);
                    primaryStage.close();
                    try {notify();} catch (Exception e) {}
                    Platform.exit(); //skal avslutte så ProdtekApp kan starte
                    launchProdTekApp();
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                try{
                	event.setDropCompleted(success);
                }catch (Exception e) {
					//System.out.println("handle får feil: "+e);
				}
            }
        });
        
        root.getChildren().add(dragTarget);
        
        primaryStage.setTitle("ProdTekApp_DragAndHover");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();
    }
    
    synchronized void launchProdTekApp(){
		//venter til en path er hentet inn
		while(path.equals("-1")){try{wait();}catch(InterruptedException e){e.printStackTrace();}}
		ProdTekAppController.setspmPath(path); 				//setter path i controller
		System.out.println("applauncher mottok path: "+path); //kjører bra hit
		String scene = "ProdTekApp.fxml";
		stage.setTitle(scene);
		Scene secScene;
		try {
			secScene = new Scene(FXMLLoader.load(getClass().getResource(scene)));
			stage.setScene(secScene);
			System.out.println("secScene ble satt");
		} catch (IOException e) {e.printStackTrace();}
		
    }
    
    
    
    public static String getPath(){
		return path;
    }
    
    public static Stage getStage(){
    	return stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
		DragAndDrop.launch(args);
	}

}
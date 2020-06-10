package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragAndDrop{
	static String path = "-1";
	boolean success = false;
	StackPane root = new StackPane();
	Scene scene = new Scene(root, 500, 250);
	Scene prevScene = null;
	static Stage stage;






	public DragAndDrop(Stage stage,Scene postDragnDropScene){
		this.stage = stage;
		this.prevScene = postDragnDropScene;
	}
	
	

	public void getPath() {


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
		EventHandler<? super DragEvent> eventhandler;
		eventhandler = new EventHandler<DragEvent>() {
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
					ProdTekAppController.spmPath = lokasjon;
					//try{notifyAll();}catch(Exception e) {e.printStackTrace();}
					stage.setScene(prevScene);
					stage.show();
					
				}
				/* let the source know whether the string was successfully 
				 * transferred and used */
				try{
					event.setDropCompleted(success);
				}catch (Exception e) {
					//System.out.println("handle får feil: "+e);
				}
			}
		};
		
		dragTarget.setOnDragOver(eventhandler);

		root.getChildren().add(dragTarget);

		stage.setTitle("ProdTekApp_DragAndHover");
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		

	}
}
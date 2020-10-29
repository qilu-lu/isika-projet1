package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		File file=new File("qilu/desktop/STAGIAIRES.DON");
		
		FileReader reader =new FileReader(file);
		BufferedReader br=new BufferedReader(reader);
		// LineNumberReader reader = new LineNumberReader(in); 
		br.read();
		
		
		
		
		
		launch(args);

		ArbreStagiaire1<Stagiaire> s = new ArbreStagiaire1<>();
		s.ajouterNoeud(new Stagiaire());
	}
}

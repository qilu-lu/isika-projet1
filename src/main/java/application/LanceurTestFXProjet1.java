package application;

import java.net.URL;

import application.controleurs.LoginControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class LanceurTestFXProjet1 extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginControleur controleur = new LoginControleur();
			URL resource = getClass().getClassLoader().getResource("Login.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setController(controleur);
			Pane root = loader.load();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			//scene.getStylesheets().add(getClass().getClassLoader().getResource("ressources/vues/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			controleur.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
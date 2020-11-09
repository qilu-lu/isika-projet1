package application.controleurs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.models.NouvelUtilisateurModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginControleur implements Initializable {

	private static final String VUE_AJOUT_NOUVEL_UTILISATEUR_VIEW_PATH = "NouvelUtilisateur.fxml";
	private static final String VUE_ACCUEIL_VIEW_PATH = "AccueilPrincipalAdmin.fxml";
	private static final String VUE_ACCUEIL2_VIEW_PATH = "AccueilPrincipalFormateur.fxml";
	@FXML
	private Button creerUnNouvelUtilisateur;
	@FXML
	private Button resetBtn;
	@FXML
	private Button okBtn;
	@FXML
	private Button quitterBtn;
	@FXML
	private TextField votreEMailTextField;
	@FXML
	private PasswordField votreMdpField;

	private Stage primaryStage;
	Alert alert = new Alert(AlertType.WARNING);

	class User {
		private String username;
		private String password;

		public User() {
		}
	}

	private List<User> loadUser() {
		List<User> users = new ArrayList<>();
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("utilisateursCrees.txt");
		try (Scanner scan = new Scanner(resourceAsStream)) {
			User user = new User();
			while (scan.hasNext()) {
				String ligne = scan.next();
				String codeEmploye = ligne.split("/")[0];
				String motDePasse = ligne.split("/")[1];
				user.username = codeEmploye;
				user.password = motDePasse;
				users.add(user);
				user = new User();
			}
		} catch (Exception e) {
		}
		return users;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		creerUnNouvelUtilisateur.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					afficherFenetreNouvelUtilisateur();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetAll();
			}
		});
		quitterBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				closeWindow();
			}
		});
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				verifierUtilisateur();
			}
		});
	}

	private void verifierUtilisateur() {
		String codeEmploye = votreEMailTextField.getText();
		String password = votreMdpField.getText();

		User userConnecte = null;
		List<User> users = loadUser();
		for (User user : users) {
			if (user.username.equals(codeEmploye) && user.password.equals(password)) {
				userConnecte = user;
				break;
			}
		}

		if (userConnecte != null) {
			if (userConnecte.username.startsWith("AD")) {
				try {
					allerVersAccueilPrincipal();
					primaryStage.hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					allerVersAccueilPrincipal2();
					primaryStage.hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			alert.setTitle("Attention");
			alert.setHeaderText("Veuillez entrer un utilisateur valide");
			alert.show();

		}
	}

	private void afficherFenetreNouvelUtilisateur() throws IOException {

		NouvelUtilisateurModel nUModel = new NouvelUtilisateurModel();
		CreerUtilisateurControleur controleur = new CreerUtilisateurControleur(this, nUModel);

		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource(VUE_AJOUT_NOUVEL_UTILISATEUR_VIEW_PATH));
		loader.setController(controleur);
		Pane rootPane = loader.load();

		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage ajoutNouvelUtilisateurStage = new Stage();
		ajoutNouvelUtilisateurStage.setTitle("Nouvel utilisateur");
		ajoutNouvelUtilisateurStage.setScene(scene);
		ajoutNouvelUtilisateurStage.show();
	}

	@FXML
	public void resetAll() {
		this.votreEMailTextField.clear();
		this.votreMdpField.clear();
	}

	@FXML
	public void closeWindow() {
		Platform.exit();
	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	public void afficher() {
		this.primaryStage.show();
	}

	// ACCES ADMINISTRATEUR
	@FXML
	public void allerVersAccueilPrincipal() throws IOException {

		AccueilPrincipalControleur controleur = new AccueilPrincipalControleur(this);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_ACCUEIL_VIEW_PATH));
		loader.setController(controleur);
		Pane rootPane = loader.load();

		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage accueilStage = new Stage();
		accueilStage.setTitle("Accueil");
		accueilStage.setScene(scene);
		accueilStage.show();

		controleur.setStage(accueilStage);
	}

	// ACCES FORMATEUR
	@FXML
	public void allerVersAccueilPrincipal2() throws IOException {

		AccueilPrincipalControleur controleur = new AccueilPrincipalControleur(this);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_ACCUEIL2_VIEW_PATH));
		loader.setController(controleur);
		Pane rootPane = loader.load();

		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage accueilStage = new Stage();
		accueilStage.setTitle("Accueil");
		accueilStage.setScene(scene);
		accueilStage.show();

		controleur.setStage(accueilStage);
	}

}
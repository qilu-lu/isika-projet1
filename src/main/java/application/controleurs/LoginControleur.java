package application.controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.models.NouvelUtilisateurModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginControleur implements Initializable {

	private static final String VUE_AJOUT_NOUVEL_UTILISATEUR_VIEW_PATH = "NouvelUtilisateur.fxml";
	private static final String VUE_ACCUEIL_VIEW_PATH = "AccueilPrincipal.fxml";

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
				try {
					allerVersAccueilPrincipal();
					primaryStage.hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}



	private void afficherFenetreNouvelUtilisateur() throws IOException {
		// TODO Auto-generated method stub

		NouvelUtilisateurModel nUModel = new NouvelUtilisateurModel();
		CreerUtilisateurControleur  controleur = new CreerUtilisateurControleur(this, nUModel);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_AJOUT_NOUVEL_UTILISATEUR_VIEW_PATH));
		loader.setController(controleur);
		Pane rootPane = loader.load();

		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage ajoutNouvelUtilisateurStage = new Stage();
		ajoutNouvelUtilisateurStage.setTitle("Ajout d'un nouvel utilisateur");
		ajoutNouvelUtilisateurStage.setScene(scene);
		ajoutNouvelUtilisateurStage.show();
	}
	@FXML
	public void resetAll() {
		this.votreEMailTextField.clear();
		this.votreMdpField.clear();
		System.out.println("all clear");
	}
	@FXML
	public void closeWindow() {
		Platform.exit();
		System.out.println("application quittée");
	}
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}
	public void afficher() {
		this.primaryStage.show();
	}


	@FXML
	public void allerVersAccueilPrincipal() throws IOException {
		//A FAIRE LOGIN OK CONNECTION

		// login (email+mdp comparer à la list) OK
		// comparer login si admin ou formateur
		// remplacer fenetre par AccueilPrincipal (admin ou non) 
		// vue admin avec bouton modifier et suppr stagiaire dans la list
		// vue utilisateur lambda sans bouton modifier et suppr stagiaire dans la list


		//		ActionListener ActionLog = new ActionListener() {
		//
		//			public void actionPerformed(ActionEvent actionEvent) {
		//				String NomUtilisateur = (String) votreEMail.getText();
		//				char[] MotDePasse  = votreMdp.getPassword();
		//				String txtMotDePasse = new String(MotDePasse);
		//				System.out.println("Nom d'utilisateur entrer : "+ NomUtilisateur);
		//				System.out.println("Mot de passe : "+ txtMotDePasse);
		//				connection.Log(NomUtilisateur, txtMotDePasse);
		//			};
		//
		//			{
		//				okBtn.addActionListener(ActionLog);
		//			}



		//		ConnexionModel coModel = new ConnexionModel();
		AccueilPrincipalControleur  controleur = new AccueilPrincipalControleur(this);

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
}
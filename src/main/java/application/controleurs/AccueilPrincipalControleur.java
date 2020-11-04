package application.controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.models.Stagiaire;
import application.models.StagiairesModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class AccueilPrincipalControleur implements Initializable {
	private static final String VUE_AJOUT_STAGIAIRE_VIEW_PATH = "AjoutStagiaire.fxml";

	private static final int INDEX_COLONNE_NOM = 0;
	private static final int INDEX_COLONNE_PRENOM = 1;
	private static final int INDEX_COLONNE_DEPARTEMENT = 2;
	private static final int INDEX_COLONNE_PROMOTION = 3;
	private static final int INDEX_COLONNE_ANNEE = 4;

	@FXML
	private MenuItem decoMenuBtn;

	@FXML
	private MenuItem quitMenuBtn;

	@FXML
	private MenuItem ajoutMenuBtn;

	@FXML
	private Button ajoutBtn;

	@FXML
	private Button modifBtn;

	@FXML
	private Button supprBtn;

	@FXML
	private Button rechercheBtn;
	
	@FXML
	private Button resetBtn;

	@FXML
	private Button quitBtn;

	@FXML
	private Button decoBtn;

	@FXML
	private TextField nomRechercheTextField;

	@FXML
	private TextField departementRechercheBtn;

	@FXML
	private TextField promotionRechercheBtn;

	@FXML
	private TextField anneeRechercheBtn;

	@FXML
	private TableView<Stagiaire> stagiairesTable;

	@FXML
	private TableColumn<Stagiaire, String> NOM;

	@FXML
	private TableColumn<Stagiaire, String> PRENOM;

	@FXML
	private TableColumn<Stagiaire, String> DEPARTEMENT;

	@FXML
	private TableColumn<Stagiaire, String> PROMOTION;

	@FXML
	private TableColumn<Stagiaire, Integer> ANNEE;


	private StagiairesModel modeleGlobalStagiaires;
	private ObservableList<Stagiaire> listeDynamiqueStagiaires;

	private LoginControleur vueAccueil;

	private Stage primaryStage;

	public AccueilPrincipalControleur(LoginControleur vueAccueil) {
		this.vueAccueil = vueAccueil;
	}



	public LoginControleur getvueAccueil() {
		return vueAccueil;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		modeleGlobalStagiaires = new StagiairesModel();

		//init la table
		initStagiairesTable();

		//Ajout de l'action du bouton ajouter
		ajoutBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					afficherFenetreAjoutStagiaire();
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

		quitMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				closeWindow();

			}
		});

		quitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				closeWindow();

			}
		});

		//		decoMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
		//
		//			@Override
		//			public void handle(ActionEvent event) {
		//				try {
		//					goToLogin();
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//				}
		////TODO : impl�menter goToLogin
		//			}
		//		});

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					goToLogin();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};

		decoMenuBtn.setOnAction(eventHandler);
		decoBtn.setOnAction(eventHandler);
	}


	public void afficherFenetreAjoutStagiaire() throws IOException {

		// On charge le fichier FXML pour la vue "Ajout stagiaire" et on lui associe son controleur 
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_AJOUT_STAGIAIRE_VIEW_PATH));
		loader.setController(this);

		// On charge l'élément parent de la vue (qui est un panel)
		Pane rootPane = loader.load();

		// Pour pouvoir agir sur la fenêtre créée on associe le stage au controleur de gestion des produits
		creeEtAfficheFenetreAjoutStagiaire(rootPane);
	}

	private void creeEtAfficheFenetreAjoutStagiaire(Pane rootPane) {
		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage ajoutStagiaireStage = new Stage();
		ajoutStagiaireStage.setTitle("Ajout d'un nouveau Stagiaire");
		ajoutStagiaireStage.setScene(scene);
		ajoutStagiaireStage.show();
	}

	@FXML
	public void resetAll() {
		this.nomRechercheTextField.clear();
		this.departementRechercheBtn.clear();
		this.promotionRechercheBtn.clear();
		this.anneeRechercheBtn.clear();
		System.out.println("all clear");
	}
	
	@FXML
	public void goToLogin() throws IOException {
		// TODO Auto-generated method stub
		//Stage stage = (Stage) retour.getScene.getWindow(); 
		//stage.close(); 
		System.out.println("retour vers Login");
		primaryStage.hide();
		vueAccueil.afficher();
	}
	
	@FXML
	public void closeWindow() {
		Platform.exit();
		System.out.println("application quitt�");
	}


	@SuppressWarnings("unchecked")
	private void initStagiairesTable() {

		// On récupère les colonnes par index (0 : nom, 1 : prenom .... 4 : annee)		
		TableColumn<Stagiaire, String> nomCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns().get(INDEX_COLONNE_NOM);
		TableColumn<Stagiaire, String> prenomCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns().get(INDEX_COLONNE_PRENOM);
		TableColumn<Stagiaire, String> departementCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns().get(INDEX_COLONNE_DEPARTEMENT);
		TableColumn<Stagiaire, String> promotionCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns().get(INDEX_COLONNE_PROMOTION);
		TableColumn<Stagiaire, Integer> anneeCol = (TableColumn<Stagiaire, Integer>) stagiairesTable.getColumns().get(INDEX_COLONNE_ANNEE);

		// On associe les colonnes de la table aux champs de l'objet qu'elle vont
		// contenir
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));
		promotionCol.setCellValueFactory(new PropertyValueFactory<>("promotion"));
		anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));

		listeDynamiqueStagiaires = FXCollections.observableArrayList(this.modeleGlobalStagiaires.getStagiaires());
		//		stagiairesTable.setItems(listeDynamiqueStagiaires);
	}
	public void mettreAJourModele(Stagiaire stagiaire) {
		// TODO Auto-generated method stub
		modeleGlobalStagiaires.ajouterUnStagiaire(stagiaire);
	}
	public void mettreAJourTable(Stagiaire stagiaire) {
		// On ajout le stagiaire à la liste de la table
		listeDynamiqueStagiaires.add(stagiaire);

		// On demande un refresh de la TableView
		stagiairesTable.refresh();



	}



	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}

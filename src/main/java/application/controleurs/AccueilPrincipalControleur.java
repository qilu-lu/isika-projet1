package application.controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JTable;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private MenuItem exporterEnPdfMenuBtn;
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
	private TableColumn<Stagiaire, String> nom;
	@FXML
	private TableColumn<Stagiaire, String> prenom;
	@FXML
	private TableColumn<Stagiaire, String> departement;
	@FXML
	private TableColumn<Stagiaire, String> promotion;
	@FXML
	private TableColumn<Stagiaire, Integer> annee;


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

		modeleGlobalStagiaires = new StagiairesModel();
		initStagiairesTable();

		EventHandler<ActionEvent> ajout = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					afficherFenetreAjoutStagiaire();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		ajoutBtn.setOnAction(ajout);
		ajoutMenuBtn.setOnAction(ajout);

		EventHandler<ActionEvent> quitter = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				closeWindow();
			}
		};
		quitBtn.setOnAction(quitter);
		quitMenuBtn.setOnAction(quitter);

		EventHandler<ActionEvent> deco = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					goToLogin();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		decoMenuBtn.setOnAction(deco);
		decoBtn.setOnAction(deco);

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetAll();
			}
		});
		modifBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modifierLine();
			}
		});
		supprBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				supprimerLine();
			}
		});
		exporterEnPdfMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				imprimerListeFiltree();
			}
		});
	}


	public void afficherFenetreAjoutStagiaire() throws IOException { 
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_AJOUT_STAGIAIRE_VIEW_PATH));
		loader.setController(this);
		Pane rootPane = loader.load();
		creeEtAfficheFenetreAjoutStagiaire(rootPane);
	}
	private void creeEtAfficheFenetreAjoutStagiaire(Pane rootPane) {
		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage ajoutStagiaireStage = new Stage();
		ajoutStagiaireStage.setTitle("Ajout d'un nouveau Stagiaire");
		ajoutStagiaireStage.setScene(scene);
		ajoutStagiaireStage.show();
	}

	private void modifierLine() {
		// TODO MODIFIER LA LIGNE
		//TODO Modifier dans l'arbre
		//selection de la ligne
		//ajouter fenetre (ajoutstagiaire) de cr�ation stagiaire
		//champ prérempli
		//modifier et accepter
		//mettre à jour liste stagiaire tableau refresh
		Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
		if(stagiaire != null) {
			try {
				afficherFenetreAjoutStagiaire();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			


			stagiairesTable.refresh();
		}

	}

	private void supprimerLine() {
		// TODO SUPPRIMER LA LIGNE
		//TODO supprimer dans l'arbre
		//selection de la ligne
		//supprimer tous les champs et ligne complete (attention suppr noeud binaire?)
		//mettre à jour la liste tableau refresh
		Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
		if(stagiaire != null) {
			listeDynamiqueStagiaires.remove(stagiaire);
			stagiairesTable.refresh();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression d'un stagiaire");
			alert.setHeaderText("Veuillez sélectionner la ligne à supprimer");
			alert.show();
		}
	}

	private void imprimerListeFiltree() {
		// TODO : Faire la méthode exporter en PDF
		//		MessageFormat header = new MessageFormat("Liste des Stagiaires :");
		//		MessageFormat footer = new MessageFormat("Page{0,number,integer}");
		//		try {
		//			listeDynamiqueStagiaires.print(JTable.PrintMode.NORMAL, header, footer);
		//			
		//		}catch(java.awt.print.PrinterException e) {
		//			System.err.format("Erreur d'impression ",  e.getMessage());
		//		}
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
		System.out.println("retour vers Login");
		primaryStage.hide();
		vueAccueil.afficher();
	}
	@FXML
	public void closeWindow() {
		Platform.exit();
		System.out.println("application quittée");
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
		stagiairesTable.setItems(listeDynamiqueStagiaires);
	}
	public void mettreAJourModele(Stagiaire stagiaire) {
		modeleGlobalStagiaires.ajouterUnStagiaire(stagiaire);
	}
	public void mettreAJourTable(Stagiaire stagiaire) {
		listeDynamiqueStagiaires.add(stagiaire);
		stagiairesTable.refresh();



	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}

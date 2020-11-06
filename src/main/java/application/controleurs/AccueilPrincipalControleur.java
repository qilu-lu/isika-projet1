package application.controleurs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.Data;
import application.models.ArbreBinaireModel;
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
import javafx.scene.control.Alert.AlertType;
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
	private static final String VUE_MODIFICATION_STAGIAIRE_VIEW_PATH = "ModificationStagiaire.fxml";

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
	private TextField nbrStagiaireTextField;
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
	private AjoutStagiaireControleur ajoutStagiaireControleur;
	private ModificationStagiaireControleur modificationStagiaireControleur;
	private ArbreBinaireModel<Stagiaire> arbreBinaireModel;

	public AccueilPrincipalControleur(LoginControleur vueAccueil) {
		this.arbreBinaireModel = new ArbreBinaireModel<Stagiaire>();
		this.vueAccueil = vueAccueil;
	}

	public AccueilPrincipalControleur() {
		// TODO Auto-generated constructor stub
	}

	public LoginControleur getvueAccueil() {
		return vueAccueil;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		modeleGlobalStagiaires = new StagiairesModel();
		initStagiairesTable();
		mettreAJourNbrStagiaire();
		
		//

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

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetAll();
			}
		});
		modifBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
				if(stagiaire != null) {
					try {
						afficherFenetreModificationStagiaire();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
				try {
					imprimePdf(listeDynamiqueStagiaires);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		nbrStagiaireTextField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mettreAJourNbrStagiaire();
			}
		});
		rechercheBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rechercherStagiaire();
			}
		});
	}

	private void rechercherStagiaire() {
//		if(nom.getText() == nomRechercheTextField.getText()) {
//			
//		}
//		nomRechercheTextField.getText()
//		departementTextField
//		promotionTextField
//		anneeTextField
		
	}
	
	private void mettreAJourNbrStagiaire() {
		int totalLigneStagiaire = listeDynamiqueStagiaires.size();
		nbrStagiaireTextField.setText(String.valueOf(totalLigneStagiaire));
	}

	public void afficherFenetreModificationStagiaire() throws IOException {
		modificationStagiaireControleur = new ModificationStagiaireControleur(this);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_MODIFICATION_STAGIAIRE_VIEW_PATH));
		loader.setController(modificationStagiaireControleur);
		Pane rootPane = loader.load();
		Stage stage = afficherFenetre(rootPane, "Modification");
		modificationStagiaireControleur.setStage(stage);
	}

	public void afficherFenetreAjoutStagiaire() throws IOException { 
		ajoutStagiaireControleur = new AjoutStagiaireControleur(this);
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(VUE_AJOUT_STAGIAIRE_VIEW_PATH));
		loader.setController(ajoutStagiaireControleur);
		Pane rootPane = loader.load();
		Stage stage = afficherFenetre(rootPane, "Nouveau Stagiaire");
		ajoutStagiaireControleur.setStage(stage);
	}
	
	private Stage afficherFenetre(Pane rootPane, String title) {
		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		return stage;
	}
	
	public void ajouterStagaireDansArbre(Stagiaire stagiaire) {
		this.arbreBinaireModel.ajouterNoeud(stagiaire);
		mettreAJourTable(stagiaire);
	}
	
//	private void creeEtAfficheFenetreAjoutStagiaire(Pane rootPane) {
//		Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
//		Stage ajoutStagiaireStage = new Stage();
//		ajoutStagiaireStage.setTitle("Nouveau Stagiaire");
//		ajoutStagiaireStage.setScene(scene);
//		ajoutStagiaireStage.show();
//	}

//	private void modifierLine() {
//		// TODO MODIFIER LA LIGNE
//		//TODO Modifier dans l'arbre
//		//selection de la ligne
//		//ajouter fenetre (ajoutstagiaire) de crÔøΩation stagiaire
//		//champ pr√©rempli
//		//modifier et accepter
//		//mettre √† jour liste stagiaire tableau refresh
//		Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
//		if(stagiaire != null) {
//			try {
//				afficherFenetreAjoutStagiaire();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} 
//
//			stagiairesTable.refresh();
//		}
//
//	}

	private void supprimerLine() {
		// TODO SUPPRIMER LA LIGNE
		//TODO supprimer dans l'arbre
		//selection de la ligne
		//supprimer tous les champs et ligne complete (attention suppr noeud binaire?)
		//mettre √† jour la liste tableau refresh
		Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
		if(stagiaire != null) {
			listeDynamiqueStagiaires.remove(stagiaire);
			stagiairesTable.refresh();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression d'un stagiaire");
			alert.setHeaderText("Veuillez s√©lectionner la ligne √† supprimer");
			alert.show();
		}
	}

		// TODO : Faire la m√©thode imprimer en PDF
		//		MessageFormat header = new MessageFormat("Liste des Stagiaires :");
		//		MessageFormat footer = new MessageFormat("Page{0,number,integer}");
		//		try {
		//			listeDynamiqueStagiaires.print(JTable.PrintMode.NORMAL, header, footer);
		//			
		//		}catch(java.awt.print.PrinterException e) {
		//			System.err.format("Erreur d'impression ",  e.getMessage());
	
		private void imprimePdf(ObservableList<Stagiaire> stagiaires) throws DocumentException, FileNotFoundException {
			//TODO VOIR POUR CHEMIN DANS DOSSIER
			FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\roman\\OneDrive\\Bureau\\ISIKA\\Test crÈation PDF\\liste.pdf"));
			Document doc = new Document();
			PdfWriter.getInstance(doc, fos);
			doc.open();
			doc.add(new Phrase("Liste des stagiaires\n"));
			doc.add(new Phrase("Liste gÈnÈrÈe le " + LocalDate.now() + "\n"));
			doc.add(new Phrase("- - - - - - - - - - - - - - - - - - - - - - - -"));
			PdfPTable table = new PdfPTable(5);
			PdfPCell cell = new PdfPCell(new Phrase("NOM"));
			PdfPCell cell2 = new PdfPCell(new Phrase("PRENOM"));
			PdfPCell cell3 = new PdfPCell(new Phrase("DEPARTEMENT"));
			PdfPCell cell4 = new PdfPCell(new Phrase("PROMOTION"));
			PdfPCell cell5 = new PdfPCell(new Phrase("ANNEE"));
			// 1 ligne => header 
			table.addCell(cell);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			// n lignes suivantes
			for(Stagiaire stagiaireTemp : stagiaires) {
				table.addCell(new Phrase(stagiaireTemp.getNom()));
				table.addCell(new Phrase(stagiaireTemp.getPrenom()));
				table.addCell(new Phrase(stagiaireTemp.getDepartement()));
				table.addCell(new Phrase(stagiaireTemp.getPromotion()));
				//TODO n'affiche pas l'annÈe dans le tableau
				table.addCell(new Phrase(stagiaireTemp.getAnnee()));
			}
			doc.add(table);
			doc.close();
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
		System.out.println("application quitt√©e");
	}

	@SuppressWarnings("unchecked")
	private void initStagiairesTable() {

		// On r√©cup√®re les colonnes par index (0 : nom, 1 : prenom .... 4 : annee)		
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

		listeDynamiqueStagiaires= FXCollections.observableArrayList();

		for (int i=0;i<Data.getInstance().getArbreStagiaire().getSize();i++) {
			listeDynamiqueStagiaires.add(Data.getInstance().getArbreStagiaire().get(i));
			//TODO arbre ‡ afficher en infixe
		}

		//Data.getInstance().getListeStagiaires().forEach(listeDynamiqueStagiaires::add);

		//listeDynamiqueStagiaires = FXCollections.observableArrayList(this.modeleGlobalStagiaires.getStagiaires());
		stagiairesTable.setItems(listeDynamiqueStagiaires);
	}
	
	public void mettreAJourModele(Stagiaire stagiaire) {
		modeleGlobalStagiaires.ajouterUnStagiaire(stagiaire);
	}
	
	private void mettreAJourTable(Stagiaire stagiaire) {
		listeDynamiqueStagiaires.add(stagiaire);
		stagiairesTable.refresh();
	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public String preRemplirChampsModification() {
			Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
			String nom = stagiaireModif.getNom();
			if(stagiaireModif != null) {
			return nom;			
		}
			else return "blabla";
	}
}

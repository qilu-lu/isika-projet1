package application.controleurs;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.Data;
import application.LanceurTestFXProjet1;
import application.models.ArbreBinaireModel;
import application.models.Stagiaire;
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
import javafx.scene.control.ButtonType;
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
	private static final String VUE_PDF_STAGIAIRE_VIEW_PATH = "ListeStagiaire.pdf";
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
	private TextField departementRechercheTextField;
	@FXML
	private TextField promotionRechercheTextField;
	@FXML
	private TextField anneeRechercheTextField;
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

	public LoginControleur getvueAccueil() {
		return vueAccueil;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initStagiairesTable();
		mettreAJourNbrStagiaire();

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
				if (stagiaire != null) {
					try {
						afficherFenetreModificationStagiaire(stagiaire);
					} catch (IOException e) {
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
					try {
						File pdfFile = new File(VUE_PDF_STAGIAIRE_VIEW_PATH);
						if (pdfFile.exists()) {
							if (Desktop.isDesktopSupported()) {
								Desktop.getDesktop().open(pdfFile);
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
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
		List<Stagiaire> listeFiltree = new ArrayList<Stagiaire>();

		String erreurs = validerRecherche();
		if(erreurs.isEmpty()) {

			String anneeText = anneeRechercheTextField.getText();
			Integer filtreAnnee = (anneeText != null && !anneeText.isEmpty()) 
					? Integer.valueOf(anneeText) : null;
					String filtreDepartement = departementRechercheTextField.getText();
					String filtreNom = nomRechercheTextField.getText().toUpperCase();
					String filtrePromotion = promotionRechercheTextField.getText();

					listeFiltree = LanceurTestFXProjet1.rechercheFiltre(Data.getInstance().getArbreStagiaireBin(),
							filtreNom, filtreDepartement,
							filtrePromotion, filtreAnnee);
					mettreAJourListe(listeFiltree);
					mettreAJourNbrStagiaire();
					majTriParNom(nom);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Saisie erronnée : ");
			alert.setContentText(erreurs);
			alert.show();
		}

	}

	private String validerRecherche() {
		StringBuilder erreurs = new StringBuilder();
		String anneeRech = anneeRechercheTextField.getText();
		try {
			if(anneeRech != null && !anneeRech.isEmpty()) {
				Integer annee = Integer.valueOf(anneeRech);
			}
		} catch(NumberFormatException e) {
			erreurs.append("L'année n'est pas valide");
		}
		return erreurs.toString();
	}

	private void mettreAJourNbrStagiaire() {
		int totalLigneStagiaire = listeDynamiqueStagiaires.size();
		nbrStagiaireTextField.setText(String.valueOf(totalLigneStagiaire));

	}

	public void afficherFenetreModificationStagiaire(Stagiaire stagiaire) throws IOException {
		modificationStagiaireControleur = new ModificationStagiaireControleur(this);
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource(VUE_MODIFICATION_STAGIAIRE_VIEW_PATH));
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

	public void ajouterStagaireDansArbre(Stagiaire stagiaire) throws IOException {
		Data.getInstance().getArbreStagiaireBin().ajouterNoeud(stagiaire);
		mettreAJourTable(stagiaire);
		mettreAJourNbrStagiaire();
		majTriParNom(nom);
	}

	public void supprStagiaireModif(Stagiaire stagiaire) {

		stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
		if(stagiaire != null) {
			listeDynamiqueStagiaires.remove(stagiaire);
			stagiairesTable.refresh();
			mettreAJourNbrStagiaire();
			Data.getInstance().getArbreStagiaireBin().supprimerNoeud(stagiaire);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression d'un stagiaire");
			alert.setHeaderText("Valider les modifications ?");
			alert.show();
		}
	}

	private void supprimerLine() {
		Stagiaire stagiaire = stagiairesTable.getSelectionModel().getSelectedItem();
		if (stagiaire != null) {
			Alert suppressionAlerte = new Alert(AlertType.CONFIRMATION);
			suppressionAlerte.setTitle("Suppression d'un stagiaire");
			suppressionAlerte.setHeaderText("Êtes-vous sûr de vouloir supprimer ce stagiaire ?");
			Optional<ButtonType> option = suppressionAlerte.showAndWait();

			if(option.get() == ButtonType.OK) {
				listeDynamiqueStagiaires.remove(stagiaire);
				stagiairesTable.refresh();
				mettreAJourNbrStagiaire();
				Data.getInstance().getArbreStagiaireBin().supprimerNoeud(stagiaire);
			} else {

			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Suppression d'un stagiaire");
			alert.setHeaderText("Veuillez sélectionner la ligne à supprimer");
			alert.show();
		}}


	private void imprimePdf(ObservableList<Stagiaire> stagiaires) throws DocumentException, FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(new File(VUE_PDF_STAGIAIRE_VIEW_PATH));
		Document doc = new Document();
		PdfWriter.getInstance(doc, fos);
		doc.open();
		doc.add(new Phrase("Liste des stagiaires\n"));
		doc.add(new Phrase("Liste générée le " + LocalDate.now() + "\n"));
		doc.add(new Phrase("Nombre de stagiaires : " + listeDynamiqueStagiaires.size() + "\n"));
		doc.add(new Phrase("- - - - - - - - - - - - - - - - - - - - - - - -"));
		PdfPTable table = new PdfPTable(5);
		PdfPCell cell1 = new PdfPCell(new Phrase("NOM"));
		PdfPCell cell2 = new PdfPCell(new Phrase("PRENOM"));
		PdfPCell cell3 = new PdfPCell(new Phrase("DEPARTEMENT"));
		PdfPCell cell4 = new PdfPCell(new Phrase("PROMOTION"));
		PdfPCell cell5 = new PdfPCell(new Phrase("ANNEE"));

		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		
		table.setWidthPercentage(100);
		
		for (Stagiaire stagiaireTemp : stagiaires) {
			table.addCell(new Phrase(stagiaireTemp.getNom()));
			table.addCell(new Phrase(stagiaireTemp.getPrenom()));
			table.addCell(new Phrase(stagiaireTemp.getDepartement()));
			table.addCell(new Phrase(stagiaireTemp.getPromotion()));
			table.addCell(new Phrase(Integer.toString(stagiaireTemp.getAnnee())));
		}
		doc.add(table);
		doc.close();
	}

	@FXML
	public void resetAll() {
		this.nomRechercheTextField.clear();
		this.departementRechercheTextField.clear();
		this.promotionRechercheTextField.clear();
		this.anneeRechercheTextField.clear();
		initStagiairesTable();
		mettreAJourNbrStagiaire();
	}

	@FXML
	public void goToLogin() throws IOException {
		primaryStage.hide();
		vueAccueil.afficher();
	}

	@FXML
	public void closeWindow() {
		Platform.exit();
	}

	@SuppressWarnings("unchecked")
	private void initStagiairesTable() {

		TableColumn<Stagiaire, String> nomCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns()
				.get(INDEX_COLONNE_NOM);
		TableColumn<Stagiaire, String> prenomCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns()
				.get(INDEX_COLONNE_PRENOM);
		TableColumn<Stagiaire, String> departementCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns()
				.get(INDEX_COLONNE_DEPARTEMENT);
		TableColumn<Stagiaire, String> promotionCol = (TableColumn<Stagiaire, String>) stagiairesTable.getColumns()
				.get(INDEX_COLONNE_PROMOTION);
		TableColumn<Stagiaire, Integer> anneeCol = (TableColumn<Stagiaire, Integer>) stagiairesTable.getColumns()
				.get(INDEX_COLONNE_ANNEE);

		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));
		promotionCol.setCellValueFactory(new PropertyValueFactory<>("promotion"));
		anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));

		listeDynamiqueStagiaires = FXCollections.observableArrayList();

		for (int i = 0; i <= Data.getInstance().getArbreStagiaireBin().getSize(); i++) {
			Stagiaire s = Data.getInstance().getArbreStagiaireBin().get(i);
			if (s != null)
				listeDynamiqueStagiaires.add(s);
		}
		stagiairesTable.setItems(listeDynamiqueStagiaires);
		majTriParNom(nomCol);

	}

	@SuppressWarnings("unchecked")
	private void majTriParNom(TableColumn<Stagiaire, String> nomCol) {
		stagiairesTable.getSortOrder().addAll(nomCol);
	}


	private void mettreAJourTable(Stagiaire stagiaire) {
		listeDynamiqueStagiaires.add(stagiaire);
		stagiairesTable.refresh();
	}

	public void mettreAJourListe(List<Stagiaire> list) {
		listeDynamiqueStagiaires.clear();
		for (Stagiaire st : list) {
			listeDynamiqueStagiaires.add(st);
		}
		stagiairesTable.refresh();
	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public String preRemplirChampsModificationNom() {
		Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
		String nom = stagiaireModif.getNom();
		if (stagiaireModif != null) {
			return nom;
		} else
			return null;
	}

	public String preRemplirChampsModificationPrenom() {
		Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
		String prenom = stagiaireModif.getPrenom();
		if (stagiaireModif != null) {
			return prenom;
		} else
			return null;
	}

	public String preRemplirChampsModificationDepartement() {
		Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
		String departement = stagiaireModif.getDepartement();
		if (stagiaireModif != null) {
			return departement;
		} else
			return null;
	}

	public String preRemplirChampsModificationPromotion() {
		Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
		String promotion = stagiaireModif.getPromotion();
		if (stagiaireModif != null) {
			return promotion;
		} else
			return null;
	}

	public String preRemplirChampsModificationAnnee() {
		Stagiaire stagiaireModif = this.stagiairesTable.getSelectionModel().getSelectedItem();
		String annee = Integer.toString(stagiaireModif.getAnnee());
		if (stagiaireModif != null) {
			return annee;
		} else
			return null;
	}

}
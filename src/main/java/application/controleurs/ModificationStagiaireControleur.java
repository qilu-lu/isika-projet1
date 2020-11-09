package application.controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.models.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificationStagiaireControleur implements Initializable {

	@FXML
	private TextField promoStTextField;
	@FXML
	private TextField nomStTextField;
	@FXML
	private TextField departementStTextField;
	@FXML
	private TextField anneeStTextField;
	@FXML
	private TextField prenomStTextField;
	@FXML
	private Button annulStBtn;
	@FXML
	private Button resetStBtn;
	@FXML
	private Button modifStBtn;

	private Stagiaire stagiaireSuppr;
	private Stage modifStagiaireStage;
	private AccueilPrincipalControleur vueModificationStagiaire;

	public ModificationStagiaireControleur(AccueilPrincipalControleur vueModificationStagiaire) {
		this.vueModificationStagiaire = vueModificationStagiaire;
	}

	public AccueilPrincipalControleur getvueModificationStagiaire() {
		return vueModificationStagiaire;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String nom = getvueModificationStagiaire().preRemplirChampsModificationNom();
		nomStTextField.setText(nom);
		String prenom = getvueModificationStagiaire().preRemplirChampsModificationPrenom();
		prenomStTextField.setText(prenom);
		String departement = getvueModificationStagiaire().preRemplirChampsModificationDepartement();
		departementStTextField.setText(departement);
		String promotion = getvueModificationStagiaire().preRemplirChampsModificationPromotion();
		promoStTextField.setText(promotion);
		String annee = getvueModificationStagiaire().preRemplirChampsModificationAnnee();
		anneeStTextField.setText(annee);

		stagiaireSuppr = new Stagiaire(nom, prenom, departement, promotion, Integer.parseInt(annee));

		annulStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				returnToAccueilPrincipal();
			}
		});
		resetStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reset();
			}
		});
		modifStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					modifierStagiaire();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Stagiaire donneesStagaireSuppr() {
		return stagiaireSuppr;
	}

	private void modifierStagiaire() throws IOException {

		Alert modifAlerte = new Alert(AlertType.CONFIRMATION);
		modifAlerte.setTitle("Modification d'un stagiaire");
		modifAlerte.setHeaderText("Voulez-vous valider les modifications ?");
		Optional<ButtonType> option = modifAlerte.showAndWait();

		if (option.get() == ButtonType.OK) {
			Stagiaire st = donneesStagaireSuppr();
			vueModificationStagiaire.supprStagiaireModif(st);

			String nomModif = nomStTextField.getText();
			String prenomModif = prenomStTextField.getText();
			String departementModif = departementStTextField.getText();
			String promoModif = promoStTextField.getText();
			String anneeModif = anneeStTextField.getText();

			Stagiaire stModif = new Stagiaire(nomModif, prenomModif, departementModif, promoModif,
					Integer.parseInt(anneeModif));
			vueModificationStagiaire.ajouterStagaireDansArbre(stModif);

			closeStage();

		} else {

		}
	}

	@FXML
	private void reset() {
		this.nomStTextField.clear();
		this.prenomStTextField.clear();
		this.departementStTextField.clear();
		this.promoStTextField.clear();
		this.anneeStTextField.clear();
		System.out.println("all clear");
	}

	private void returnToAccueilPrincipal() {
		System.out.println("retour vers Accueil Principal");
		this.modifStagiaireStage.hide();
	}

	public void setStage(Stage stage) {
		modifStagiaireStage = stage;
	}

	private void closeStage() {
		modifStagiaireStage.close();
	}
}

package application.controleurs;


import java.net.URL;
import java.util.ResourceBundle;

import application.models.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AjoutStagiaireControleur implements Initializable {

	@FXML
	private Button resetStBtn;
	@FXML
	private Button ajoutStBtn;
	@FXML
	private TextField nomStTextField;
	@FXML
	private Button annulStBtn;
	@FXML
	private TextField departementStTextField;
	@FXML
	private TextField anneeStTextField;
	@FXML
	private TextField prenomStTextField;
	@FXML
	private TextField promoStTextField;
	
	private AccueilPrincipalControleur vueAjoutStagiaire;
	private Stage ajoutStagiaireStage;

	public AjoutStagiaireControleur(AccueilPrincipalControleur vueAjoutStagiaire) {
		this.vueAjoutStagiaire = vueAjoutStagiaire;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		annulStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				returnToAccueilPrincipal();
			}
		});
		resetStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetAll();
			}
		});
		ajoutStBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ajouterNewStagiaire();
			}
		});
	}

	protected void ajouterNewStagiaire() {
		String erreurs = validerSaisie();
		if (erreurs.isEmpty()) {

			Stagiaire stagiaire = new Stagiaire();
			stagiaire.setNom(nomStTextField.getText());
			stagiaire.setPrenom(prenomStTextField.getText());
			stagiaire.setDepartement(departementStTextField.getText());
			stagiaire.setPromotion(promoStTextField.getText());
			stagiaire.setAnnee(Integer.valueOf(anneeStTextField.getText()));
			
			//TODO récupération des données du nouveau stagiaire dans l'arbre
			//arbreStagiaires.ajoutNouveauNoeud(stagiaire);
			//
			
			vueAjoutStagiaire.mettreAJourTable(stagiaire);
			

			closeStage();
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erreurs de saisie : ");
			alert.setContentText(erreurs);
			alert.show();
		}
	}

	private void closeStage() {
		ajoutStagiaireStage.close();
	}
	@FXML
	public void resetAll() {
		this.nomStTextField.clear();
		this.prenomStTextField.clear();
		this.departementStTextField.clear();
		this.promoStTextField.clear();
		this.anneeStTextField.clear();
		System.out.println("all clear");
	}
	@FXML
	public void returnToAccueilPrincipal() {
//		Stage stage = (Stage) annulStBtn.getScene().getWindow(); 
//		stage.close(); 
		System.out.println("retour vers Accueil Principal");
		ajoutStagiaireStage.hide();
	}

	private String validerSaisie() {
		StringBuilder errorsBuilder = new StringBuilder();

		// nom
		String nom = nomStTextField.getText();
		if (nom == null || nom.trim().isEmpty()) {
			errorsBuilder.append("Le nom du stagiaire doit être renseigné\n");
		}

		// prenom
		String prenom = prenomStTextField.getText();
		if (prenom.trim().isEmpty()) {
			errorsBuilder.append("Le prénom du stagiaire doit être renseigné\n");
		}

		// departement
		String departement = departementStTextField.getText();
		if (departement.trim().isEmpty()) {
			errorsBuilder.append("Le département du stagiaire doit être renseigné\n");
		}

		// promotion
		String promotion = promoStTextField.getText();
		if (promotion.trim().isEmpty()) {
			errorsBuilder.append("La promotion du stagiaire doit être renseigné\n");
		}

		// annee
		String annee = anneeStTextField.getText();
		if (annee == null || annee.trim().isEmpty()) {
			errorsBuilder.append("L'année de l'entrée dans la promotion du stagiaire doit être renseigné\n");
		} else {
			try {
				Integer.valueOf(annee);
			} catch (NumberFormatException e) {
				errorsBuilder.append("L'année doit être une valeur numérique\n");
			}
		}
		return errorsBuilder.toString();
	}



}

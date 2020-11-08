package application.controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import application.models.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
				// TODO à faire acceptation de la modification du stagiaire;
				modifierStagiaire();
			}
		});
	}
	
	
	private void modifierStagiaire() {
		// TODO Auto-generated method stub
		
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
		Stage stage = (Stage) annulStBtn.getScene().getWindow(); 
		stage.hide();		
	}

	public void setStage(Stage stage) {
	}
}

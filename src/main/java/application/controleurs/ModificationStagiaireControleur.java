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
	private Stage modificationStagiaireStage;
	private AccueilPrincipalControleur donneeStagiaire;
	
	public ModificationStagiaireControleur(AccueilPrincipalControleur vueModificationStagiaire) {
		this.vueModificationStagiaire = vueModificationStagiaire;
	}

	public AccueilPrincipalControleur getvueModificationStagiaire() {
		return vueModificationStagiaire;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		donneeStagiaire = new AccueilPrincipalControleur;
//		String nom = donneeStagiaire.preRemplirChampsModification();
//		nomStTextField.setText(nom);
		
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
			}
		});
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
		modificationStagiaireStage = stage;
	}
}

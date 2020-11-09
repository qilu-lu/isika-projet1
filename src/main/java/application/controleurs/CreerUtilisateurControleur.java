package application.controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.models.NouvelUtilisateurModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreerUtilisateurControleur implements Initializable {

	@FXML
	private PasswordField confirmMdpField;
	@FXML
	private TextField codeEmployeTextField;
	@FXML
	private TextField eMailTextField;
	@FXML
	private PasswordField mdpField;
	@FXML
	private TextField prenomTextField;
	@FXML
	private TextField nomTextField;
	@FXML
	private Button retour;
	@FXML
	private Button ok;
	@FXML
	private Button reset;

	protected List<NouvelUtilisateurModel> utilisateurList = new ArrayList<NouvelUtilisateurModel>();

	Alert alert = new Alert(AlertType.ERROR);

	private LoginControleur vuePrincipaleControleur;

	public CreerUtilisateurControleur(LoginControleur vuePrincipaleControleur, NouvelUtilisateurModel nUModel) {
		this.vuePrincipaleControleur = vuePrincipaleControleur;
	}

	public LoginControleur getVuePrincipaleControleur() {
		return vuePrincipaleControleur;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				creerUtilisateur(event);
			}
		});
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetAll();
			}
		});
		retour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				precedent();
			}
		});
	}

	@FXML
	public void creerUtilisateur(ActionEvent event) {

		String adresseEmail = eMailTextField.getText();
		String codeEmploye = codeEmployeTextField.getText();
		String nom = nomTextField.getText();
		String prenom = prenomTextField.getText();
		String mdp = mdpField.getText();
		String confirmMdp = confirmMdpField.getText();

		try {
			validateEMail(adresseEmail);
			validateCodeEmploye(codeEmploye);
			validateMdp(mdp, confirmMdp);
			validateMdp(confirmMdp, mdp);
		} catch (Exception e) {
			String msg = e.getMessage();
			alert.setHeaderText(msg);
			alert.show();
		}

		if (mdp.equals(confirmMdp)) {
			NouvelUtilisateurModel monUtilisateur = new NouvelUtilisateurModel(adresseEmail, codeEmploye, nom, prenom,
					mdp, confirmMdp);
			utilisateurList.add(monUtilisateur);
			resetAll();
			if (!alert.isShowing()) {
				Stage stage = (Stage) retour.getScene().getWindow();
				stage.close();
			}
		}
	}

	private void validateCodeEmploye(String codeEmploye) throws IOException {
		if (codeEmploye.trim().isEmpty())
			throw new IOException("Votre code employé est obligatoire");
		if (!codeEmploye.contains("AD") && !(codeEmploye.length() == 6) | !codeEmploye.contains("FR")
				&& !(codeEmploye.length() == 6))
			throw new IOException(
					"Votre code employé doit être valide : ex. AD1234 (Deux lettres (AD ou FR) suivies de 4 chiffres");
	}

	private void validateMdp(String mdp, String confirmMdp) throws IOException {

		if (mdp.trim().isEmpty() || confirmMdp.trim().isEmpty())
			throw new IOException("Le mot de passe ne doit pas vide");
		if (mdp.length() < 6)
			throw new IOException("Le mot de passe ne doit pas être inférieur à 6 caractères");
		if (!mdp.equals(confirmMdp))
			throw new IOException("Les mots de passe ne correspondent pas");
	}

	private void validateEMail(String eMail) throws IOException {
		if (eMail.trim().isEmpty())
			throw new IOException("L'adresse email est obligatoire");
		if (!eMail.contains("@") && !eMail.contains("."))
			throw new IOException("Merci de fournir une adresse email valide");
	}

	@FXML
	public void resetAll() {
		this.eMailTextField.clear();
		this.codeEmployeTextField.clear();
		this.nomTextField.clear();
		this.prenomTextField.clear();
		this.mdpField.clear();
		this.confirmMdpField.clear();
	}

	@FXML
	public void precedent() {
		Stage stage = (Stage) retour.getScene().getWindow();
		stage.hide();
	}

}

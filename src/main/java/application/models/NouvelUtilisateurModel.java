package application.models;

public class NouvelUtilisateurModel {

	protected String adresseEmail;
	protected String codeEmploye;
	protected String nom;
	protected String prenom;
	protected String mdp;
	protected String confirmMdp;

	public NouvelUtilisateurModel() {
		super();
	}

	public NouvelUtilisateurModel(String adresseEmail, String codeEmploye, String nom, String prenom, String mdp,
			String confirmMdp) {
		super();
		this.adresseEmail = adresseEmail;
		this.codeEmploye = codeEmploye;
		this.nom = nom;
		this.prenom = prenom;
		this.mdp = mdp;
		this.confirmMdp = confirmMdp;
	}

	@Override
	public String toString() {
		return "NouvelUtilisateurModel [adresseEmail=" + adresseEmail + ", codeEmploye=" + codeEmploye + ", nom=" + nom
				+ ", prenom=" + prenom + ", mdp=" + mdp + ", confirmMdp=" + confirmMdp + "]";
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	public String getCodeEmploye() {
		return codeEmploye;
	}

	public void setCodeEmploye(String codeEmploye) {
		this.codeEmploye = codeEmploye;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getConfirmMdp() {
		return confirmMdp;
	}

	public void setConfirmMdp(String confirmMdp) {
		this.confirmMdp = confirmMdp;
	}

}

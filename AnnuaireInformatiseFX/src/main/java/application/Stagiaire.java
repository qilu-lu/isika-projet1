package application;

public class Stagiaire implements Comparable<Stagiaire> {
	private String nom;
	private String prenom;
	private String appartement;
	private String promotion;
	private int annee;
	
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

	public String getAppartement() {
		return appartement;
	}

	public void setAppartement(String appartement) {
		this.appartement = appartement;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	@Override
	public int compareTo(Stagiaire o) {
	int result=this.getNom().compareTo(o.getNom());
	if(result>0) {
		
	}
		
		
		
		return 0;
	}

}

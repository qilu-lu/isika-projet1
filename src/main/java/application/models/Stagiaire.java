package application.models;



public class Stagiaire implements IEnregistrable<Stagiaire> {

	private String nom;
	private String prenom;
	private String departement;
	private String promotion;
	private int annee;
/////////////////////////MOFIDICATION SEQUENCE A 124 AJOUT NOEUD PARENT//////////////////////////////
	private static final int SEQUENCE_OCTETS = 124;
	private static final int SEQUENCE_CHARS_A_SUPPRIMER = 60;
	private static final int ID_NOEUD_INIT = -1;
	private static final int POS_ACCES_NOEUD_PARENT = 16;
	private static final int POS_ACCES_NOEUD_GAUCHE = 12;
	private static final int POS_ACCES_NOEUD_DROIT = 8;

		 
	public Stagiaire(String nom, String prenom, String departement, String promotion, int annee) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.promotion = promotion;
		this.annee = annee;
	}

	public Stagiaire(String nom, String prenom, String departement, String promotion) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.promotion = promotion;
		this.annee = this.getAnnee();
	}

	public Stagiaire() {
	}

	public String getNom() {
		return nom;
	}

	public String getNomUniformise() {
		StringBuilder nomUnif = new StringBuilder();
		nomUnif.append(nom);
		nomUnif.setLength(21);
		return nomUnif.toString();
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public String getPrenomUniformise() {
		StringBuilder prenomUnif = new StringBuilder();
		prenomUnif.append(prenom);
		prenomUnif.setLength(18);
		return prenomUnif.toString();
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}
	public String getDepartementUniformise() {
		StringBuilder departementUnif = new StringBuilder();
		departementUnif.append(departement);
		departementUnif.setLength(3);
		return departementUnif.toString();
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getPromotion() {
		return promotion;
	}
	public String getPromotionUniformise() {
		StringBuilder promotionUnif = new StringBuilder();
		promotionUnif.append(promotion);
		promotionUnif.setLength(10);
		return promotionUnif.toString();
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
	public String uniformise() {
		StringBuilder sequence = new StringBuilder();
		sequence.append(this.getNomUniformise());
		sequence.append(this.getPrenomUniformise());
		sequence.append(this.getDepartementUniformise());
		sequence.append(this.getPromotionUniformise());

		return sequence.toString();
	}
	
	@Override
	public String uniformiseVide() {
		StringBuilder sequenceVide = new StringBuilder();
		for(int i = 0 ; i< SEQUENCE_CHARS_A_SUPPRIMER;i++) {
			sequenceVide.append("*");
		}
		return sequenceVide.toString();
	}
	
	
	public int compareToNom(Stagiaire o) {
		int result = 0;
		
		if(this.nom == null)
			return result = 0;

			if (!this.nom.equals(o.getNom())) {
				result = this.getNom().compareTo(o.getNom());
			}
			else if (!this.prenom.equals(o.getPrenom())) {
				result = this.getPrenom().compareTo(o.getPrenom());
			} else if (!this.departement.equals(o.getDepartement())) {
				result = this.getDepartement().compareTo(o.getDepartement());
			} else if (!this.promotion.equals(o.getPromotion())) {
				result = this.getPromotion().compareTo(o.getPromotion());
			} else if (this.annee != (o.getAnnee())) {
				if (this.annee > o.getAnnee()) {
					result = -1;
				}
				if (this.annee < o.getAnnee()) {
					result = 1;
				} else {
					result = 0;
				}
	}
		return result;
	}

	
	@Override
	public int compareTo(Stagiaire o) {
		return compareToNom(o);
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stagiaire [nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", departement=");
		builder.append(departement);
		builder.append(", promotion=");
		builder.append(promotion);
		builder.append(", annee=");
		builder.append(annee);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * Builder pattern
	 */
	public Stagiaire avecNom(final String nom) {
		this.nom = nom;
		return this;
	}
	
	public Stagiaire avecPrenom(final String prenom) {
		this.prenom = prenom;
		return this;
	}
	public Stagiaire avecDepartement(final String departement) {
		this.departement = departement;
		return this;
	}
	public Stagiaire avecPromotion(final String promotion) {
		this.promotion = promotion;
		return this;
	}
	public Stagiaire avecAnnee(final int annee) {
		this.annee = annee;
		return this;
	}

	@Override
	public int getTailleEnregistrement() {
		return SEQUENCE_OCTETS ;
	}
	
	@Override
	public int getIDNoeudINIT() {
		return ID_NOEUD_INIT ;
	}
	@Override
	public int getPositionNoeudG() {
		return POS_ACCES_NOEUD_GAUCHE ;
	}
	@Override
	public int getPositionNoeudD() {
		return POS_ACCES_NOEUD_DROIT ;
	}
	@Override
	public int getSequenceASupprimer() {
		return SEQUENCE_CHARS_A_SUPPRIMER ;
	}
	@Override
	public int getPositionNoeudP() {
		return POS_ACCES_NOEUD_PARENT;
	}
	
	
	
	
}
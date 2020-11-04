package application;

public class Stagiaire implements Comparable<Stagiaire> {
	
	public static int RecordSize=166;//chaque strgiaire dois avoir 166 octets
	private String nom="";
	private String prenom="";
	private String departement="";
	private String promotion="";
	private int annee;
	
	 public static int NomSize=42;
	 public static int PrenomSize=36;
	 public static int DepartementSize=4;
	 public static int PromotionSize=10;
	 
	 
	 
	
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

	public String getdepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
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
		int result=0;
		if(!this.nom.equals(o.getNom())) {
			result=this.getNom().compareTo(o.getNom());
		}
		else {
			if(!this.prenom.equals(o.getPrenom()))
			result=this.getPrenom().compareTo(o.getPrenom());
			else {
				if(!this.departement.equals(o.getdepartement()))
				result=this.getdepartement().compareTo(o.getdepartement());
				else {
					if(!this.promotion.equals(o.getPromotion()));
					result=this.promotion.compareTo(o.getPromotion());
//					else {
//						
//					}
				}
			}
		}
	
		return result;
	}

	public Stagiaire(String nom2, String prenom2, String departement2, int annee2) {
		super();
	}

	public Stagiaire(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	public Stagiaire() {
		super();
	}

	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + ", promotion="
				+ promotion + ", annee=" + annee + "]";
	}
	

}

package application.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;



public class ArbreBinaireModel<E extends IEnregistrable<E>> {
	public static final String LISTE_STAGIAIRE_FICHIER = "";

	private static class Noeud<T> {

		private T stagiaire;
		private Noeud<T> gauche;
		private Noeud<T> droit;
		private int iD;

		public void setID(int iD) {
			this.iD = iD;
		}

		public int getID() {
			return iD;
		}

		public Noeud(T st, int iD) {
			this.stagiaire = (T) st;
			this.iD = iD;
			gauche = null;
			droit = null;
		}

	}

	private Noeud<E> racine;
	private int size;

	public int getSize() {
		return size;
	}

	public Noeud<E> getRacine() {
		return racine;
	}

	public String getRacineString() {
		StringBuilder builder = new StringBuilder();
		builder.append(racine);
		return builder.toString();
	}

	public void ajouterNoeud(E st) {
		EcritureFichierBinaire<IEnregistrable> ecr = new EcritureFichierBinaire<IEnregistrable>();
		Noeud<E> courant = racine;
		if (courant == null) {
			racine = new Noeud<E>(st, 0);
			size++;
			ecr.ecrireNouveauNoeud(st, racine.getID());
		} else {
			boolean trouve = false;
			while (!trouve) {
				int test = st.compareTo(courant.stagiaire);
				if (test == 0) {
					trouve = true;
				} else if (test < 0) {
					if (courant.gauche == null) {
						courant.gauche = new Noeud<E>(st, ecr.calculerID());
						size++;
						//recup�rer les infos
						ecr.ecrireNouveauNoeud(st, courant.gauche.getID());
						ecr.reecrirePositionNoeud(courant.stagiaire, courant.gauche.getID(), courant.getID(),
								st.getPositionNoeudG());
					} else {
						courant = courant.gauche;
					}
				} else {
					if (courant.droit == null) {
						courant.droit = new Noeud<E>(st, ecr.calculerID());
						size++;
						ecr.ecrireNouveauNoeud(st, courant.droit.getID());
						ecr.reecrirePositionNoeud(courant.stagiaire, courant.droit.getID(), courant.getID(),
								st.getPositionNoeudD());
					} else {
						courant = courant.droit;
					}
				}
			}
		}
	}




	//	public void supprimerNoeud(E noeudASuppr) {
	//		racine = remplacerNoeud(racine, noeudASuppr);
	//	}
	//
	//	private Noeud<E> remplacerNoeud(Noeud<E> racine, E noeudASuppr) {
	//		Noeud<E> courant = racine;
	//		if (courant == null)
	//			return null;
	//
	//		if (noeudASuppr.compareTo(courant.stagiaire) == 0) {
	//
	//			// CAS 1 : PAS DE FILS
	//			if (courant.gauche == null && courant.droit == null) {
	//				reecrirePositionNoeud(courant.getID())
	//				supprimerNoeudDansBin(courant, courant.getID());
	//				return null;
	//
	//				// CAS 2 : UN FILS
	//			} else if (courant.gauche == null) {
	//				// suppression dans le fichier bin
	//				supprimerNoeudDansBin(courant, courant.getID());
	//				return courant.gauche;
	//			} else if (courant.droit == null) {
	//				// suppression dans le fichier bin
	//				supprimerNoeudDansBin(courant, courant.getID());
	//				return courant.droit;
	//			}
	//
	//			// CAS TROIS : DEUX FILS
	//			else {
	//				E valMin = trouverLaPlusPetiteValeur(courant.droit);
	//				courant.stagiaire = valMin;
	//				courant.droit = remplacerNoeud(courant.droit, valMin);
	//				supprimerNoeudDansBin(courant, courant.getID());
	//				return courant;
	//			}
	//		}
	//
	//		if (noeudASuppr.compareTo(courant.stagiaire) < 0) {
	//			courant.gauche = remplacerNoeud(courant.gauche, noeudASuppr);
	//			return courant;
	//		}
	//		courant.droit = remplacerNoeud(courant.droit, noeudASuppr);
	//		return courant;
	//	}
	//
	//	public E trouverLaPlusPetiteValeur(Noeud nMin) {
	//		return nMin.gauche == null ? (E) nMin.stagiaire : trouverLaPlusPetiteValeur(nMin.gauche);
	//	}
	//


	public ArbreBinaireModel<E>filter(Predicate<E>p){
		ArbreBinaireModel<E>ret=new ArbreBinaireModel<>();
		for (int i = 0; i < this.size; i++) {
			final E el=this.get(getRacine(), i);
			if(p.test(el)) {
				ret.ajouterNoeud(el);
			}
		}
		return ret;

	}

	public E get( int pos) {	
		return get(racine, pos);
	}
	
	private E get(Noeud<E> r, int pos) {	
		if (r != null) {
			if (r.iD == pos)
				return r.stagiaire;		
			E e = get(r.gauche, pos);
			if (e != null) {
				return e;
			}
			// System.out.println(r);
			e = get(r.droit, pos);
			if (e != null) {
				return e;
			}
		}
		return (E) null;
	}

	// parcours infixe
	public void infixe(Noeud<E> r) {
		if (r != null) {
			infixe(r.gauche);
			System.out.println(r.stagiaire.toString() +"\n");
			infixe(r.droit);
		}
	}
	
//	public void infixe(Noeud<E> r) {
//		if (r != null) {
//			infixe(r.gauche);
//			System.out.println(r.getID() +"\n");
//			infixe(r.droit);
//		}
//	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StagiaireArbre [racine=");
		builder.append(racine);
		builder.append("]");
		return builder.toString();
	}


}

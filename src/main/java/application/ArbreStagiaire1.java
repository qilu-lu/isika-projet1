package application;


public class ArbreStagiaire1<E extends Comparable<E>> {

	private static class Noeud<T> {
		private T cle;
		private Noeud<T> gauche;
		private Noeud<T> droit;

		public Noeud() {
			super();
			cle = null;
			gauche = null;
			droit = null;
		}

		public Noeud(T cle) {
			super();
			this.cle = cle;
			gauche = null;
			droit = null;
		}

		@Override
		public String toString() {
			return "Noeud [cle=" + cle + "]";
		}
	}

	private Noeud<E> racine;

	public ArbreStagiaire1() {
		super();
	}

	public Noeud<E> getRacine() {
		return racine;
	}

	public void ajouterNoeud(E valeur) {
		Noeud<E> courant = racine;
		if (courant == null)
			racine = new Noeud<E>(valeur);
		else {
			boolean trouve = false;
			while (!trouve) {
				int test = valeur.compareTo(courant.cle);
				if (test == 0) {
					trouve = true;
				} else if (test < 0) {
					if (courant.gauche == null) {
						courant.gauche = new Noeud<E>(valeur);
						trouve = true;
					} else {
						courant = courant.gauche;
					}
				} else {
					if (courant.droit == null) {
						courant.droit = new Noeud<E>(valeur);
						trouve = true;
					} else {
						courant = courant.droit;

					}
				}
			}
		}

	}

//parcours infixe
	public void infixe(Noeud<E> r) {
		if (r != null) {
			infixe(r.gauche);
			System.out.print(r.cle + ";");
			infixe(r.droit);
		}
	}

	public void addAll(Noeud<E> r) {
		if (r != null) {
			addAll(r.gauche);
			ajouterNoeud(r.cle);
			addAll(r.droit);
		}
	}

	public Noeud<E> rechercher(Noeud<E> noeud, E valRech) {
		Noeud<E> courant = noeud;
		while (valRech.compareTo(courant.cle) != 0) {
			if (valRech.compareTo(courant.cle) < 0) {
				courant = courant.gauche;
			} else {
				courant = courant.droit;
			}
			if (courant == null)
				return null;
		}
		return courant;
	}

	public E recherche(Noeud<E> noeud, E valRech) {
		Noeud<E> no = rechercher(noeud, valRech);
		if (no != null)
			return no.cle;
		return null;
	}


	public Noeud<E> supprimer(Noeud<E> noeudRac, E cle) {
		Noeud<E> noeud = rechercher(noeudRac, cle);
		if (noeud != null) {
			if (noeud.droit == null && noeud.gauche == null) {
				noeud = null;
			} else if (noeud.gauche != null && noeud.droit != null) {

				if (noeud.droit.gauche != null) {
					noeud.cle = noeud.droit.gauche.cle;
					Noeud<E> n = noeud.droit.gauche;
					noeud.droit.gauche = null;
					addAll(n);
				} else if (noeud.droit.gauche == null) {
					
					noeud.cle = noeud.droit.cle;
					noeud.droit.cle=noeud.droit.droit.cle;
					Noeud<E>nn=noeud.droit.droit ;
					noeud.droit.droit=null;
					addAll(nn);
				}
			} else if (noeud.gauche != null && noeud.droit == null || noeud.gauche == null && noeud.droit != null) {
				if (noeud.gauche != null) {
					noeud = noeud.gauche;
					noeud.gauche = null;
				}
				if (noeud.droit != null) {
					noeud = noeud.droit;
					noeud.droit = null;
				}
			}

		} else {
			return null;
		}

		return noeud;

	}

}
package application;

import java.util.function.Predicate;

//import fr.isika.arbrebinaire.ArbreBinaire.Noeud;

public class ArbreStagiaire1<E extends Comparable<E>> {

	private static class Noeud<T> {
		private T cle;
		private Noeud<T> gauche;
		private Noeud<T> droit;
		private Noeud<T> doublon;
		int pos = -1;

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

			return "Noeud [cle=" + cle + ", pos=" + pos + "]";
		}
	}

	private Noeud<E> racine;
	public int size;

	public ArbreStagiaire1() {
		super();
	}

	public Noeud<E> getRacine() {
		return racine;
	}

	public void ajouterNoeud(E valeur) {
		Noeud<E> courant = racine;
		if (courant == null) {
			racine = new Noeud<E>(valeur);
			racine.pos = size++;
		} else {
			boolean trouve = false;
			while (!trouve) {
				int test = valeur.compareTo(courant.cle);
				if (test == 0) {
					trouve = true;
				} else if (test < 0) {
					if (courant.gauche == null) {
						courant.gauche = new Noeud<E>(valeur);
						courant.gauche.pos = size++;
						trouve = true;
					} else {
						courant = courant.gauche;
					}
				} else {
					if (courant.droit == null) {
						courant.droit = new Noeud<E>(valeur);
						courant.droit.pos = size++;
						trouve = true;
					} else {
						courant = courant.droit;

					}
				}
			}
		}
		// recomputeIndex(racine, 0);
//		size++;

	}

//	public int position(Noeud<E> r, E valRech) {
//		int pos=-1;
//			while(r.cle!=valRech) {
//				//position(r.gauche);
//			}
//		}
//	

//parcours infixe
	public void infixe(Noeud<E> r) {
		if (r != null) {
			infixe(r.gauche);
			String s = r.cle + "  " + r.pos + "  ";
			if (r.gauche != null) {
				s += r.gauche.pos;
			} else {
				s += "null";
			}
			s += "  ";
			if (r.droit != null) {
				s += r.droit.pos;
			} else {
				s += "null";
			}
			s += "  ";
			System.out.println(s);
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

	public int recomputeIndex(Noeud<E> r, int pos) {
		if (r != null) {
			pos = recomputeIndex(r.gauche, pos);
			r.pos = pos;
			pos++;
			pos = recomputeIndex(r.droit, pos);
		}

		return pos;
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
					noeud.droit.cle = noeud.droit.droit.cle;
					Noeud<E> nn = noeud.droit.droit;
					noeud.droit.droit = null;
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
			size--;
		} else {
			return null;
		}
		/// !!!! n'oublie pas!!!
		// recomputeIndex(racine, 0);
		return noeud;

	}

	public E get(Noeud<E> r, int pos) {

		if (r != null) {
			if (r.pos == pos)
				return r.cle;

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

	public int getsize() {

		return size;
	}
	public ArbreStagiaire1<E>filter(Predicate<E>p){
		ArbreStagiaire1<E>ret=new ArbreStagiaire1<>();
		for (int i = 0; i < this.size; i++) {
			final E el=this.get(getRacine(), i);
			if(p.test(el)) {
				ret.ajouterNoeud(el);
			}
		}
		return ret;
		
	}

}
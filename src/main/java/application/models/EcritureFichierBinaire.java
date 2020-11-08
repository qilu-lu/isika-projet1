package application.models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import application.models.ArbreBinaireModel.Noeud;

public class EcritureFichierBinaire<E extends IEnregistrable> {
	public static final String LISTE_STAGIAIRE_FICHIER = "FileBin.bin";
	URL resource1 = getClass().getClassLoader().getResource(LISTE_STAGIAIRE_FICHIER);

	public void reecrirePositionNoeud(E st, int positionNoeud, int positionNoeudParent, int positionBin) {
		int positionAModifier = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			positionAModifier = (positionNoeud - positionNoeudParent) * st.getTailleEnregistrement() + positionBin;
			raf.seek(raf.length() - positionAModifier);
			raf.writeInt(positionNoeud);
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public int calculerID() {
		int noeudID = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			raf.seek(raf.length() - 4);
			noeudID = ((int) raf.readInt()) + 1;
		} catch (IOException ioe) {
			ioe.getMessage();
		}
		return noeudID;
	}

	public int trouverDernierID() {
		int dernierID = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "r");) {
			raf.seek(raf.length() - 4);
		} catch (IOException ioe) {
			ioe.getMessage();
		}
		return dernierID;
	}

	public void ecrireNouveauNoeud(E st, int iDNoeudParent, int numDeNoeud) {

		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			// TODO modifier le length pour �crire dans les trous de suppression
			raf.seek(raf.length());
			raf.writeChars(st.uniformise());
			raf.writeInt((int) ((Stagiaire) st).getAnnee());
			// AJOUT NOEUD PARENT
			raf.writeInt(iDNoeudParent);
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(numDeNoeud);
		} catch (IOException ioe) {
			ioe.getMessage();

		}
	}

	public void supprimerNoeudDansBin(Noeud<? extends IEnregistrable> courant, int iDNoeudASuppr) {
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			raf.seek(raf.length() - 4);
			int dernierID = ((int) raf.readInt());
			int positionDebutNoeudSuppr = (dernierID - iDNoeudASuppr) * courant.getStagiaire().getTailleEnregistrement()
					+ courant.getStagiaire().getTailleEnregistrement();
			raf.seek(raf.length() - positionDebutNoeudSuppr);
			raf.writeChars(courant.getStagiaire().uniformiseVide());
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public void reecrirePositionNoeud(Noeud<? extends IEnregistrable> st, int idNoeudSuccesseur, int idNoeud) {
		int positionAModifier = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			raf.seek(idNoeud * st.getStagiaire().getTailleEnregistrement() + 108);

			// ID PARENT
			int idNoeudParent = raf.readInt();
			raf.getFilePointer();

			// SE POSITIONNE EN DEBUT DE NOEUD GAUCHE DU PARENT
			raf.seek(idNoeudParent * st.getStagiaire().getTailleEnregistrement() + 112);
			// LIT LE NOEUD GAUCHE
			int test = raf.readInt();
			if (test == idNoeud) {
				raf.seek(idNoeudParent * st.getStagiaire().getTailleEnregistrement() + 112);
				raf.writeInt(idNoeudSuccesseur);
			} else {
				raf.writeInt(idNoeudSuccesseur);
			}

			// MODIFICATION DU SUCCESSEUR
			raf.getFilePointer();

			// SE POSITIONNE EN DEBUT DE NOEUD PARENT DU SUCCESSEUR
			raf.seek(idNoeudSuccesseur * st.getStagiaire().getTailleEnregistrement() + 108);
			raf.writeInt(idNoeudParent);
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public void reecrirePositionParentFeuille(Noeud<? extends IEnregistrable> st, int idNoeudSuccesseur, int idNoeud) {
		int positionAModifier = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			raf.seek(idNoeud * st.getStagiaire().getTailleEnregistrement() + 108);
			// ID PARENT
			int idNoeudParent = raf.readInt();
			raf.getFilePointer();
			//// SE POSITIONNE EN DEBUT DU PARENT DU PARENT
			raf.seek(idNoeudParent * st.getStagiaire().getTailleEnregistrement() + 108);
			raf.writeInt(idNoeud);
			raf.writeInt(idNoeudSuccesseur);
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public void reecrirePositionNoeudCas3(Noeud<? extends IEnregistrable> st, int noeudParent, int noeudG, int noeudD,
			int idNoeud) {
		int positionAModifier = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			// if (idNoeud != 0) {
			raf.seek(idNoeud * st.getStagiaire().getTailleEnregistrement() + 108);
			// ID PARENT //REECRIT POSITIONS DU NOEUD DE REMPLACEMENT 0 4 2
			raf.writeInt(noeudParent);
			raf.writeInt(noeudG);
			raf.writeInt(noeudD);

			// MODIFICATION NG DU NOEUD PARENT LACROIX -1 7 3 0 //TODO BOUCLE DE COMPA PR ND
			raf.getFilePointer();
			if (noeudParent == 0) {
				raf.seek(112);
				raf.writeInt(idNoeud);
			} else {
				raf.seek(noeudParent * st.getStagiaire().getTailleEnregistrement() + 112);
				raf.writeInt(idNoeud);
			}
			// NOUVEAU PARENT DU NOEUD GAUCHE : GARIJO 7 7 -1 2
			// MANQUE LE -1 SUR PERTE DU NOEUD DANS LA PETITE RECURSIVE
			raf.getFilePointer();
			raf.seek(noeudG * st.getStagiaire().getTailleEnregistrement() + 108);
			raf.writeInt(idNoeud);
			// NOUVEAU PARENT DU NOEUD DROIT
			raf.getFilePointer();
			raf.seek(noeudD * st.getStagiaire().getTailleEnregistrement() + 108);
			raf.writeInt(idNoeud);
			raf.writeInt(st.getStagiaire().getIDNoeudINIT());
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public int recupererIdentifiants(Noeud<? extends IEnregistrable> st, int positionNoeud) {
		int idNoeudParent = 0;
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			raf.seek(positionNoeud * st.getStagiaire().getTailleEnregistrement() + 108);
			idNoeudParent = raf.readInt();
		} catch (IOException ioe) {
			ioe.getMessage();
		}
		return idNoeudParent;
	}

}

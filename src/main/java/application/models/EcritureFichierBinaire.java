package application.models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import application.LanceurTestFXProjet1;


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

	public void ecrireNouveauNoeud(E st, int numDeNoeud) {
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			// TODO modifier le length pour écrire dans les trous de suppression
			raf.seek(raf.length());
			raf.writeChars(st.uniformise());
			raf.writeInt((int) ((Stagiaire) st).getAnnee());
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(numDeNoeud);
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	public void supprimerNoeudDansBin(E st, int iDNoeudASuppr) {
		try (RandomAccessFile raf = new RandomAccessFile(resource1.getFile(), "rw");) {
			// recupère le dernier id du fichier
			raf.seek(raf.length() - 4);
			int dernierID = ((int) raf.readInt());
			// calcule la position de la sequence à supprimer
			int positionDebutNoeudSuppr = (dernierID - iDNoeudASuppr) * st.getTailleEnregistrement()
					+ st.getTailleEnregistrement();
			raf.seek(raf.length() - positionDebutNoeudSuppr);
			raf.writeChars(st.uniformiseVide());
			raf.writeInt(0);
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(st.getIDNoeudINIT());
			raf.writeInt(st.getIDNoeudINIT());

		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

}

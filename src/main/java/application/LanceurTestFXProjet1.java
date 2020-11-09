package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import application.controleurs.LoginControleur;
import application.models.ArbreBinaireModel;
import application.models.Stagiaire;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LanceurTestFXProjet1 extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginControleur controleur = new LoginControleur();
			URL resource = getClass().getClassLoader().getResource("Login.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setController(controleur);
			Pane root = loader.load();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
			controleur.setStage(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String CHEMIN_FICHIER_STAGIAIRE_DON = "STAGIAIRES.DON";
	private static final String LISTE_STAGIAIRE_FICHIER = "FileBin.bin";
	URL resource1 = initResourceBin();

	private URL initResourceBin() {
		return getClass().getClassLoader().getResource(LISTE_STAGIAIRE_FICHIER);
	}

	public static void main(String[] args) throws IOException {

		LanceurTestFXProjet1 lanceur = new LanceurTestFXProjet1();

		lanceur.lectureSeuleFichier(0);

		launch(args);
	}

	public LanceurTestFXProjet1() throws IOException {

		URL resource = getClass().getClassLoader().getResource(CHEMIN_FICHIER_STAGIAIRE_DON);
		// AU PREMIER LANCEMENT : CHARGEMENT DU FICHIER SOURCE .DON
		// AU SECOND LANCEMENT : FICHIER .BIN COMME SOURCE DE DONNEES
		if (resource1 == null) {
			String pathBin = new File(resource.getPath()).getParent() + File.separator + LISTE_STAGIAIRE_FICHIER;
			new File(pathBin).createNewFile();
			resource1 = initResourceBin();

			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(resource.getFile()), "UTF-8"))) {

				int nb = 0;
				int numero = 0;
				String line;

				Stagiaire st = new Stagiaire();

				String nom = null, prenom = null, departement = null, promo = null;
				int annee = 0;

				while ((line = bufferedReader.readLine()) != null) {
					numero++;
					if (numero % 6 == 1)
						st.setNom(line);
					if (numero % 6 == 2)
						st.setPrenom(line);
					if (numero % 6 == 3)
						st.setDepartement(line);
					if (numero % 6 == 4)
						st.setPromotion(line);
					if (numero % 6 == 5)
						st.setAnnee(Integer.parseInt(line));
					if (line.contentEquals("*")) {
						numero = 0;

						Data.getInstance().getArbreStagiaire().ajouterNoeud(st);
						st = new Stagiaire();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// LECTURE DU FICHIER BINAIRE
	public void lectureSeuleFichier(long position) {
		StringBuilder stNom = new StringBuilder();
		StringBuilder stPrenom = new StringBuilder();
		StringBuilder stDepartement = new StringBuilder();
		StringBuilder stPromotion = new StringBuilder();
		StringBuilder stAnnee = new StringBuilder();
		StringBuilder stNoeud = new StringBuilder();
		StringBuilder myStringBuilder = new StringBuilder();

		try (RandomAccessFile raf = new RandomAccessFile(

				resource1.getFile(), "r")) {

			Stagiaire st = new Stagiaire();

			while (position % 124 < 121) {

				stNom.setLength(0);
				for (int i = 0; i < 21; i++) {
					stNom.append(raf.readChar());
				}
				String nom = stNom.toString().trim();

				if (nom.startsWith("*")) {
					position = position + 124;
					raf.seek(position);
					continue;
				}

				st.setNom(nom);

				position = position + 42;
				raf.seek(position);

				stPrenom.setLength(0);
				for (int i = 0; i < 18; i++) {
					stPrenom.append(raf.readChar());
				}
				String prenom = stPrenom.toString().trim();
				st.setPrenom(prenom);

				position = position + 36;
				raf.seek(position);

				stDepartement.setLength(0);
				for (int i = 0; i < 3; i++) {
					stDepartement.append(raf.readChar());
				}
				String departement = stDepartement.toString().trim();
				st.setDepartement(departement);

				position = position + 6;
				raf.seek(position);

				stPromotion.setLength(0);
				for (int i = 0; i < 10; i++) {
					stPromotion.append(raf.readChar());
				}
				String promotion = stPromotion.toString().trim();
				st.setPromotion(promotion);

				position = position + 20;
				raf.seek(position);

				int annee = raf.readInt();
				stAnnee.setLength(0);
				st.setAnnee(annee);

				position = position + 16;
				raf.seek(position);

				int iD = raf.readInt();

				Data.getInstance().getArbreStagiaireBin().creationArbredeFicherBin(st, iD);

				st = new Stagiaire();
				position = position + 4;
			}

		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	// METHODE DE RECHERCHE MULTI-CRITERES
	public static List<Stagiaire> rechercheFiltre(ArbreBinaireModel arbre, String filtreNom, String filtreDepartement,
			String filtrePromotion, Integer filtreAnnee) {
		List<Stagiaire> list = new ArrayList<Stagiaire>();
		Predicate<Stagiaire> filter = e -> (filtreNom.isEmpty() || e.getNom().startsWith(filtreNom))
				&& (filtreDepartement.isEmpty() || e.getDepartement().startsWith(filtreDepartement))
				&& (filtrePromotion.isEmpty() || e.getPromotion().startsWith(filtrePromotion))
				&& (filtreAnnee == null || e.getAnnee() == Integer.valueOf(filtreAnnee));
		ArbreBinaireModel<Stagiaire> stagiaireFiltre = arbre.filter(filter);
		for (int i = 0; i <= stagiaireFiltre.getSize(); i++) {
			Stagiaire el = stagiaireFiltre.get(i);
			if (el != null)
				list.add(el);
		}
		return list;
	}
}

package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			// scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			controleur.setStage(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String CHEMIN_FICHIER_STAGIAIRE_DON = "STAGIAIRES.DON";

	public static void main(String[] args) throws IOException {

		LanceurTestFXProjet1 lanceur = new LanceurTestFXProjet1();

		launch(args);
	}

	public LanceurTestFXProjet1() {
		ArbreBinaireModel<Stagiaire> arbreStagiaireDon = new ArbreBinaireModel<Stagiaire>();
		// ArbreBinaireModel<Stagiaire>();

		// List<Stagiaire> listeStagiaire = new ArrayList<Stagiaire>();
		URL resource = getClass().getClassLoader().getResource(CHEMIN_FICHIER_STAGIAIRE_DON);
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
			// System.out.println(arbreStagiaire.getSize());
			// listeStagiaires = arbreStagiaire.infixe(arbreStagiaire.getRacine());

			// Data.getInstance().getListeStagiaires().add(arbreStagiaire.get(i));

//			System.out.print(listeStagiaires);
			// listeStagiaires.forEach(stag -> System.out.println(stag));

			// for (int i = 0; i < arbreStagiaire.getSize(); i++) {
			// lectureSeuleFichier(120 * i);
			// }

//			rechercheFiltre(arbreStagiaire);

		} catch (IOException e) {
			e.getMessage();
		}
		// listeStagiaire.forEach(stag -> System.out.println(stag));

	}

	public void rechercheFiltre(ArbreBinaireModel arbre) {
		String filtreNom = null;
		String filtreDepartement = null;
		String filtrePromotion = null;
		Integer filtreAnnee = null;
		Predicate<Stagiaire> filter = e -> (filtreNom == null || e.getNom().startsWith(filtreNom))
				&& (filtreDepartement == null || e.getDepartement().startsWith(filtreDepartement))
				&& (filtrePromotion == null || e.getPromotion().startsWith(filtrePromotion))
				&& (filtreAnnee == null || e.getAnnee() == filtreAnnee);
		ArbreBinaireModel<Stagiaire> stagiaireFiltre = arbre.filter(filter);
		for (int i = 0; i < stagiaireFiltre.getSize(); i++) {
			System.out.println(stagiaireFiltre.get(i));
		}

	}

	public void lectureSeuleFichier(long position) {
<<<<<<< HEAD

		// ArbreBinaireModel<Stagiaire> arbreStagiaireBin = new ArbreBinaireModel<>();

=======

		// ArbreBinaireModel<Stagiaire> arbreStagiaireBin = new ArbreBinaireModel<>();

>>>>>>> b8a4e28... lecteur binaire fini
		// passer la position en argument

		try (RandomAccessFile raf = new RandomAccessFile(
				"C:\\Users\\Stagiaire\\hubiC\\_FormationISIKA\\PROJET_1\\Stagiaire.bin", "r")) {
			// while(true) {

			Stagiaire st = new Stagiaire();
			while (position % 120 < 117) {

				raf.seek(position);
				String nom = readString(raf, 21);
				if (nom.contains("*")) {
					position = position + 120;
					raf.seek(position);
					continue;
				}
				st.setNom(nom);
				position = position + 42;
				raf.seek(position);
				//
				String prenom = readString(raf, 18);
				st.setPrenom(prenom);
				position = position + 36;
				raf.seek(position);
//
				String departement = readString(raf, 4);
				st.setDepartement(departement);
				position = position + 6;
				raf.seek(position);
//
				String promotion = readString(raf, 10);
				st.setPromotion(promotion);
				position = position + 20;
				raf.seek(position);
//
				int annee = raf.readInt();
				st.setAnnee(annee);
				position = position + 12;
//
				raf.seek(position);
				int iD = raf.readInt();

				// stagiaire ajouter dans l'arbre
				System.out.println(st + "" + iD);
				// Data.getInstance().getArbreStagiaire().creationArbredeFicherBin(st, id);
				st = new Stagiaire();
				position = position + 4;
			}

		} catch (

		IOException ioe) {
			ioe.getMessage();
		}
	}
	// raf.close();

	private String readString(RandomAccessFile raf, int size) throws IOException {
		byte[] tmp = new byte[size];
		raf.read(tmp);
		String nom = new String(tmp);
		return nom;
	}
}

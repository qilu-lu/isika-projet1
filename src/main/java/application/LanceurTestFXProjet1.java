package application;

import java.io.BufferedReader;
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
			//scene.getStylesheets().add(getClass().getClassLoader().getResource("ressources/vues/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			controleur.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final String CHEMIN_FICHIER_STAGIAIRE_DON = "STAGIAIRES.DON";


	public static void main(String[] args) {
		
		LanceurTestFXProjet1 lanceur = new LanceurTestFXProjet1();
		launch(args);
	}

	public LanceurTestFXProjet1() {
		ArbreBinaireModel<Stagiaire> arbreStagiaire = new ArbreBinaireModel<Stagiaire>();

		List<Stagiaire> listeStagiaire = new ArrayList<Stagiaire>();
		URL resource = getClass().getClassLoader().getResource(CHEMIN_FICHIER_STAGIAIRE_DON);
		try (
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(new FileInputStream( resource.getFile()),"UTF-8"))
				)
		{
			int nb=0;
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

					arbreStagiaire.ajouterNoeud(st);
					st = new Stagiaire();
					//arbreStagiaire.ajouterNoeud(st);
				}
			}
			//			System.out.println(arbreStagiaire.getSize());
			//			arbreStagiaire.infixe(arbreStagiaire.getRacine());

			//			for (int i = 0; i < arbreStagiaire.getSize(); i++) {
			//				lectureSeuleFichier(120 * i);
			//			}
			rechercheFiltre(arbreStagiaire);

		} catch (IOException e) {
			e.getMessage();
		}
		//		listeStagiaire.forEach(stag -> System.out.println(stag));

	}

	public void rechercheFiltre(ArbreBinaireModel arbre) {
		String filtreNom = null;
		String filtreDepartement = null;
		String filtrePromotion = null;
		Integer filtreAnnee = 2014;		
		Predicate<Stagiaire> filter = e -> (filtreNom == null || e.getNom().startsWith(filtreNom))
				&& (filtreDepartement == null || e.getDepartement().startsWith(filtreDepartement))
				&& (filtrePromotion == null || e.getPromotion().startsWith(filtrePromotion))
				&& (filtreAnnee == null || e.getAnnee() == filtreAnnee);		
		ArbreBinaireModel<Stagiaire> stagiaireFiltre = arbre.filter(filter);	
		for (int i = 0; i < stagiaireFiltre.getSize(); i++) {
			System.out.println(stagiaireFiltre.get(stagiaireFiltre.getRacine(), i));
		}

	}

	public void lectureSeuleFichier(long position) { 


		StringBuilder stNom = new StringBuilder();
		StringBuilder stPrenom = new StringBuilder();
		StringBuilder stDepartement = new StringBuilder();
		StringBuilder stPromotion = new StringBuilder();
		StringBuilder stAnnee = new StringBuilder();
		StringBuilder stNoeudG = new StringBuilder();
		StringBuilder stNoeudD = new StringBuilder();
		StringBuilder stNoeudDoublon = new StringBuilder();
		StringBuilder stNoeud = new StringBuilder();
		List<Stagiaire> listeExportBin = new ArrayList<Stagiaire>();

		StringBuilder stagiaireNoeud = new StringBuilder();
		// passer la position en argument

		try (RandomAccessFile raf = new RandomAccessFile(
				"C:\\Users\\Stagiaire\\hubiC\\_FormationISIKA\\PROJET_1\\Stagiaire.bin", "r")) {
			//			while(true) {

			raf.seek(position);

			for (int i = 0; i < 21; i++) {
				stNom.append(raf.readChar());
			}
			String nom = stNom.toString().trim();
			stagiaireNoeud.append(nom);

			raf.seek(position + 42);
			for (int i = 0; i < 18; i++) {
				stPrenom.append(raf.readChar());
			}
			String prenom = stPrenom.toString().trim();
			stagiaireNoeud.append(prenom);

			raf.seek(position + 78);
			for (int i = 0; i < 3; i++) {
				stDepartement.append(raf.readChar());
			}
			String departement = stDepartement.toString().trim();
			stagiaireNoeud.append(" " + departement);

			raf.seek(position + 84);
			for (int i = 0; i < 10; i++) {
				stPromotion.append(raf.readChar());
			}
			String promotion = stPromotion.toString().trim();
			stagiaireNoeud.append(" " + promotion);

			raf.seek(position + 104);
			stAnnee.append(raf.readInt());
			String annee = stAnnee.toString();
			stagiaireNoeud.append(" " + Integer.parseInt(annee));

			raf.seek(position + 108);
			stNoeudG.append(raf.readInt());
			String noeudG = stNoeudG.toString();
			stagiaireNoeud.append(" " + Integer.parseInt(noeudG));

			raf.seek(position + 112);
			String noeudD;
			stNoeudD.append(raf.readInt());
			noeudD = stNoeudD.toString();
			stagiaireNoeud.append(" " + Integer.parseInt(noeudD));

			raf.seek(position + 116);
			String noeud;
			stNoeud.append(raf.readInt());
			noeud = stNoeud.toString();
			stagiaireNoeud.append(" " + Integer.parseInt(noeud));

			System.out.println(stagiaireNoeud);

			//			}

		} catch (IOException ioe) {
			ioe.getMessage();
		}
		// raf.close();
	}
}

package application;

import java.io.BufferedReader;
import java.io.File;
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
		// ArbreBinaireModel<Stagiaire> arbreStagiaireDon = new
		// ArbreBinaireModel<Stagiaire>();
		// ArbreBinaireModel<Stagiaire>();

		// List<Stagiaire> listeStagiaire = new ArrayList<Stagiaire>();
		URL resource = getClass().getClassLoader().getResource(CHEMIN_FICHIER_STAGIAIRE_DON);

		if (resource1 == null) {
			String pathBin = new File(resource.getPath()).getParent() + File.separator + LISTE_STAGIAIRE_FICHIER;
			new File(pathBin).createNewFile();
			resource1 = initResourceBin();

			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(resource.getFile()), "UTF-8"))) {

				// Delete fichier FileBin.bin ou faire un if(il existe) ne pas recopier encore
				// une fois dedans ...

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
	// listeStagiaire.forEach(stag -> System.out.println(stag));

	// }

	public static List<Stagiaire> rechercheFiltre(ArbreBinaireModel arbre, String filtreNom, 
			String filtreDepartement, String filtrePromotion, String filtreAnnee) { 
		//		String filtreNom = null;
		//		String filtreDepartement = null;
		//		String filtrePromotion = null;
		//		Integer filtreAnnee = null;
		List<Stagiaire>list=new ArrayList<Stagiaire>();
		Predicate<Stagiaire> filter = e -> (filtreNom.isEmpty() || e.getNom().startsWith(filtreNom))
				&& (filtreDepartement.isEmpty() || e.getDepartement().startsWith(filtreDepartement))
				&& (filtrePromotion.isEmpty() || e.getPromotion().startsWith(filtrePromotion))
				&& (filtreAnnee.isEmpty() || String.valueOf(e.getAnnee()) == filtreAnnee);
		ArbreBinaireModel<Stagiaire> stagiaireFiltre = arbre.filter(filter);
		//		for (int i = 0; i < stagiaireFiltre.getSize(); i++) {
		//			stagiaireFiltre.get(i);
		for (int i=0;i<stagiaireFiltre.getSize();i++) {
			list.add(stagiaireFiltre.get(i));
			//TODO arbre à afficher en infixe
		}

		return  list;
	}



	public void lectureSeuleFichier(long position) {
		StringBuilder stNom = new StringBuilder();
		StringBuilder stPrenom = new StringBuilder();
		StringBuilder stDepartement = new StringBuilder();
		StringBuilder stPromotion = new StringBuilder();
		StringBuilder stAnnee = new StringBuilder();
		StringBuilder stNoeud = new StringBuilder();
		StringBuilder myStringBuilder =new StringBuilder();


		// ArbreBinaireModel<Stagiaire> arbreStagiaireBin = new ArbreBinaireModel<>();
		// passer la position en argument
		try (RandomAccessFile raf = new RandomAccessFile(

				resource1.getFile(), "r")) {


			Stagiaire st = new Stagiaire();

			while (position % 120 < 117) {


				stNom.setLength(0);
				for(int i=0;i<21;i++) {
					stNom.append(raf.readChar());
				}
				String nom = stNom.toString().trim();
				//			if (nom.contains("*")) {
				//				position = position + 120;
				//				raf.seek(position);
				//				continue;
				//			}
				st.setNom(nom);

				position = position + 42;
				raf.seek(position);



				stPrenom.setLength(0);
				for(int i=0;i<18;i++) {
					stPrenom.append(raf.readChar());
				}
				String prenom = stPrenom.toString().trim();
				st.setPrenom(prenom);

				position = position + 36;
				raf.seek(position);



				stDepartement.setLength(0);
				for(int i=0;i<3;i++) {
					stDepartement.append(raf.readChar());
				}
				String departement = stDepartement.toString().trim();
				st.setDepartement(departement);

				position = position + 6;
				raf.seek(position);



				stPromotion.setLength(0);
				for(int i=0;i<10;i++) {
					stPromotion.append(raf.readChar());
				}
				String promotion = stPromotion.toString().trim();
				st.setPromotion(promotion);

				position = position + 20;
				raf.seek(position);



				int annee = raf.readInt();
				stAnnee.setLength(0);
				st.setAnnee(annee);

				position = position + 12;
				raf.seek(position);


				int iD = raf.readInt();	
				System.out.println(st + "" + iD);
				Data.getInstance().getArbreStagiaireBin().creationArbredeFicherBin(st, iD);

				st = new Stagiaire();
				position = position + 4;
			}

		} catch (IOException ioe) {
			ioe.getMessage();
		}

	}
	// raf.close();

	//	private String readString(RandomAccessFile raf, int size) throws IOException {
	//		byte[] tmp = new byte[size];
	//		raf.read(tmp);
	//		String nom = new String(tmp);
	//		return nom;
	//	}

}

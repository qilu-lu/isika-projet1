package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		readStagiaireFromFile();

//		// br.read();
//		ArbreStagiaire1<Stagiaire> arbre = new ArbreStagiaire1<Stagiaire>();
//		Stagiaire s1 = new Stagiaire("APC", "Pre");
//		Stagiaire s2 = new Stagiaire("BCZZD", "Pre");
//		Stagiaire s3 = new Stagiaire("mmmD", "Pre");
//		Stagiaire s4 = new Stagiaire("zewe", "Pre");
//		Stagiaire s5 = new Stagiaire("z", "Pre");
//		arbre.ajouterNoeud(s1);
//		arbre.ajouterNoeud(s2);
//		arbre.ajouterNoeud(s3);
//		arbre.ajouterNoeud(s4);
//		arbre.ajouterNoeud(s5);
//		arbre.infixe(arbre.getRacine());
//		arbre.supprimer(arbre.getRacine(), s1);
//		System.out.println();
//		arbre.infixe(arbre.getRacine());
//		arbre.recherche(arbre.getRacine(), s1);

		launch(args);

		// ArbreStagiaire1<Stagiaire> s = new ArbreStagiaire1<>();
		// s.ajouterNoeud(new Stagiaire());
	}

	private static List<Stagiaire> readStagiaireFromFile() {
		File file = new File(Main.class.getClassLoader().getResource("STAGIAIRES.DON").getFile());

		BufferedReader bufferedReader = null;
		LineNumberReader ligneCourante = null;

		List<Stagiaire> listeStagiaire = new ArrayList<Stagiaire>();

		try {
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			ligneCourante = new LineNumberReader(fileReader);
			int numero = 0;
			String line;

			Stagiaire st = new Stagiaire();

			while (bufferedReader.ready()) { // tant que la ligne suivante existe

				// Stagiaire st = new Stagiaire();
				line = ligneCourante.readLine();
				numero++;

				if (numero % 6 == 1) {
					st.setNom(line);
				}

				if (numero % 6 == 2) {
					st.setPrenom(line);
				}
				if (numero % 6 == 3) {
					st.setDepartement(line);
				}
				if (numero % 6 == 4) {
					st.setPromotion(line);
				}
				if (numero % 6 == 5) {
					st.setAnnee(Integer.parseInt(line));
				}
				if (line.equals("*")) {
					numero = 0;
					listeStagiaire.add(st);
					st = new Stagiaire();
				}

			}

			for (Stagiaire stagiaire : listeStagiaire) {
				System.out.println(stagiaire);
			}
			// }

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("Le fichier n'existe pas");
		} catch (IOException e) {
			System.out.println("Impossible de lire le contenu du fichier");
		}

		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Impossible de fermer le fichier");
		} catch (NullPointerException e) {
			System.out.println("Impossible d'ouvrir le fichier");
		}
		return listeStagiaire;
	}

//	public List<Stagiaire> rechercheParAnnee(int a) {
//		List<Stagiaire> list = new ArrayList<>();
//		for (Stagiaire stagiaire : list) {
//
//		}
//	}

}
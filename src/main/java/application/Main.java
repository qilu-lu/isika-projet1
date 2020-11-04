package application;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
		// 166 pour cheque stagiaire
		List<Stagiaire> l = ReadFile.readStagiaireFromFile();
		List<Stagiaire> s = ReadFile.remplir(l);
		ArbreStagiaire1<Stagiaire> arbre = new ArbreStagiaire1<Stagiaire>();

		for (Stagiaire stagiaire : s) {
			arbre.ajouterNoeud(stagiaire);
		}
System.out.println(arbre.getsize());
		String filtreNom = null;
		String filtreDepartement = null;
		String filtrePromotion = null;
		Integer filtreAnnee = 2000;

		Predicate<Stagiaire> filter = e -> (filtreNom == null || e.getNom().startsWith(filtreNom))
				&& (filtreDepartement == null || e.getdepartement().startsWith(filtreDepartement))
				&& (filtrePromotion == null || e.getPromotion().startsWith(filtrePromotion))
				&& (filtreAnnee == null || e.getAnnee() == filtreAnnee);

		ArbreStagiaire1<Stagiaire> stagiaireFiltre = arbre.filter(filter);

		for (int i = 0; i < stagiaireFiltre.size; i++) {
			System.out.println(stagiaireFiltre.get(stagiaireFiltre.getRacine(), i));

		// List<Stagiaire> l = ReadFile.readStagiaireFromFile();
		// System.out.println(ReadFile.calculerTailleNom(ReadFile.remplir(l)));
		// System.out.println(ReadFile.calculerTaillePreNom(l));
		// System.out.println(ReadFile.calculerTailleDepartement(l));
		// System.out.println(ReadFile.calculerTailleDepartement1(l));
		// System.out.println(ReadFile.calculerTaillePromotion(l));
		// List<Stagiaire> s = ReadFile.remplir(l);
//aligner les stagiaires
//		for (Stagiaire stagiaire : s) {
//			System.out.println(stagiaire.getNom() + stagiaire.getNom().length());
//			for (int i = 0; i < stagiaire.getNom().length(); i++) {
//			System.out.println(stagiaire.getNom().codePointAt(i) + "=>" + stagiaire.getNom().charAt(i));
//		}
//			System.out.println(stagiaire);
//		}
//		
//		ArbreStagiaire1<Stagiaire> arbre = new ArbreStagiaire1<Stagiaire>();
//		for (Stagiaire stagiaire : s) {
//			arbre.ajouterNoeud(stagiaire);
//		}
		// System.out.println(arbre.getsize());
		// System.out.println(arbre.getRacine());
		// arbre.infixe(arbre.getRacine());

//		for (int i = 0; i < arbre.getsize(); i++) {
//			System.out.println(arbre.get(arbre.getRacine(), i)+" "+i);
//			
//	}
	//arbre.infixe(arbre.getRacine());
		DataOutputStream out = new DataOutputStream(new FileOutputStream("FileBin.bin"));

		
		out.flush();
		out.close();

		RandomAccessFile in = new RandomAccessFile("FileBin.bin", "r");
		int len = (int) (in.length() / Stagiaire.RecordSize);

//		for (long i = 0; i < len; i++) {
//			in.seek(i * Stagiaire.RecordSize);
//
//			Stagiaire str = readStagiaire(in);
//			System.out.println(str.toString());
//		}
		//System.out.println();
		launch(args);
	}
	}
	private static void writeStr(DataOutputStream out, Stagiaire s) throws IOException {
		writeString(s.getNom(), Stagiaire.NomSize, out);
		writeString(s.getPrenom(), Stagiaire.PrenomSize, out);
		writeString(s.getdepartement(), Stagiaire.DepartementSize, out);
		writeString(s.getPromotion(), Stagiaire.PromotionSize, out);
		out.write(s.getAnnee());

	}

	private static void writeString(String nom, int size, DataOutputStream out) throws IOException {
		for (int i = 0; i < size; i++) {
			char c = 0;
			if (i < nom.length()) {
				c = nom.charAt(i);
			}
			out.writeChar(c);
		}

	}

	private static Stagiaire readStagiaire(DataInput in) throws IOException {
		String nom = readString(Stagiaire.NomSize, in);
		String prenom = readString(Stagiaire.PrenomSize, in);
		String departement = readString(Stagiaire.DepartementSize, in);
		String promotion = readString(Stagiaire.PromotionSize, in);
		int annee = in.readInt();

		return new Stagiaire(nom, prenom, departement, annee);
	}

	private static String readString(int size, DataInput in) throws IOException {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < size; i++) {
			char c = in.readChar();

			if (c == 0) {
				continue;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();

	}

}
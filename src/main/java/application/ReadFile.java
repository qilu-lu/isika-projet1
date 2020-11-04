package application;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	static List<Stagiaire> readStagiaireFromFile() {
		File file = new File(Main.class.getClassLoader().getResource("STAGIAIRES.DON").getFile());

		BufferedReader bufferedReader = null;
		LineNumberReader ligneCourante = null;

		List<Stagiaire> listeStagiaire = new ArrayList<Stagiaire>();

		try {
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			int numero = 0;
			String line;

			Stagiaire st = new Stagiaire();

			while ((line = bufferedReader.readLine()) != null) { // tant que la ligne suivante existe
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
					System.out.println(st.getNom() + " " + st.getPrenom());
					st = new Stagiaire();
				}

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

	public static int calculerTailleNom(List<Stagiaire> list) {
		int length = 0;
		String nom = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNom().length() > length) {
				length = list.get(i).getNom().length();
				nom = list.get(i).getNom();
			}
		}
		System.out.println(nom);
		return length;
	}

	public static List<Stagiaire> remplir(List<Stagiaire> list) {
		// List<Stagiaire>list2=new ArrayList<Stagiaire>();
		int lengthnom = 21;
		int lengthprenom = 18;
		int lengthPromo = 10;
		String nouveauxPrenom = "";
		String nouveauxPromo = "";
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNom(padRight(list.get(i).getNom(), 21));
		}
		for (int i = 0; i < list.size(); i++) {
			int num2 = lengthprenom - list.get(i).getPrenom().length();
			nouveauxPrenom = list.get(i).getPrenom();
			for (int j = 0; j < num2; j++) {
				nouveauxPrenom = nouveauxPrenom + " ";
			}
			list.get(i).setPrenom(nouveauxPrenom);
		}
		for (int i = 0; i < list.size(); i++) {
			int num3 = lengthPromo - list.get(i).getPromotion().length();
			nouveauxPromo = list.get(i).getPromotion();
			for (int j = 0; j < num3; j++) {
				nouveauxPromo = nouveauxPromo + " ";
			}
			list.get(i).setPromotion(nouveauxPromo);
		}

		return list;
	}

	public static String padRight(String s, int n) {
		return String.format("%-" + n + "s", s);
	}

	public static int calculerTaillePreNom(List<Stagiaire> list) {
		int length = 0;
		String prenom = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPrenom().length() > length) {
				length = list.get(i).getPrenom().length();
				prenom = list.get(i).getPrenom();
			}
		}
		System.out.println(prenom);
		return length;
	}

	public static int calculerTailleDepartement(List<Stagiaire> list) {
		int length = 0;
		String departement = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPrenom().length() > length) {
				length = list.get(i).getdepartement().length();
				departement = list.get(i).getdepartement();
			}
		}
		System.out.println(departement);
		return length;
	}

//	public static int calculerTailleDepartement1(List<Stagiaire>list) {
//		int length=2;
//		String departement=null;
//		for (int i = 0; i < list.size(); i++) {
//			if(list.get(i).getPrenom().length()<length) {
//				length=	list.get(i).getdepartement().length();
//				departement=list.get(i).getdepartement();
//			}	
//		}System.out.println(departement);
//		return length;	
//	}
	public static int calculerTaillePromotion(List<Stagiaire> list) {
		int length = 0;
		String promotion = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPromotion().length() > length) {
				length = list.get(i).getPromotion().length();
				promotion = list.get(i).getPromotion();
			}
		}
		System.out.println(promotion);

		for (Stagiaire stagiaire : list) {
			System.out.println(stagiaire.toString());
		}

		return length;
	}

}

package application.models;

import java.util.ArrayList;
import java.util.List;

public class StagiairesModel {
private List<Stagiaire> stagiaires;
	
	public StagiairesModel() {
		this.stagiaires = new ArrayList<>();
	}
	
	public boolean ajouterUnStagiaire(Stagiaire stagiaire) {
		// TODO : écrire le code qui permet d'ajouter le produit à la liste 
		// Avec les vérifications nécessaires (produits existant etc ...)
		// Si ajouté renvoyer TRUE, sinon renvoyer FALSE
		stagiaires.add(stagiaire);
		return true;
	}
	
//	public boolean modifierUnProduit(Stagiaire stagiaire) {
//		// TODO : écrire le code qui permet de modifier les infos d'un produit 
//		// Avec les vérifications nécessaires
//		// Si modifié renvoyer TRUE, sinon renvoyer FALSE
//		return false;
//	}
//	
//	public boolean supprimerProduit(Stagiaire stagiaire) {
//		// TODO : écrire le code qui permet de supprimer le produit de la liste 
//		// Si supprimé renvoyer TRUE, sinon renvoyer FALSE
//		return false;
//	}
//	
	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}
}
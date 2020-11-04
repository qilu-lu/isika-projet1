package application;

import java.util.ArrayList;
import java.util.List;

import application.models.ArbreBinaireModel;
import application.models.Stagiaire;

public class Data {
	
	private static Data instance=new Data();

	private List<Stagiaire> listeStagiaires = new ArrayList<>();

	private ArbreBinaireModel<Stagiaire> arbreStagiaire = new ArbreBinaireModel<>();
	
	public ArbreBinaireModel<Stagiaire> getArbreStagiaire() {
		return arbreStagiaire;
	}

	public static Data getInstance() {
		return instance;
	}

	public List<Stagiaire> getListeStagiaires() {
		return listeStagiaires;
	}
	

}

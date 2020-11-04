package application.models;

public interface IEnregistrable<E> extends Comparable<E> {
	
	public String uniformise();
	
	public int getTailleEnregistrement();
	
	public int getIDNoeudINIT();
	
	public int getPositionNoeudG();
	
	public int getPositionNoeudD();

	public String uniformiseVide();

}

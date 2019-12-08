package Tuples;

public class TuplePlante {
	private String nom;
	private Integer duree;

	public TuplePlante() {
	}
		
	public TuplePlante(String nom, Integer duree) {
		this.nom = nom;
		this.duree = duree;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nomPlante) {
		this.nom = nomPlante;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}
	
	@Override
	public String toString() {
		return "Nom: " + this.nom + "     Temps pour mûrir: " + this.duree.toString() + " jours"; 
	}



}

package Tuples;

public class TupleLot {

	private String nom;
	private Integer max_collab;
	
	public TupleLot(String nomLot, Integer maxCollabs) {
		this.nom = nomLot;
		this.max_collab = maxCollabs;
	}
	
	public TupleLot() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getMax_collab() {
		return max_collab;
	}

	public void setMax_collab(Integer max_collab) {
		this.max_collab = max_collab;
	}

	@Override
	public String toString() {
		return "Lot " + this.nom + ", Nb max. membres: " + this.max_collab.toString(); 
	}

}

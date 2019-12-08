package Tuples;

public class TupleAttribution {

	private Integer idMembre;
	private String nomLot;

	public TupleAttribution() {
	}

	public TupleAttribution(String nomLot, Integer noMembre) {
		this.nomLot = nomLot;
		this.idMembre = noMembre;
	}

	public Integer getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Integer idMembre) {
		this.idMembre = idMembre;
	}

	public String getNomLot() {
		return nomLot;
	}

	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}

	@Override
	public String toString() {
		return "Membre " + this.idMembre.toString() + " collabore sur le lot " + this.nomLot; 
	}

}

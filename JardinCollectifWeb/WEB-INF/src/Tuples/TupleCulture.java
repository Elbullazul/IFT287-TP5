package Tuples;

import java.sql.Date;

public class TupleCulture {

	private String nomLot;
	private String nomPlante;
	private Integer idMembre;
	private Integer nbExemplaires;
	private Date plantee;
	
	public static Integer NOM_LOT = 0;
	public static Integer NOM_PLANTE = 1;
	
	public TupleCulture() {
	}
	
	

	public TupleCulture(String nomLot, String nomPlante, Integer idMembre, Integer nbExemplaires, Date datePlantation) {
		this.nomLot = nomLot;
		this.nomPlante = nomPlante;
		this.idMembre = idMembre;
		this.nbExemplaires = nbExemplaires;
		this.plantee = datePlantation;
	}

	public String getNomLot() {
		return nomLot;
	}

	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}

	public String getNomPlante() {
		return nomPlante;
	}

	public void setNomPlante(String nomPlante) {
		this.nomPlante = nomPlante;
	}

	public Integer getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Integer idMembre) {
		this.idMembre = idMembre;
	}

	public Integer getNbExemplaires() {
		return nbExemplaires;
	}

	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}
	
	public Date getPlantee() {
		return plantee;
	}

	public void setPlantee(Date plantee) {
		this.plantee = plantee;
	}

	@Override
	public String toString() {
		return this.nbExemplaires + " " + this.nomPlante + "(s) en pousse depuis " + this.plantee.toString() + " dans " + this.nomLot + " par membre " + this.idMembre; 
	}

}

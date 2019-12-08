package Gestionnaires;

import java.sql.Date;
import java.util.Calendar;


import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;
import JardinCollectif.tables.TablePlante;
import Tuples.TupleCulture;

public class GestionnaireCulture {
	
	private Connexion cx;
	
	private TableLot tlot;
	private TableMembre tmembre;
	private TableCulture tculture;
	private TablePlante tplante;
	private TableAttribution tatt;


	public GestionnaireCulture(TableLot lot, TablePlante plante, TableMembre membre, TableCulture culture, TableAttribution attribution)
			throws Exception {

		if (lot.getConnexion() != plante.getConnexion() || lot.getConnexion() != attribution.getConnexion()
				|| membre.getConnexion() != culture.getConnexion() || membre.getConnexion() != lot.getConnexion()) {
			throw new IFT287Exception("Les collections d'objets n'utilisent pas la meme connexion au serveur");
		}
		this.tlot = lot;
		this.tplante = plante;
		this.tculture = culture;
		this.tmembre = membre;
		this.tatt = attribution;
		
	}
	public void planterplante(String nomPlante, String nomLot, int noMembre, int nombreExemplaire, Date date)
			throws Exception {
		try {
			if (!tplante.existe(nomPlante)) {
				throw new IFT287Exception("Plante inexistante");
			}
			if (!tlot.existe(nomLot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(noMembre)) {
				throw new IFT287Exception("Membre inexistant");
			}
			if (!tatt.existe(nomLot,noMembre)) {
				throw new IFT287Exception("Membre: " + noMembre  + " ne fait pas partie de " + nomLot);
			}
			tculture.setCulture(nomPlante, nomLot, noMembre, nombreExemplaire, date);
			System.out.println(Integer.toString(nombreExemplaire) + " " + nomPlante + " s plantees sur lot " + nomLot);
		} catch (Exception e) {
			throw e;
		}
	}

	public void recolterplante(String nomPlante, String nomLot, int id) throws Exception {
		try {
			Calendar d = Calendar.getInstance();
			Calendar c = Calendar.getInstance();
			c.setTime(tculture.getCulture(nomPlante, nomLot).getPlantee());
			c.add(Calendar.DATE, tplante.getPlante(nomPlante).getDuree());

			if (!tplante.existe(nomPlante)) {
				throw new IFT287Exception("Plante inexistante");
			}
			if (!tlot.existe(nomLot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(id)) {
				throw new IFT287Exception("Membre inexistant");
			}
			if (!tatt.existe(nomLot,id)) {
				throw new IFT287Exception("Membre ne fait pas partie du lot");
			}
			if (tculture.getCulture(nomPlante,nomLot).getNbExemplaires() == 0) {
				throw new IFT287Exception(
						"Le lot " + nomLot + " ne contient aucun exemplaire de " + nomPlante);
			}
			if (d.before(c)) {
				throw new IFT287Exception("Le lot " + nomLot + " contient " + nomPlante + " pour la date"
						+ tculture.getCulture(nomPlante, nomLot).getPlantee());
			}
			//tculture.updateCulture(nbplantee, nomPlante, nomLot)
			tculture.updateCulture(0, nomPlante,nomLot);
		
			System.out.println("Exemplaires de " + nomPlante + " recolters");

		} catch (Exception e) {
			throw e;
		}
	}

	public void affichePlanteLot(String nomlot) throws Exception {
		try {
			if (!tlot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			for(TupleCulture t : tculture.listplanteLot(nomlot)) {
				
				System.out.println(t.getNomPlante());
			}
		} catch (Exception e) {
			throw e;
		}
	}

 
	
	
}

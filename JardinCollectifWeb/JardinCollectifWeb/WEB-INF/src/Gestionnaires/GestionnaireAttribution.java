package Gestionnaires;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;

public class GestionnaireAttribution {
	
	private Connexion cx;
	private TableLot tlot;
	private TableMembre tmembre;
	private TableAttribution tatt; 

	public GestionnaireAttribution(TableLot lot, TableMembre membre, TableAttribution attribution) throws Exception {
		if (lot.getConnexion() != membre.getConnexion() || attribution.getConnexion() != membre.getConnexion()) {
			throw new IFT287Exception("Les collections d'objets n'utilisent pas la meme connexion au serveur");
		}
		this.tlot = lot;
		this.tmembre = membre;
		this.tatt = attribution;
	}

	public void rejoindreLot(String nomlot, int id) throws Exception {
		try {
			if (!tlot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(id)) {
				throw new IFT287Exception("Le Membre " + id + " est inexistant");
			}
			if (tatt.existe(nomlot, id)) {
				throw new IFT287Exception("Le Membre " + id + " fait deja partie du lot");
			}
			tatt.setAttribution(id, nomlot);
			System.out.println(id +" a rejoint le lot "+ nomlot);
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}

	}
	
}

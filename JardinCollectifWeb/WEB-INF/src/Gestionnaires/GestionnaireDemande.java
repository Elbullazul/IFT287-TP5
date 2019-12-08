package Gestionnaires;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TableDemande;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;
import Tuples.TupleDemande;

public class GestionnaireDemande {
	private Connexion cx;
	private TableLot tlot;
	private TableMembre tmembre;
	private TableDemande tdemande;
	private static TupleDemande status;
	
	public GestionnaireDemande(TableLot lot, TableMembre membre, TableDemande demande) throws Exception {
		this.cx = demande.getConnexion();
		if (lot.getConnexion() != membre.getConnexion() || lot.getConnexion() != demande.getConnexion()) {
			throw new IFT287Exception("Les collections d'objets n'utilisent pas la meme connexion au serveur");
		}
		this.tdemande= demande;
		this.tlot=lot;
		this.tmembre =membre;
		
	}
	
	public void AjouterDemande(String nomlot, int noMembre) throws Exception {
		try {
			// verifie l'existence
			if (!tlot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(noMembre)) {
				throw new IFT287Exception("Membre inexistant");
			}

			if (tdemande.existe(noMembre, nomlot)) {
				throw new IFT287Exception("Demande deja ouverte");
			}
			tdemande.setDemande(noMembre, nomlot, status.STATUS_PENDING );

			System.out.println("Demande soumise");
		} catch (Exception e) {
			throw e;
		}
	}

	public void supprimerDemande(String nomlot, int noMembre) throws Exception {
		try {
			if (!tdemande.existe(noMembre, nomlot)) {
				throw new IFT287Exception("Demande inexistante");
			}
			int t = tdemande.getDemande(noMembre, nomlot).getStatus();
			
			tdemande.deleteDemande(noMembre, nomlot, t);
			System.out.println("Demande supprimer");
		} 
		catch (Exception e) {
			throw e;
		}
	}

	public void accepterDemande(String nomlot, int noMembre) throws Exception {
		try {
			if (!tlot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(noMembre)) {
				throw new IFT287Exception("Membre inexistant");
			}
			if (!tdemande.existe(noMembre, nomlot)) {
				throw new IFT287Exception("Demande pas ouverte");
			}
			if (tdemande.getDemande(noMembre,nomlot).getStatus() != status.STATUS_PENDING) {
				throw new IFT287Exception(nomlot + " est deja traitee");
			}

			tdemande.updateDemande(noMembre,nomlot,status.STATUS_APPROVED);
			System.out.println("Demande approuve");
		} catch (Exception e) {
			throw e;
		}
	}

	public void refuserDemande(String nomlot, int noMembre) throws Exception {
		try {
			if (!tlot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant");
			}
			if (!tmembre.existe(noMembre)) {
				throw new IFT287Exception("Membre inexistant");
			}
			if (tdemande.getDemande(noMembre, nomlot).getStatus() != status.STATUS_PENDING) {
				throw new IFT287Exception(nomlot + " est deja traitee");
			}

			tdemande.updateDemande(noMembre,nomlot,status.STATUS_DENIED);
			System.out.println("Demande refusee");
		} catch (Exception e) {
			throw e;
		}
	}

}

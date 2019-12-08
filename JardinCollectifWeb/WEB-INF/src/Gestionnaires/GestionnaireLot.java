package Gestionnaires;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TableLot;
import Tuples.TupleLot;

public class GestionnaireLot {
	private Connexion cx;
	private TableLot tLot;

	public GestionnaireLot(TableLot lot) {
		this.cx = lot.getConnexion();
		this.tLot = lot;
	}
	public void AjoutLot(String nomlot, int nbmaxmembre) throws Exception {
		try {
			if (nbmaxmembre <= 0) {
				throw new IFT287Exception("Nombre de Membre trop petit!");
			}
			if (tLot.existe(nomlot)) {
				throw new IFT287Exception("Lot existe deja.");
			}
			tLot.setLot(nomlot, nbmaxmembre);
		
		} catch (Exception e) {
			throw e;
		}
	}

	public void SupprimerLot(String nomlot) throws Exception {
		try {
			if (!tLot.existe(nomlot)) {
				throw new IFT287Exception("Lot inexistant.");
			}
			tLot.deleteLot(nomlot);
		} 
		catch (Exception e) {
			throw e;
		}
	}

	public void AfficheLots() throws Exception {
		try {
			if (tLot.listLot().isEmpty()) {
				throw new IFT287Exception("Aucun lot trouve.");
			}
			for(TupleLot t: tLot.listLot()) {
			System.out.println(t.toString());
			}
			} catch (Exception e) {
			throw e;
		}
	}

}

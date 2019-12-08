package Gestionnaires;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TablePlante;
import Tuples.TuplePlante;

public class GestionnairePlante {
	
	private TablePlante tPlante;
	private Connexion cx;
	
	public GestionnairePlante(TablePlante plante) {
	    this.tPlante = plante;
		this.cx = tPlante.getConnexion();
	}
	
	public void AjouterPlante(String nom, int jour) throws Exception {
		try {
			if (tPlante.existe(nom)) {
				throw new IFT287Exception("Le plante existe deja");
			}
			if (jour <= 0) {
				throw new IFT287Exception("Periode de germination doit etre superieur a:0");
			}
			tPlante.setPlante(nom, jour);

		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public void RetirerPlante(String nom) throws Exception {
		try {
			if (!tPlante.existe(nom)) {
				throw new IFT287Exception("Plante inexistante");
			}
			
			tPlante.deletePlante(nom);
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public void Afficheplantes() throws Exception {
		try {
			if (tPlante.listplante().isEmpty()) {
				throw new IFT287Exception("Aucune plante trouve");
				}
			for(TuplePlante t: tPlante.listplante()) {
				
				System.out.println(t.toString());
			}
					
		} 
		catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}
	
	
}

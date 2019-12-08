package Gestionnaires;

import java.sql.SQLException;

import JardinCollectif.Connexion;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableDemande;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;
import JardinCollectif.tables.TablePlante;

public class GestionnaireJardinCollectif {
	public Connexion cx;

	public GestionnaireMembre gestionMembre;
	public GestionnaireLot gestionLot;
	public GestionnairePlante gestionPlante;
	public GestionnaireDemande gestionDemande;
	public GestionnaireAttribution gestionAttribution;
	public GestionnaireCulture gestionCulture;

	private TableAttribution attributions;
	private TableCulture cultures;
	private TableDemande demandes;
	private TableLot lots;
	private TableMembre membres;
	private TablePlante plantes;

	public GestionnaireJardinCollectif(String serveur, String bd, String user, String password) throws Exception {
		cx = new Connexion(serveur, bd, user, password);

		lots = new TableLot(cx);
		plantes = new TablePlante(cx);
		membres = new TableMembre(cx);
		cultures = new TableCulture(cx);
		demandes = new TableDemande(cx);
		attributions = new TableAttribution(cx);

		setGestionLot(new GestionnaireLot(lots));
		setGestionPlante(new GestionnairePlante(plantes));
		setGestionMembre(new GestionnaireMembre(membres));
		setGestionCulture(new GestionnaireCulture(lots, plantes, membres, cultures, attributions));
		setGestionDemande(new GestionnaireDemande(lots, membres, demandes));
		setGestionAttribution(new GestionnaireAttribution(lots, membres, attributions));
	}

	public void fermer() throws SQLException {
		// fermer la connexion
		cx.fermer();
	}

	public GestionnaireMembre getGestionMembre() {
		return gestionMembre;
	}

	public void setGestionMembre(GestionnaireMembre gestionMembre) {
		this.gestionMembre = gestionMembre;
	}

	public GestionnaireLot getGestionLot() {
		return gestionLot;
	}

	public void setGestionLot(GestionnaireLot gestionLot) {
		this.gestionLot = gestionLot;
	}

	public GestionnairePlante getGestionPlante() {
		return gestionPlante;
	}

	public void setGestionPlante(GestionnairePlante gestionPlante) {
		this.gestionPlante = gestionPlante;
	}

	public GestionnaireDemande getGestionDemande() {
		return gestionDemande;
	}

	public void setGestionDemande(GestionnaireDemande gestionDemande) {
		this.gestionDemande = gestionDemande;
	}

	public GestionnaireAttribution getGestionAttribution() {
		return gestionAttribution;
	}

	public void setGestionAttribution(GestionnaireAttribution gestionAttribution) {
		this.gestionAttribution = gestionAttribution;
	}

	public GestionnaireCulture getGestionCulture() {
		return gestionCulture;
	}

	public void setGestionCulture(GestionnaireCulture gestionCulture) {
		this.gestionCulture = gestionCulture;
	}

	public Connexion getConnexion() {
		return cx;
	}
}
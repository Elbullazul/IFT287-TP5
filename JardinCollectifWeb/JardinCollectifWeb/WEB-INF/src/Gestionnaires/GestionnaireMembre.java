package Gestionnaires;

import java.sql.SQLException;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableMembre;
import Tuples.TupleMembre;

public class GestionnaireMembre {
	private Connexion cx;
	private TableMembre tMembre;
	private TableAttribution tatt;

	public GestionnaireMembre(TableMembre membre) {
		this.cx = membre.getConnexion();
		this.tMembre = membre;
	}

	public void InscrireMembre(String fName, String name, String pw, int nomemb, int isAdmin) throws Exception {
		try {
			// Vérifie si le membre existe déja
			if (tMembre.existe(nomemb))
				throw new IFT287Exception("Membre existe deja.");

			if (pw.length() < 8)
				throw new IFT287Exception("Mot de passe trop court. Longueur minimum de 8 caracteres");

			// Ajout du membre.
			tMembre.setMembre(nomemb, fName, name, pw, (isAdmin == 1));
		} catch (Exception e) {
			e.printStackTrace();
			cx.rollback();
			throw e;
		}
	}

	public void supprimerMembre(int nomemb) throws Exception {
		try {
			// Vérifie si le membre existe et son nombre de pret en cours
			if (!tMembre.existe(nomemb))
				throw new IFT287Exception("Membre inexistant");

			// Suppression du membre
			tMembre.deleteMembre(nomemb);
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public void Promouvoir(int nomemb) throws Exception {
		try {
			if (!tMembre.existe(nomemb)) {
				throw new IFT287Exception("Membre inexistant");
			}
			TupleMembre tupleMembre = tMembre.getMembre(nomemb);
			if (tupleMembre.getIsAdmin() == true) {
				throw new IFT287Exception("Le membre est deja un admin");
			}
			tMembre.updateMembre(nomemb, true);

		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public void AfficheMembres() throws Exception {
		try {
			if (tMembre.listpMembre().isEmpty()) {
				throw new IFT287Exception("Aucun membre trouvee");
			}
			for (TupleMembre t : tMembre.listpMembre()) {
				System.out.println(t.toString());
			}

		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public boolean informationsConnexionValide(int userId, String motDePasse) throws SQLException, IFT287Exception {
		// TODO Implement SHA-512 authentication
		try {
			if (!tMembre.existe(userId)) {
				throw new IFT287Exception("Membre inexistant");
			}
			return tMembre.authenticate(userId, motDePasse);
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

	public boolean utilisateurEstAdministrateur(int userId) throws Exception {
		try {
			if (!tMembre.existe(userId)) {
				throw new IFT287Exception("Membre inexistant");
			}
			TupleMembre tupleMembre = tMembre.getMembre(userId);

			return tupleMembre.getIsAdmin();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}

}

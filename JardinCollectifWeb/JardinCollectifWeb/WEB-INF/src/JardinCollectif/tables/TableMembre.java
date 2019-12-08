package JardinCollectif.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TupleMembre;

public class TableMembre {
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteMembre;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtAuth;

	public TableMembre(Connexion con) throws SQLException {
		cx = con;
		stmtExisteMembre = cx.getConnection().prepareStatement("SELECT * FROM Membres ORDER BY id");
		stmtExiste = cx.getConnection().prepareStatement("SELECT * FROM Membres WHERE id = ?");
		stmtUpdate = cx.getConnection().prepareStatement("UPDATE Membres SET isAdmin=? WHERE id=?");
		stmtInsert = cx.getConnection()
				.prepareStatement("INSERT INTO Membres (id, prenom, nom, password, isAdmin) VALUES(?, ?, ?, ?, ?)");
		stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Membres WHERE id=?");
		stmtAuth = cx.getConnection().prepareStatement("SELECT password FROM Membres WHERE id=?");
	}

	public Connexion getConnexion() {
		return cx;
	}

	public boolean existe(int nomemb) throws SQLException {
		stmtExiste.setInt(1, nomemb);
		ResultSet rset = stmtExiste.executeQuery();
		boolean MembreExiste = rset.next();
		rset.close();
		return MembreExiste;
	}
	
	public boolean authenticate(int nomemb, String hash) throws SQLException {
		stmtAuth.setInt(1, nomemb);
		ResultSet rset = stmtExiste.executeQuery();
		if (rset.next()) {
			return rset.getString(1) == hash;
		}
		return false;
	}

	public TupleMembre getMembre(int id) throws SQLException {
		stmtExiste.setInt(1, id);
		ResultSet rset = stmtExiste.executeQuery();
		if (rset.next()) {
			// id, prenom, nom, password, isAdmin
			TupleMembre tupleMembre = new TupleMembre();
			tupleMembre.setId(id);
			tupleMembre.setPrenom(rset.getString(2));
			tupleMembre.setNom(rset.getString(3));
			tupleMembre.setPassword(rset.getString(4));
			tupleMembre.setIsAdmin(rset.getBoolean(5));
			rset.close();
			return tupleMembre;
		} else {
			return null;
		}

	}

	public void setMembre(int id, String fname, String name, String mdp, Boolean adminR) throws SQLException {
		// id, prenom, nom, password, isAdmin
		stmtInsert.setInt(1, id);
		stmtInsert.setString(2, fname);
		stmtInsert.setString(3, name);
		stmtInsert.setString(4, mdp);
		stmtInsert.setBoolean(5, adminR);
		stmtInsert.executeUpdate();
	}

	public void deleteMembre(int id) throws SQLException {
		stmtDelete.setInt(1, id);
		stmtDelete.executeUpdate();
	}

	public List<TupleMembre> listpMembre() throws SQLException {
		ResultSet rset = stmtExisteMembre.executeQuery();
		List<TupleMembre> liste = new LinkedList<TupleMembre>();

		while (rset.next()) {
			// id, prenom, nom, password, isAdmin
			TupleMembre tupleMembre = new TupleMembre();

			tupleMembre.setId(rset.getInt(1));
			tupleMembre.setPrenom(rset.getString(2));
			tupleMembre.setNom(rset.getString(3));
			tupleMembre.setPassword(rset.getString(4));
			tupleMembre.setIsAdmin(rset.getBoolean(5));

		}
		rset.close();
		return liste;
	}

	// Implimenter le changement de isAdmin
	public int updateMembre(int id, boolean adsts) throws SQLException {
		// "UPDATE Membres SET isAdmin=? WHERE id=?
		stmtUpdate.setBoolean(1, adsts);
		stmtUpdate.setInt(2, id);

		return stmtUpdate.executeUpdate();
	}
}

package JardinCollectif.tables;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TupleDemande;

public class TableDemande {
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteDemande;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtUpdate;
	//private PreparedStatement stmtStatus;
	
	public TableDemande(Connexion con) throws SQLException {
		cx = con;
		stmtExisteDemande =cx.getConnection().prepareStatement("SELECT * FROM Lots ORDER BY nom");
        stmtExiste = cx.getConnection().prepareStatement("SELECT * FROM Demandes WHERE idMembre=? AND nomLot=?");
      //  stmtStatus= cx.getConnection().prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE status=?");
        stmtUpdate = cx.getConnection().prepareStatement("UPDATE Demandes SET status=? WHERE idMembre=? AND nomLot=?");
        stmtInsert = cx.getConnection()
                .prepareStatement("INSERT INTO Demandes (idMembre, nomLot, status) VALUES(?, ?, ?)");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
        
	}
	public Connexion getConnexion()
	    {
	        return cx;
	    }
	public boolean existe(int id,String name) throws SQLException{
		stmtExiste.setInt(1, id);
		stmtExiste.setString(2, name);
        ResultSet rset = stmtExiste.executeQuery();
        boolean DemandeExiste = rset.next();
        rset.close();
        return DemandeExiste;
		}
	public TupleDemande getDemande(int id,String nomlot) throws SQLException {
		//(idMembre, nomLot, status)
	      stmtExiste.setInt(1, id);
	      stmtExiste.setString(2,nomlot);
	        ResultSet rset = stmtExiste.executeQuery();
	        if (rset.next())
	        {
	            TupleDemande tupleDemande = new TupleDemande();
	            tupleDemande.setIdMembre(id);
	            tupleDemande.setNomLot(nomlot);
	            tupleDemande.setStatus(rset.getInt(3));
	            rset.close();
	            return tupleDemande;
	        }
	        else
	            return null;
		
	}
	
	public  void setDemande(int id, String nom, int sta)throws SQLException {
		//(idMembre, nomLot, status)
		
		stmtInsert.setInt(1, id);
		stmtInsert.setString(2, nom);
		stmtInsert.setInt(3, sta);
        stmtInsert.executeUpdate();
		
	}   
	
	public void deleteDemande(int id,String name,int stat )throws SQLException{
		//"DELETE FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?"
		stmtDelete.setInt(1, id);
		stmtDelete.setString(2, name);
		stmtDelete.setInt(3, stat);
        stmtDelete.executeUpdate();
	}
	
	public List<TupleDemande> listDemande() throws SQLException{
		ResultSet rset = stmtExisteDemande.executeQuery();
		  List<TupleDemande> liste =  new LinkedList<TupleDemande>();
		  
		  while(rset.next())
		  {
			  TupleDemande tupleDemande = new TupleDemande();
	            tupleDemande.setIdMembre(rset.getInt(1));
	            tupleDemande.setNomLot(rset.getString(2));
	            tupleDemande.setStatus(rset.getInt(3));
	     }
	        rset.close(); 
	        return liste;
}
	
	public int  updateDemande(int id, String nameL,int status)throws SQLException {
		//"UPDATE Demandes SET status=? WHERE idMembre=? AND nomLot=?"
		stmtUpdate.setInt(1, status);
		stmtUpdate.setInt(2,id);
		stmtUpdate.setString(3,nameL);
		return stmtUpdate.executeUpdate();
	}
}
	   
	
	/*// SQL
	public static ArrayList<TableDemande> fetchAll() {
		ArrayList<TableDemande> tl = new ArrayList<TableDemande>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE status=?");
			ps.setInt(1, STATUS_PENDING);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableDemande o = new TableDemande();

				o.setIdMembre(rs.getInt(1));
				o.setNomLot(rs.getString(2));
				o.setStatus(rs.getInt(3));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	@Override
	public Boolean insert() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("INSERT INTO Demandes (idMembre, nomLot, status) VALUES(?, ?, ?)");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3,  this.status);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Creation failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean update() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("UPDATE Demandes SET status=? WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.status);
			ps.setInt(2, this.idMembre);
			ps.setString(3, this.nomLot);
			ps.setInt(4,  STATUS_PENDING);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Update failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3, this.status);
			
			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE nomLot=?");
			ps.setString(1, this.nomLot);
			
			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE idMembre=?");
			ps.setInt(1, this.idMembre);
			
			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean fetch() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3, STATUS_PENDING);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status= rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Demandes WHERE idMembre=?");
			ps.setInt(1, this.idMembre);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status= rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Demandes WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status= rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}*/


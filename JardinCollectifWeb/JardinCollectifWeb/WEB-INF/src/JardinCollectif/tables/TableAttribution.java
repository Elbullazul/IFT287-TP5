package JardinCollectif.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TupleAttribution;

public class TableAttribution {
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteAttribution;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
	//private PreparedStatement stmtUpdate;
	
	public TableAttribution(Connexion con) throws SQLException {
		cx = con;
		stmtExisteAttribution =cx.getConnection().prepareStatement("SELECT * FROM Attributions");
        stmtExiste = cx.getConnection()
                .prepareStatement("SELECT * FROM Attributions WHERE nomLot=? AND idMembre=?");
        //stmtUpdate = cx.getConnection().prepareStatement("UPDATE Membres SET prenom=?, nom=?, password=?, isAdmin=? WHERE id=?");
        stmtInsert = cx.getConnection()
                .prepareStatement("INSERT INTO Attributions (idMembre, nomLot) VALUES(?, ?)");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Attributions WHERE nomLot=? AND idMembre=?");
        
	}
	public Connexion getConnexion()
	    {
	        return cx;
	    }
	public boolean existe(String name, int id) throws SQLException{
		stmtExiste.setString(1, name);
		stmtExiste.setInt(2, id);
        ResultSet rset = stmtExiste.executeQuery();
        boolean AttributionExiste = rset.next();
        rset.close();
        return AttributionExiste;
        }
	
	public TupleAttribution getAttribution(String nomlot) throws SQLException {
		 stmtExiste.setString(1,nomlot);
	        ResultSet rset = stmtExiste.executeQuery();
	        if (rset.next())
	        {
	        	//(idMembre, nomLot)
	        	TupleAttribution tupleAtt = new TupleAttribution();
	        
	        	tupleAtt.setIdMembre(rset.getInt(1));
	        	tupleAtt.setNomLot(nomlot);
	            rset.close();
	            return tupleAtt;
	        }
	        else
	            return null;
	}
	
	public  void setAttribution(int id, String nameL)throws SQLException {
		// (idMembre, nomLot)
		   stmtInsert.setInt(1,id);
		   stmtInsert.setString(2, nameL);
	        stmtInsert.executeUpdate();
	
	} 
	
	public void deleteAttribution(String name, int id)throws SQLException{
		stmtDelete.setString(1, name);
		stmtDelete.setInt (2,id);
        stmtDelete.executeUpdate();

	}
	
	public List<TupleAttribution> listAttribution() throws SQLException{
		
		ResultSet rset = stmtExisteAttribution.executeQuery();
		  List<TupleAttribution> liste =  new LinkedList<TupleAttribution>();
		  while(rset.next())
		  {
				//(idMembre, nomLot)
	        	TupleAttribution tupleAtt = new TupleAttribution();
	        	tupleAtt.setIdMembre(rset.getInt(1));
	        	tupleAtt.setNomLot(rset.getString(2));
	        	
	        	
	     }
	        rset.close(); 
	        return liste;
	        }
	public void  updateAttribution()throws SQLException {}
	   
	/*// SQL
	public static ArrayList<TableAttribution> fetchAll() {
		ArrayList<TableAttribution> tl = new ArrayList<TableAttribution>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableAttribution o = new TableAttribution();

				o.setIdMembre(rs.getInt(1));
				o.setNomLot(rs.getString(2));

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

			ps = cnn.prepareStatement("INSERT INTO Attributions (idMembre, nomLot) VALUES(?, ?)");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);

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
		// TODO: pas implémenté car pas utilisé dans le cadre du devoir (renommer lot)

		return false;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE nomLot=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setInt(2, this.idMembre);

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

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE idMembre=?");
			ps.setInt(1, this.idMembre);

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

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE nomLot=?");
			ps.setString(1, this.nomLot);

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

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE nomLot=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setInt(2, this.idMembre);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}
			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}
			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public ArrayList<TableLot> getLotsMembre() {
		ArrayList<TableLot> tl = new ArrayList<TableLot>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE idMembre=?");
			ps.setInt(1, this.idMembre);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableLot o = new TableLot();

				o.setNom(rs.getString(2));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public ArrayList<TableMembre> getMembresLot() {
		ArrayList<TableMembre> tl = new ArrayList<TableMembre>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableMembre o = new TableMembre();

				o.setId(rs.getInt(1));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public Boolean notLast() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE idMembre!=? AND nomLot=?");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}
			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}*/
}

package JardinCollectif.tables;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TupleLot;


public class TableLot {
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteLot;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
	//private PreparedStatement stmtUpdate;
	
	public TableLot(Connexion con) throws SQLException {
		cx = con;
		stmtExisteLot =cx.getConnection().prepareStatement("SELECT * FROM Lots ORDER BY nom");
        stmtExiste = cx.getConnection()
                .prepareStatement("SELECT * FROM Lots WHERE nom=?");
      //  stmtUpdate = cx.getConnection().prepareStatement("UPDATE Lots SET max_collab=?) WHERE nom=?");
        stmtInsert = cx.getConnection()
                .prepareStatement("INSERT INTO Lots (nom, max_collab) VALUES(?, ?)");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Lots WHERE nom=?");
        
	}
	public Connexion getConnexion()
	    {
	        return cx;
	    }
	public boolean existe(String name) throws SQLException{
		stmtExiste.setString(1, name);
        ResultSet rset = stmtExiste.executeQuery();
        boolean lotExiste = rset.next();
        rset.close();
        return lotExiste;
        }
	
	public TupleLot getLot(String name) throws SQLException {
		stmtExiste.setString(1, name);
    ResultSet rset = stmtExiste.executeQuery();
    if (rset.next())
    {
        TupleLot tupleLot = new TupleLot();
        tupleLot.setNom(name);
        tupleLot.setMax_collab(rset.getInt(2));
        rset.close();
        return tupleLot;
    }
    else
        return null;
		
	}
	
	public  void setLot(String name, int nbcol )throws SQLException {
		//(nom, max_collab)
		stmtInsert.setString(1,name);
		stmtInsert.setInt(2,nbcol);
		 stmtInsert.executeUpdate();
	}   
	
	public void deleteLot(String name)throws SQLException{
		stmtDelete.setString(1, name);
        stmtDelete.executeUpdate();
		
		
	}
	
	public List<TupleLot> listLot() throws SQLException{
		ResultSet rset = stmtExisteLot.executeQuery();
		  List<TupleLot> liste =  new LinkedList<TupleLot>();
		  
		  while(rset.next())
		  {
			  TupleLot tupleLot = new TupleLot();
		        tupleLot.setNom(rset.getString(1));
		        tupleLot.setMax_collab(rset.getInt(2));
	        
	     }
	        rset.close(); 
	        return liste;
		
	}
	// Not used
	public void  updateLot()throws SQLException {}
	   
	
	/*
	// SQL

	public static ArrayList<TableLot> fetchAll() {
		ArrayList<TableLot> tl = new ArrayList<TableLot>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Lots ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableLot o = new TableLot();

				o.setNom(rs.getString(1));
				o.setMax_collab(rs.getInt(2));

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

			ps = cnn.prepareStatement("INSERT INTO Lots (nom, max_collab) VALUES(?, ?)");
			ps.setString(1, this.nom);
			ps.setInt(2, this.max_collab);

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

			ps = cnn.prepareStatement("UPDATE Lots SET max_collab=?) WHERE nom=?");
			ps.setInt(1, this.max_collab);
			ps.setString(2, this.nom);

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

			ps = cnn.prepareStatement("DELETE FROM Lots WHERE nom=?");
			ps.setString(1, this.nom);
			
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

			ps = cnn.prepareStatement("SELECT * FROM Lots WHERE nom=?");
			ps.setString(1, this.nom);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				max_collab = rs.getInt(2);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}*/
}

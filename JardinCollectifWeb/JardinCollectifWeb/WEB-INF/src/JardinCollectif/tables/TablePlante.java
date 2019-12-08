package JardinCollectif.tables;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TuplePlante;

public class TablePlante {
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExistePlante;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
//	private PreparedStatement stmtUpdate;
	
	public TablePlante(Connexion con)throws SQLException {
		cx = con;
		stmtExistePlante =cx.getConnection().prepareStatement("SELECT * FROM Plantes ORDER BY nom");
	    stmtExiste = cx.getConnection().prepareStatement("SELECT * FROM Plantes WHERE nom=?");
  //      stmtUpdate = cx.getConnection().prepareStatement("UPDATE Plantes SET duree=?) WHERE nom=?");
        stmtInsert = cx.getConnection()
                .prepareStatement("INSERT INTO Plantes (nom, duree) VALUES(?, ?)");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Plantes WHERE nom=?");
		
	}
	public Connexion getConnexion()
	    {
	        return cx;
	    }
	public boolean existe(String name) throws SQLException{
	
		 stmtExiste.setString(1, name);
	        ResultSet rset = stmtExiste.executeQuery();
	        boolean PlanteExiste = rset.next();
	        rset.close();
	        return PlanteExiste;
		}
	
	public TuplePlante getPlante(String name) throws SQLException {
		  stmtExiste.setString(1, name);
	        ResultSet rset = stmtExiste.executeQuery();
	        if (rset.next())
	        {
	            TuplePlante tuplePlante = new TuplePlante();
	            tuplePlante.setNom(name);
	            tuplePlante.setDuree(rset.getInt(2));
	         
	            rset.close();
	            return tuplePlante;
	        }
	        else
	        
	            return null;
	        
		}
	
	public  void setPlante(String name, int tempG)throws SQLException {
		//					(nom, duree)
			stmtInsert.setInt(2, tempG);
	        stmtInsert.setString(1, name);
	        stmtInsert.executeUpdate();
		
	}   
	public void deletePlante(String name)throws SQLException{
		stmtDelete.setString(1, name);
        stmtDelete.executeUpdate();	
	}
	
	public List<TuplePlante> listplante() throws SQLException{
		  ResultSet rset = stmtExistePlante.executeQuery();
		  List<TuplePlante> liste =  new LinkedList<TuplePlante>();
		  
		  while(rset.next())
		  {
	            TuplePlante tuplePlante = new TuplePlante();
	            tuplePlante.setNom(rset.getString(1));
	            tuplePlante.setDuree(rset.getInt(2));
	     }
	        rset.close(); 
	        return liste;
	}
	//Not Used dans le cadre du tp
	public void  updatePlante()throws SQLException {}
	   
/*	   
	// SQL
	public static ArrayList<TablePlante> fetchAll() {
		ArrayList<TablePlante> tl = new ArrayList<TablePlante>();

		try {
		

			ps = cnn.prepareStatement("SELECT * FROM Plantes ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TablePlante o = new TablePlante();

				o.setNom(rs.getString(1));
				o.setDuree(rs.getInt(2));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return tl;
	}
	
	@Override
	public Boolean insert() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("INSERT INTO Plantes (nom, duree) VALUES(?, ?)");
			ps.setString(1, this.nom);
			ps.setInt(2, this.duree);

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

			ps = cnn.prepareStatement("UPDATE Plantes SET duree=?) WHERE nom=?");
			ps.setInt(1, this.duree);
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

			ps = cnn.prepareStatement("DELETE FROM Plantes WHERE nom=?");
			ps.setString(1, this.nom);
			
			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Boolean fetch() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Plantes WHERE nom=?");
			ps.setString(1, this.nom);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				duree = rs.getInt(2);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}*/
}

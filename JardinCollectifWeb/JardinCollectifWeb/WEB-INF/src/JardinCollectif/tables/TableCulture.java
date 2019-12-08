package JardinCollectif.tables;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import JardinCollectif.Connexion;
import Tuples.TupleCulture;

public class TableCulture {
	
	private Connexion cx;
	private PreparedStatement stmtExiste;
	private PreparedStatement stmtExisteCulture;
	private PreparedStatement stmtExisteLot;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtUpdate;
	
	public TableCulture(Connexion con) throws SQLException {
		cx = con;
		stmtExisteCulture =cx.getConnection().prepareStatement("SELECT * FROM Cultures ORDER BY nomLot, nomPlante");
		stmtExisteLot =cx.getConnection().prepareStatement("SELECT * FROM Cultures WHERE nomLot=? ORDER BY nomLot, nomPlante");
        stmtExiste = cx.getConnection()
                .prepareStatement("SELECT * FROM Cultures WHERE nomLot=? AND nomPlante= ? ORDER BY nomLot, nomPlante");
        stmtUpdate = cx.getConnection().prepareStatement("UPDATE Cultures SET quantitee=?) WHERE nomPlante=? AND nomLot=?");
        stmtInsert = cx.getConnection()
                .prepareStatement("INSERT INTO Cultures (nomPlante, nomLot, idMembre, quantitee, plantee) VALUES(?, ?, ?, ?, ?)");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee=?");
      
	}
	public Connexion getConnexion()
	    {
	        return cx;
	    }
	public boolean existe(String name) throws SQLException{
		stmtExiste.setString(1, name);
        ResultSet rset = stmtExiste.executeQuery();
        boolean CultureExiste = rset.next();
        rset.close();
        return CultureExiste;
        }
	
	public TupleCulture getCulture(String nomplante,String nomlot) throws SQLException {
		
		   stmtExiste.setString(2, nomplante);
		   stmtExiste.setString(1, nomlot);
	        ResultSet rset = stmtExiste.executeQuery();
	        if (rset.next())
	        {
	        	//(nomPlante, nomLot, idMembre, quantitee, plantee)
	        	TupleCulture tupleCulture = new TupleCulture();
	        	tupleCulture.setNomPlante(nomplante);
	        	tupleCulture.setNomLot(nomlot);
	        	tupleCulture.setIdMembre(rset.getInt(3));
	        	tupleCulture.setNbExemplaires(rset.getInt(4));
	        	tupleCulture.setPlantee(rset.getDate(5));
	            rset.close();
	            return tupleCulture;
	        }
	        else
	            return null;	
	}
	
	public  void setCulture(String nameP,String nameL, int id, int qt,Date date )throws SQLException {
		//(nomPlante, nomLot, idMembre, quantitee, plantee)
        stmtInsert.setString(1, nameP);
        stmtInsert.setString(2, nameL);
        stmtInsert.setInt(3, id);
        stmtInsert.setInt(4, qt);
        stmtInsert.setDate(5, date);
        stmtInsert.executeUpdate();
	}   
	
	public void deleteCulture(String nameL,String nameP,int id, Date dateP)throws SQLException{
		//DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee=?"
		  stmtDelete.setString(1,nameL);
		  stmtDelete.setString(2, nameP);
		  stmtDelete.setInt(3,id);
		  stmtDelete.setDate(4, dateP);
		  stmtDelete.executeUpdate();
		
	}
	
	public List<TupleCulture> listplanteLot (String nomlot) throws SQLException{
		stmtExisteLot.setString(1,nomlot );
		ResultSet rset = stmtExisteLot.executeQuery();
		  List<TupleCulture> liste =  new LinkedList<TupleCulture>();
		  while(rset.next())
		  {
			 	//(nomPlante, nomLot, idMembre, quantitee, plantee)
	        	TupleCulture tupleCulture = new TupleCulture();
	        	tupleCulture.setNomPlante(rset.getString(1));
	        	tupleCulture.setNomLot(rset.getString(2));
	        	tupleCulture.setIdMembre(rset.getInt(3));
	        	tupleCulture.setNbExemplaires(rset.getInt(4));
	        	tupleCulture.setPlantee(rset.getDate(5));
	     }
	        rset.close(); 
	        return liste;
		
	}
	
	public List<TupleCulture> listplante() throws SQLException{
		ResultSet rset = stmtExisteCulture.executeQuery();
		  List<TupleCulture> liste =  new LinkedList<TupleCulture>();
		  while(rset.next())
		  {
			 	//(nomPlante, nomLot, idMembre, quantitee, plantee)
	        	TupleCulture tupleCulture = new TupleCulture();
	        	tupleCulture.setNomPlante(rset.getString(1));
	        	tupleCulture.setNomLot(rset.getString(2));
	        	tupleCulture.setIdMembre(rset.getInt(3));
	        	tupleCulture.setNbExemplaires(rset.getInt(4));
	        	tupleCulture.setPlantee(rset.getDate(5));
	     }
	        rset.close(); 
	        return liste;
		
	}
	
	public int  updateCulture(int nbplantee,String nomPlante,String nomLot)throws SQLException {
		//"UPDATE Cultures SET quantitee=?) WHERE nomPlante=? AND nomLot=?"
		stmtUpdate.setInt(1, nbplantee);
		stmtUpdate.setString(2,nomPlante);
		stmtUpdate.setString(3,nomLot);
		return stmtUpdate.executeUpdate();
		
	}
	  
	
/*	// SQL
	public static ArrayList<TableCulture> fetchAll() {
		ArrayList<TableCulture> tl = new ArrayList<TableCulture>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures ORDER BY nomLot, nomPlante");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableCulture o = new TableCulture();
				
				o.setNomPlante(rs.getString(1));
				o.setNomLot(rs.getString(2));
				o.setIdMembre(rs.getInt(3));
				o.setNbExemplaires(rs.getInt(4));
				o.setPlantee(rs.getDate(5));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public ArrayList<TableCulture> fetchAllLot() {
		ArrayList<TableCulture> tl = new ArrayList<TableCulture>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=? ORDER BY nomLot, nomPlante");
			ps.setString(1, this.nomLot);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableCulture o = new TableCulture();
				
				o.setNomPlante(rs.getString(1));
				o.setNomLot(rs.getString(2));
				o.setIdMembre(rs.getInt(3));
				o.setNbExemplaires(rs.getInt(4));
				o.setPlantee(rs.getDate(5));

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

			ps = cnn.prepareStatement("INSERT INTO Cultures (nomPlante, nomLot, idMembre, quantitee, plantee) VALUES(?, ?, ?, ?, ?)");
			ps.setString(1, this.nomPlante);
			ps.setString(2, this.nomLot);
			ps.setInt(3, this.idMembre);
			ps.setInt(4,  this.nbExemplaires);
			ps.setDate(5, this.plantee);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Creation failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Boolean update() {
		// TODO: pas utilisé dans le devoir

		return false;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee=?");
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);
			ps.setDate(4, this.plantee);

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

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomPlante=?");
			ps.setString(1, this.nomPlante);

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

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE idMembre=?");
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

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				nbExemplaires = rs.getInt(4);
				plantee = rs.getDate(5);

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

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=?");
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
	
	public Boolean existsPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomPlante=?");
			ps.setString(1, this.nomPlante);
			
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
	
	public Boolean existsMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE idMembre=?");
			ps.setInt(1, this.idMembre);
			
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
	
	public Boolean recolterPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();
			
			// charger la durée
			TablePlante tp = new TablePlante(this.nomPlante);
			tp.fetch();
			
			// Aditionner les jours de maturation
			Calendar c = Calendar.getInstance();
	        c.setTime(this.plantee);
	        c.add(Calendar.DATE, tp.getDuree());

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee <= ?");
			
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);
			ps.setDate(4, new Date(c.getTimeInMillis()));

			if (ps.executeUpdate() == 0)
				throw new SQLException("Reaping failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}*/
}

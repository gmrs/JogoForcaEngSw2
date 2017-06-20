package fontes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;
import java.util.Random;

public class DatabasePalavra
{   
  private static String sortearTipo()
  {
      Random rand = new Random();
      int t = rand.nextInt(10) + 1;
      if (t>5)
      {return "P";}
      else {return "F";}
  }
  
  public static void addNew(String tipo, String palavrafrase, String categoria) throws SQLException
  {
        Database db = new Database();
        String sql = "insert into palavra (palavra, tipo, categoria, letras) ";
        sql += "values ('" + palavrafrase + "', ";
        sql += "'" + tipo + "', ";
        sql += "'" + categoria + "', ";
        sql += (maxId(tipo)+1) + ")";
        
        try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            stmt.executeUpdate(sql);
            db.Desconectar();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
  }
  
  public static Palavra SortearAmbos() throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        Random rand = new Random();
        String tipo = sortearTipo();
        String sql = "SELECT palavra, tipo, categoria, letras FROM palavra where letras = " + (rand.nextInt(maxId(tipo)-1) + 1);
        sql += " and tipo='" + tipo + "'";
        sql += " LIMIT 1";
        
        try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            crs.populate(rs);
            db.Desconectar();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        crs.next();
        Palavra p = new Palavra(crs.getString("palavra"), crs.getString("categoria"));
        return p;
    }
  
    private static int maxId(String tp) throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        Random rand = new Random();
        String sql = "SELECT max(letras) maximo from palavra where tipo = '" + tp + "'";
         try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            crs.populate(rs);
            db.Desconectar();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        crs.next();
        return crs.getInt("maximo");
    }
  
    public static Palavra Sortear(boolean frases) throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        Random rand = new Random();
        String tipo = "P";
        if (frases)
        {
            tipo = "F";
        }
        String sql = "SELECT palavra, tipo, categoria, letras FROM palavra where letras = " + (rand.nextInt(maxId(tipo)-1) + 1);
        if (frases)
        {
            sql += " and tipo = 'F' ";
        }
        else
        {
            sql += " and tipo = 'P' ";
        }
        sql += " LIMIT 1";
        
        try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            crs.populate(rs);
            db.Desconectar();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        crs.next();
        Palavra p = new Palavra(crs.getString("palavra"), crs.getString("categoria"));
        return p;
    }
}

package fontes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;
import java.util.Date;

public class DatabaseRanking
{
    public static CachedRowSet GetRanking() throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        String sql = "SELECT nome, datahora, pontuacao FROM ranking ORDER BY pontuacao DESC";
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
        finally
        {
            return crs;
        }
    }
    
    public static void SalvarRanking(String nome, int pontuacao) throws SQLException
    {
        Database db = new Database();
        String sql = "insert into ranking (nome, datahora, pontuacao) values ('" + nome.replaceAll("'", "") + "',datetime('now','localtime'),+" + pontuacao + ")";
        try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            return;
        }
    }
    
    
    
    
      public static String GetUltimaPartida() throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        String sql = "SELECT max(datahora) u FROM ranking";
       
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
        return crs.getString("u");
    }
}

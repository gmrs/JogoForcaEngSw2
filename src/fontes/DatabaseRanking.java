package fontes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

public class DatabaseRanking
{
    public static void main(String[] args) throws SQLException
    {
        CachedRowSet teste = GetRanking();
        while (teste.next())
        {
           System.out.println(teste.getString("nome")); 
        }
    }
    
    public static CachedRowSet GetRanking() throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        String sql = "SELECT nome, datahora, pontuacao FROM ranking";
        try
        {
            Connection conn = db.c;
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            crs.populate(rs);
            db.Desconectar();
            //while (crs.next())
            //{
            //   System.out.println(crs.getString("nome")); 
            //}
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
}

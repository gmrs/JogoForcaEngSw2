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
    public static Palavra Sortear(boolean frases) throws SQLException
    {
        Database db = new Database();
        CachedRowSet crs = new CachedRowSetImpl();
        Random rand = new Random();
        String sql = "SELECT palavra, tipo, categoria, letras FROM palavra where letras = " + (rand.nextInt(23) + 1);
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

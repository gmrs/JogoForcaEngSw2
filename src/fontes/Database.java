/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Or4cl3
 */
public class Database {
    public Connection c;
    
    public Boolean GetStatusConexao() throws SQLException
    {
        return this.c.isValid(5);
    }
    
    public void Desconectar()
    {
        try
        {
            if (this.c != null) {this.c.close();}
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    Database()
    {
        try
        {
            String url = "jdbc:sqlite:./db/forca.db";
            this.c = DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
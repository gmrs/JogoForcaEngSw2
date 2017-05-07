/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import java.sql.SQLException;

/**
 *
 * @author Or4cl3
 */
public class DatabasePalavra
{
    public static void main(String[] args) throws SQLException
    {
        Database db = new Database();
        System.out.println(db.GetStatusConexao().toString());
        db.Desconectar();
    }
}

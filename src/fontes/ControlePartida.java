/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class ControlePartida {
    int pontuacao;
    
    public boolean novoJogo(){
        pontuacao = 0;
        // TEMPO ZERADO
        // POWERUPS ZERADOS
        // NOVA PALAVRA OU FRASE
        return true;
    }
    
    public int atualizaPontuacao(int pontos){
        pontuacao += pontos;
        return (pontuacao);
    }
    
    // CRIAR METODO DE TEMPO
    // CRIAR METODO DE POWERUP
    
    // NLETRA RECEBE NA CLASSE TELAJOGO A QUANTIDADE DE CARACTERES DA PALAVRA/FRASE
    // DECREMENTA NLETRA CONFORME FOR ACERTANDO E DECREMENTA NTENTATIVAS CONFORME FOR ERRANDO
    public boolean fimRodada(int nTentativas, int nLetras){
        if((nTentativas >= 0) && (nLetras == 0)) return true;
        else return false;
    }
    
    public boolean fimJogo(int nTentativas, int nLetras, int totalLetras, String jog){
        if((nTentativas < 0) && (nLetras > 0)){
            // VERIFICAR SE POSSUI POWERUP, SE SIM, FAZER MÉTODO PARA UTILIZAR.
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            pontuacao = pontuacao + ((totalLetras-nLetras)*20);
            JOptionPane.showMessageDialog(null, "Que pena, voce perdeu!\n\n Sua pontuação foi: " + pontuacao + ".");
            try {
                // GRAVAR NO BD RANKING
                DatabaseRanking.SalvarRanking(jog, pontuacao);
            } catch (SQLException ex) {
                Logger.getLogger(ControlePartida.class.getName()).log(Level.SEVERE, null, ex);
            }
            TelaInicial inicio = new TelaInicial();
            inicio.setVisible(true);
                    
            return true;
        }
        else return false;
    }
   


   
   
    
}

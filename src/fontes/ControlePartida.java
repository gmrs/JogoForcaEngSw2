/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public boolean fimJogo(int nTentativas, int nLetras){
        if((nTentativas < 0) && (nLetras > 0)){
            // VERIFICAR SE POSSUI POWERUP, SE SIM, FAZER MÉTODO PARA UTILIZAR.
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Que pena, voce perdeu!\n\n Sua pontuação foi: " + pontuacao + ".");
            // GRAVAR NO BD RANKING
            TelaInicial inicio = new TelaInicial();
            inicio.setVisible(true);
            return true;
        }
        else return false;
    }
   
    /*
    // RETORNA 0 SE PROBLEMA
    // RETORNA 1 SE TEM APENAS POWERUPEXIBIRLETRA
    // RETORNA 2 SE TEM APENAS POWERUPULTIMACHANCE
    // RETORNA 3 SE TEM APENAS POWERUPVIDA
    // RETORNA 4 SE TEM POWERUPEXIBIRLETRA E POWERUPULTIMACHANCE
    // RETORNA 5 SE TEM POWERUPEXIBIRLETRA E POWERUPVIDA
    // RETORNA 6 SE TEM POWERUPULTIMACHANCE E POWERUPVIDA
    */
    public int temPowerup(boolean powerupExibirLetra, boolean powerupUltimaChance, boolean powerupVida){
        if(powerupExibirLetra == true) return 1;
        else if(powerupUltimaChance == true) return 2;
        else if(powerupVida == true) return 3;
        else if(powerupExibirLetra == true && powerupUltimaChance == true) return 4;
        else if(powerupExibirLetra == true && powerupVida == true) return 5;
        else if(powerupUltimaChance && powerupVida == true) return 6;
        else return 0;
    }

   
   
    
}

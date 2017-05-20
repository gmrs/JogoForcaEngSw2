/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class Powerup {
    
    private boolean exibeLetra = false;
    private boolean ultimaChance = false;
    private boolean vidaExtra = false;
   
    
    
    public void novaRodadaPWUP(){
        exibeLetra = false;
        ultimaChance = false;
        vidaExtra = false;
    }
    
    public String exibeLetra(String palavra, ArrayList tent){
        String replace = null;
        char letra;
        Random rand = new Random();
        if(!tent.isEmpty()){
            for(int i=1; i<=palavra.length(); i++){
                 if((tent.contains(palavra.substring(i-1, i)))){
                    replace = palavra.replace(palavra.substring(i-1, i), "");
                    System.out.println(replace);
                    palavra = replace;
                 }
            }
            letra = replace.charAt(rand.nextInt(replace.length()));
            replace = String.valueOf(letra);
        }
        else {
            letra = palavra.charAt(rand.nextInt(palavra.length()));
            replace = String.valueOf(letra);
        }
        return replace;
    }
    
    public int ultimaChance(int ntent){
        ntent++;
        JOptionPane.showMessageDialog(null, "Você esta utilizando seu Powerup ULTIMA CHANCE. \nVocê tem mais uma tentativa, aproveite!");
        return ntent;
    }
    

    public boolean isExibeLetra() {
        return exibeLetra;
    }

    public void setExibeLetra(boolean exibeLetra) {
        this.exibeLetra = exibeLetra;
    }

    public boolean isUltimaChance() {
        return ultimaChance;
    }

    public void setUltimaChance(boolean ultimaChance) {
        this.ultimaChance = ultimaChance;
    }

    public boolean isVidaExtra() {
        return vidaExtra;
    }

    public void setVidaExtra(boolean vidaExtra) {
        this.vidaExtra = vidaExtra;
    }
    
        /*
    // RETORNA 0 SE NÃO TEM
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

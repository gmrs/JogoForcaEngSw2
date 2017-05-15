/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

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

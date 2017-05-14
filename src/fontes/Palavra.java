/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import java.text.Normalizer;
import java.util.ArrayList;

/**
 *
 * @author Or4cl3
 */
public class Palavra
{
    private String palavra;
    private String categoria;
    
    public String getPalavra() {
        return palavra;
        //return Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public String getPalavraCifrada() {
        return palavra.replaceAll("[A-Z]", "_ ");
        //return palavra;
    }
        
    public void setPalavra(String palavra) {
        this.palavra = palavra.toUpperCase();
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getCaracteresSemEspacos()
    {
        return this.palavra.replaceAll(" ", "").length();
    }
    
    public int getCaracteresComEspacos()
    {
        return this.palavra.length();
    }
        
    Palavra(String p_palavra_frase, String p_categoria)
    {
        this.palavra = p_palavra_frase.toUpperCase();
        this.categoria = p_categoria.toUpperCase();
    }
    
    public boolean Contem(String letra)
    {
        return palavra.contains(letra);
    }
    
    public String getLetraDescobreta(ArrayList<String> letra){
        String texto_temp="";
        for (int n=1; n<=palavra.length(); n++){       //pega o tamanho da palavra para gerar os traços
                if((letra.contains(palavra.substring(n-1, n))))
                {
                texto_temp = texto_temp + palavra.substring(n-1, n)+" "; //revela a letra chutada
                }else if(palavra.substring(n-1, n).contains(" ")) texto_temp=texto_temp+" ";
                else
                {
                    texto_temp = texto_temp + "_ ";
                    //resta_um = true;
                }
                //texto_temp = texto_temp + " ";
            }
        return texto_temp;
    }
    
    public int getNumLetraDescoberta(String letra){
        int cont=0;
        for (int n=1; n<=palavra.length(); n++){       //pega o tamanho da palavra para gerar os traços
                if((letra.contains(palavra.substring(n-1, n))))
                {
                    cont++;
                }
        }
        return cont;
    }
}

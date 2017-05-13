/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

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
    }

    public String getPalavraCifrada() {
        return palavra.replaceAll("A", "_");
    }
        
    public void setPalavra(String palavra) {
        this.palavra = palavra;
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
        this.palavra = p_palavra_frase;
        this.categoria = p_categoria;
    }
    
    public boolean Contem(String letra)
    {
        return palavra.contains(palavra);
    }
}

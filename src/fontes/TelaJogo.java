/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import java.awt.Color;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class TelaJogo extends javax.swing.JFrame {
    
    private static Timer cronometro = new Timer();
    private int contador = 0;
    private Boolean intercalar = false;
    
    public TimerTask run()
    {
        return new TimerTask()
        {
            public void run()
            {
                contador=contador+1;
                //System.out.println("TEMPO:" + contador);
                jLabelTempo2.setText(""+contador);
                if(contador <= 20) {
                    jLabelTempo2.setFont(new Font("Times New Roman", Font.BOLD, 12));
                    jLabelTempo2.setForeground(Color.BLUE);
                }
                else jLabelTempo2.setForeground(Color.BLACK);
                if (contador==0)
                {
                    terminouTempo();
                    cronometro.cancel();
                }
            }
        };
    }
    
    public static void terminouTempo()
    {
        System.out.println("Tempo excedido");
    }
    
    

    private String letra;
    private String nomeJogador;
    private Palavra palavraSorteada;
    private int formaJogo;
    private int nTentativas = 6;
    private int nLetras;
    private int pontos;
    ArrayList tentativas = new ArrayList();
    
    
    
    /**
     * Creates new form TelaJogo
     */
    public TelaJogo() {
        initComponents();
    }
    
    public TelaJogo(String nome, int forma) throws SQLException {
        nomeJogador = nome;
        formaJogo = forma;
        initComponents();
        novaRodada();
    }



    /**
    * @return the nTentativas
    */
    public int getnTentativas() {
        return nTentativas;
    }

    /**
     * @param nTentativas the nTentativas to set
     */
    public void setnTentativas(int nTentativas) {
        this.nTentativas = nTentativas;
    }
    
    private void novaRodada() throws SQLException{
        if (this.formaJogo==1)
        {
            this.palavraSorteada = DatabasePalavra.Sortear(false);
        }
        else if (this.formaJogo==2)
        {
            this.palavraSorteada = DatabasePalavra.Sortear(true);
        }
        else
        {
            this.palavraSorteada = DatabasePalavra.Sortear(intercalar);
            intercalar = !intercalar;
        }
        // NUMERO DE LETRAS DA PALAVRA SORTEADA (SEM ESPAÇO)
        nLetras = palavraSorteada.getCaracteresSemEspacos();
        // LIMPA A LISTA DE LETRAS SELECIONADAS NA RODADA
        tentativas.clear();
        // EXIBE A PALAVRA CIFRADA (COM OS TRAÇOS)
        jLabelPalavraFrase.setText(this.palavraSorteada.getPalavraCifrada());
        // EXIBE A CATEGORIA
        jLabelCategoria1.setText("");
        jLabelCategoria1.setText("Categoria: "+jLabelCategoria1.getText()+this.palavraSorteada.getCategoria());
        jLabelPalavraFrase.repaint();
        // FUNDO PADRÃO
        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Fundo.png"))); // NOI18N
        //NUMERO DE TENTATIVAS ZERADO (=6)
        setnTentativas(6);
        // ATUALIZA PAINEL DE NUMERO DE TENTATIVAS RESTANTES
        jLabelTentativasRestantes.setText(String.valueOf(getnTentativas()));
        jLabelTentativasRestantes.setForeground(Color.BLACK);
        
        // ZERAR TEMPO;
        
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);
        e.setEnabled(true);
        f.setEnabled(true);
        g.setEnabled(true);
        h.setEnabled(true);
        i.setEnabled(true);
        j.setEnabled(true);
        l.setEnabled(true);
        m.setEnabled(true);
        n.setEnabled(true);
        k.setEnabled(true);
        o.setEnabled(true);
        p.setEnabled(true);
        q.setEnabled(true);
        r.setEnabled(true);
        s.setEnabled(true);
        t.setEnabled(true);
        u.setEnabled(true);
        v.setEnabled(true);
        w.setEnabled(true);
        y.setEnabled(true);
        x.setEnabled(true);
        z.setEnabled(true);
        cronometro.cancel();
        cronometro.purge();
        cronometro = new Timer();
        TimerTask tarefa = run();
        contador = 0;
        cronometro.scheduleAtFixedRate(tarefa, 1000, 1000);
    }
    
    public void confere() throws SQLException {

        if (!partida.fimJogo(nTentativas, nLetras, palavraSorteada.getCaracteresSemEspacos(), nomeJogador)) {

            tentativas.add(letra);

            if (palavraSorteada.Contem(letra)) {
                // Acertou - EXIBE A LETRA
                jLabelPalavraFrase.setText(palavraSorteada.getLetraDescobreta(tentativas));
                nLetras = nLetras - palavraSorteada.getNumLetraDescoberta(letra);
                if (partida.fimRodada(nTentativas, nLetras) == true) //MUDAR
                {
                    if (contador <= 20) {
                        power.setUltimaChance(true);
                        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/r_color.jpg"))); // NOI18N
                    }
                    if (power.isVidaExtra() == false) {
                        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_pb.png"))); // NOI18
                    }
                    jLabelPalavraFrase.setText(this.palavraSorteada.getPalavra());
                    jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Venceu.png"))); // NOI18N
                    if (!palavraSorteada.contemEspaco()) {//getfrase ou palavra
                        pontos = 100; // SE PALAVRA
                    } else {
                        pontos = 200;
                    } 
                    pontuacaoTotal = partida.atualizaPontuacao(pontos);
                    jLabelPontuacao2.setText(String.valueOf(pontuacaoTotal));

                    // POWERUP EXIBE LETRA
                    power.setExibeLetra(true);
                    jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/l_color.png"))); // NOI18N

                    if (nTentativas == 6) {
                        power.setVidaExtra(true);
                        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_color.png"))); // NOI18N
                    }
                    
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null, "Parabéns! Você acertou a palavra/frase e ganhou mais "+ pontos +" pontos!\nSua pontuação atual é: " + pontuacaoTotal + ".\n\nClique em OK para continuar");
                    // POWERUP ULTIMA CHANCE
                    /* AGUARDANDO DESENVOLVIMENTO DO TEMPO DE JOGO */
                    // POWERUP VIDA EXTRA
                    

                    novaRodada();
                }
            } else {
                nTentativas = nTentativas - 1;    //vai adicionando o numero de tentativas; se errar, adiciona o desenho
                jLabelTentativasRestantes.setText(String.valueOf(getnTentativas()));
                if(nTentativas<2){
                    jLabelTentativasRestantes.setFont(new Font("Times New Roman", Font.BOLD, 12));
                    jLabelTentativasRestantes.setForeground(Color.RED);
                }
                switch (nTentativas) {
                    case (5): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa1.png"))); // NOI18N
                        break;
                    }
                    case (4): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa2.png"))); // NOI18N
                        break;
                    }
                    case (3): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa3.png"))); // NOI18N
                        break;
                    }
                    case (2): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa4.png"))); // NOI18N
                        break;
                    }
                    case (1): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa5.png"))); // NOI18N
                        break;
                    }
                    case (0): {
                        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa6.png"))); // NOI18N
                        break;
                    }
                    case (-1): {
                        //DatabaseRanking.SalvarRanking(nomeJogador, pontos);
                        if (power.isUltimaChance() == true) {
                            nTentativas = power.ultimaChance(nTentativas);
                            jLabelTentativasRestantes.setText(String.valueOf(nTentativas));
                            power.setUltimaChance(false);
                            jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/r_pb.png"))); // NOI18N
                        } else if (power.isVidaExtra() == true) {
                            
                                jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_pb.png"))); // NOI18
                                power.setVidaExtra(false);
                                JOptionPane.showMessageDialog(null, "Você esta utilizando seu Powerup VIDA EXTRA!");
                                jLabelPalavraFrase.setText(this.palavraSorteada.getPalavra());
                                jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Venceu.png"))); // NOI18N
                                if (formaJogo == 1) {
                                    pontos = 100; // SE PALAVRA
                                } else {
                                    pontos = 200;
                                }
                                pontuacaoTotal = partida.atualizaPontuacao(pontos);
                                jLabelPontuacao2.setText(String.valueOf(pontuacaoTotal));

                                // POWERUP EXIBE LETRA
                                power.setExibeLetra(true);
                                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/l_color.png"))); // NOI18N

                                // POWERUP VIDA EXTRA
                                if (nTentativas == 6) {
                                    power.setVidaExtra(true);
                                    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_color.png"))); // NOI18N
                                }

                                try {
                                    sleep(500);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                JOptionPane.showMessageDialog(null, "Parabéns! Você acertou!\n\n Sua pontuação é: " + pontuacaoTotal + ".\n\nClique em OK para continuar");

                                // POWERUP ULTIMA CHANCE
                                /* AGUARDANDO DESENVOLVIMENTO DO TEMPO DE JOGO */
                                novaRodada();
                            
                            } else {
                                jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Perdeu.png"))); // NOI18N
                                jLabelPalavraFrase.setText(this.palavraSorteada.getPalavra());
                                jLabelPalavraFrase.setText(this.palavraSorteada.getPalavra());
                                 
                                try {
                                    sleep(1000);
                                 } catch (InterruptedException ex) {
                                    Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                                 }
                                partida.fimJogo(nTentativas, nLetras, palavraSorteada.getCaracteresSemEspacos(), nomeJogador);
                                dispose();
                            }
                        break;
                    }

                }
            }

        }

    }
    
    
    // NOVA PARTIDA
    ControlePartida partida = new ControlePartida();
    // INICIA O JOGO
    boolean ok = partida.novoJogo();
    // ATUALIZA PONTOS
    int pontuacaoTotal = partida.atualizaPontuacao(pontos);
    
    Powerup power = new Powerup();
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTeclado = new javax.swing.JPanel();
        i = new javax.swing.JButton();
        e = new javax.swing.JButton();
        u = new javax.swing.JButton();
        f = new javax.swing.JButton();
        j = new javax.swing.JButton();
        p = new javax.swing.JButton();
        w = new javax.swing.JButton();
        x = new javax.swing.JButton();
        v = new javax.swing.JButton();
        d = new javax.swing.JButton();
        n = new javax.swing.JButton();
        o = new javax.swing.JButton();
        z = new javax.swing.JButton();
        m = new javax.swing.JButton();
        h = new javax.swing.JButton();
        s = new javax.swing.JButton();
        c = new javax.swing.JButton();
        b = new javax.swing.JButton();
        r = new javax.swing.JButton();
        g = new javax.swing.JButton();
        y = new javax.swing.JButton();
        a = new javax.swing.JButton();
        l = new javax.swing.JButton();
        t = new javax.swing.JButton();
        q = new javax.swing.JButton();
        k = new javax.swing.JButton();
        jPanelPowerUp = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanelJogador = new javax.swing.JPanel();
        jLabelNomeJogador = new javax.swing.JLabel();
        jLabelJogador = new javax.swing.JLabel();
        jLabelModo = new javax.swing.JLabel();
        jLabelModoJogo = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelPontuacao = new javax.swing.JLabel();
        jLabelPontuacao2 = new javax.swing.JLabel();
        jLabelTempo = new javax.swing.JLabel();
        jLabelTempo2 = new javax.swing.JLabel();
        jLabelTentativasRestantes = new javax.swing.JLabel();
        jLabelTentativas = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanelPalavra = new javax.swing.JPanel();
        jLabelPalavraFrase = new javax.swing.JLabel();
        jPanelJogo = new javax.swing.JPanel();
        jLabelAnimacao = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelCategoria1 = new javax.swing.JLabel();
        jMenuBarMenuPrincipal = new javax.swing.JMenuBar();
        jMenuInf = new javax.swing.JMenu();
        jMenuItemAjuda = new javax.swing.JMenuItem();
        jMenuItemSobre = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelTeclado.setBackground(new java.awt.Color(153, 153, 153));

        i.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        i.setText("I");
        i.setFocusPainted(false);
        i.setPreferredSize(new java.awt.Dimension(39, 23));
        i.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iActionPerformed(evt);
            }
        });

        e.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        e.setText("E");
        e.setFocusPainted(false);
        e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eActionPerformed(evt);
            }
        });

        u.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        u.setText("U");
        u.setFocusPainted(false);
        u.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uActionPerformed(evt);
            }
        });

        f.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        f.setText("F");
        f.setFocusPainted(false);
        f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fActionPerformed(evt);
            }
        });

        j.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        j.setText("J");
        j.setFocusPainted(false);
        j.setPreferredSize(new java.awt.Dimension(39, 23));
        j.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jActionPerformed(evt);
            }
        });

        p.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p.setText("P");
        p.setFocusPainted(false);
        p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pActionPerformed(evt);
            }
        });

        w.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        w.setText("W");
        w.setFocusPainted(false);
        w.setPreferredSize(new java.awt.Dimension(39, 23));
        w.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wActionPerformed(evt);
            }
        });

        x.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        x.setText("X");
        x.setFocusPainted(false);
        x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xActionPerformed(evt);
            }
        });

        v.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        v.setText("V");
        v.setFocusPainted(false);
        v.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vActionPerformed(evt);
            }
        });

        d.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        d.setText("D");
        d.setFocusPainted(false);
        d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dActionPerformed(evt);
            }
        });

        n.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        n.setText("N");
        n.setFocusPainted(false);
        n.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nActionPerformed(evt);
            }
        });

        o.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        o.setText("O");
        o.setFocusPainted(false);
        o.setPreferredSize(new java.awt.Dimension(39, 23));
        o.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oActionPerformed(evt);
            }
        });

        z.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        z.setText("Z");
        z.setFocusPainted(false);
        z.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zActionPerformed(evt);
            }
        });

        m.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        m.setText("M");
        m.setFocusPainted(false);
        m.setPreferredSize(new java.awt.Dimension(39, 23));
        m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mActionPerformed(evt);
            }
        });

        h.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        h.setText("H");
        h.setFocusPainted(false);
        h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hActionPerformed(evt);
            }
        });

        s.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        s.setText("S");
        s.setFocusPainted(false);
        s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sActionPerformed(evt);
            }
        });

        c.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        c.setText("C");
        c.setFocusPainted(false);
        c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cActionPerformed(evt);
            }
        });

        b.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        b.setText("B");
        b.setFocusPainted(false);
        b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActionPerformed(evt);
            }
        });

        r.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        r.setText("R");
        r.setFocusPainted(false);
        r.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rActionPerformed(evt);
            }
        });

        g.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        g.setText("G");
        g.setFocusPainted(false);
        g.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gActionPerformed(evt);
            }
        });

        y.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        y.setText("Y");
        y.setFocusPainted(false);
        y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yActionPerformed(evt);
            }
        });

        a.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        a.setText("A");
        a.setFocusPainted(false);
        a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aActionPerformed(evt);
            }
        });

        l.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        l.setText("L");
        l.setFocusPainted(false);
        l.setPreferredSize(new java.awt.Dimension(39, 23));
        l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lActionPerformed(evt);
            }
        });

        t.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        t.setText("T");
        t.setFocusPainted(false);
        t.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tActionPerformed(evt);
            }
        });

        q.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        q.setText("Q");
        q.setFocusPainted(false);
        q.setPreferredSize(new java.awt.Dimension(39, 23));
        q.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qActionPerformed(evt);
            }
        });

        k.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        k.setText("K");
        k.setFocusPainted(false);
        k.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTecladoLayout = new javax.swing.GroupLayout(jPanelTeclado);
        jPanelTeclado.setLayout(jPanelTecladoLayout);
        jPanelTecladoLayout.setHorizontalGroup(
            jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTecladoLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTecladoLayout.createSequentialGroup()
                        .addComponent(q, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(w, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(e, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(r, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(t, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(y, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(u, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(i, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(o, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTecladoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(g, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(k, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTecladoLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(z, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(x, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(v, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanelTecladoLayout.setVerticalGroup(
            jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTecladoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(q, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(w, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(y, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(u, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(i, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(o, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(g, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelTecladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(z, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(x, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(v, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelPowerUp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PowerUps", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanelPowerUp.setMaximumSize(new java.awt.Dimension(231, 170));
        jPanelPowerUp.setMinimumSize(new java.awt.Dimension(231, 170));
        jPanelPowerUp.setPreferredSize(new java.awt.Dimension(231, 170));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/r_pb.png"))); // NOI18N
        jButton2.setToolTipText("Para adiquirir complete a palavra ou frase em menos de 20 segundos.");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/r_pb_s.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_pb.png"))); // NOI18N
        jButton3.setToolTipText("Para adquirir termina a rodada sem errar nenhuma letra.");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/c_pb_s.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/l_pb.png"))); // NOI18N
        jButton4.setToolTipText("Para adquirir complete uma rodada.");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/l_pb_s.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Exibir letra");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Exibe uma letra da Palavra ou Frase");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Ultima Chance (automático)");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("Perdeu!? Volta uma rodada.");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Vida extra (automático)");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("Perdeu!? Siga jogando.");

        javax.swing.GroupLayout jPanelPowerUpLayout = new javax.swing.GroupLayout(jPanelPowerUp);
        jPanelPowerUp.setLayout(jPanelPowerUpLayout);
        jPanelPowerUpLayout.setHorizontalGroup(
            jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPowerUpLayout.setVerticalGroup(
            jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGroup(jPanelPowerUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(9, 9, 9))
                    .addGroup(jPanelPowerUpLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );

        jPanelJogador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Área do jogador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanelJogador.setMaximumSize(new java.awt.Dimension(231, 170));
        jPanelJogador.setMinimumSize(new java.awt.Dimension(231, 170));
        jPanelJogador.setPreferredSize(new java.awt.Dimension(231, 170));

        jLabelNomeJogador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelNomeJogador.setText(nomeJogador);

        jLabelJogador.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabelJogador.setText("Jogador:");

        jLabelModo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabelModo.setText("Modo de jogo:");

        jLabelModoJogo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        String modo;
        if(formaJogo == 1) {modo = "PALAVRA";}
        else if (formaJogo ==2) {modo = "FRASE";}
        else {modo = "PALAVRA/FRASE";}
        jLabelModoJogo.setText(modo);

        jLabelPontuacao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPontuacao.setText("Pontuação:");

        jLabelPontuacao2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPontuacao2.setText(String.valueOf(pontuacaoTotal));

        jLabelTempo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabelTempo.setText("Tempo (s)");

        jLabelTempo2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabelTentativasRestantes.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabelTentativasRestantes.setText(String.valueOf(nTentativas));

        jLabelTentativas.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabelTentativas.setText("Tentativas restantes: ");

        javax.swing.GroupLayout jPanelJogadorLayout = new javax.swing.GroupLayout(jPanelJogador);
        jPanelJogador.setLayout(jPanelJogadorLayout);
        jPanelJogadorLayout.setHorizontalGroup(
            jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelJogadorLayout.createSequentialGroup()
                        .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                                .addComponent(jLabelModo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelModoJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                                .addComponent(jLabelJogador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNomeJogador))
                            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                                .addComponent(jLabelTentativas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTentativasRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelTempo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTempo2))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelJogadorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabelPontuacao)
                .addGap(18, 18, 18)
                .addComponent(jLabelPontuacao2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelJogadorLayout.setVerticalGroup(
            jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogadorLayout.createSequentialGroup()
                .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelJogador)
                    .addComponent(jLabelNomeJogador))
                .addGap(2, 2, 2)
                .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelModoJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelModo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTentativas)
                    .addComponent(jLabelTentativasRestantes)
                    .addComponent(jLabelTempo)
                    .addComponent(jLabelTempo2))
                .addGap(12, 12, 12)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPontuacao2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPontuacao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jPanelPalavra.setBackground(new java.awt.Color(204, 204, 204));

        jLabelPalavraFrase.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabelPalavraFrase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPalavraFrase.setText("__ __ __ __ __");

        javax.swing.GroupLayout jPanelPalavraLayout = new javax.swing.GroupLayout(jPanelPalavra);
        jPanelPalavra.setLayout(jPanelPalavraLayout);
        jPanelPalavraLayout.setHorizontalGroup(
            jPanelPalavraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPalavraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPalavraFrase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPalavraLayout.setVerticalGroup(
            jPanelPalavraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPalavraLayout.createSequentialGroup()
                .addComponent(jLabelPalavraFrase)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanelJogo.setBackground(new java.awt.Color(255, 255, 255));

        if(nTentativas == 5) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa1.png"))); // NOI18N
        if(nTentativas == 4) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa2.png"))); // NOI18N
        if(nTentativas == 3) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa3.png"))); // NOI18N
        if(nTentativas == 2) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa4.png"))); // NOI18N
        if(nTentativas == 1) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa5.png"))); // NOI18N
        if(nTentativas == 0) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa6.png"))); // NOI18N
        // Código que adiciona o componente ao contêiner pai - não mostrado aqui
        jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Fundo.png")));
        if(nTentativas == 5) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa1.png"))); // NOI18N
        if(nTentativas == 4) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa2.png"))); // NOI18N
        if(nTentativas == 3) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa3.png"))); // NOI18N
        if(nTentativas == 2) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa4.png"))); // NOI18N
        if(nTentativas == 1) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa5.png"))); // NOI18N
        if(nTentativas == 0) jLabelAnimacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Tentativa6.png"))); // NOI18N

        javax.swing.GroupLayout jPanelJogoLayout = new javax.swing.GroupLayout(jPanelJogo);
        jPanelJogo.setLayout(jPanelJogoLayout);
        jPanelJogoLayout.setHorizontalGroup(
            jPanelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabelAnimacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelJogoLayout.setVerticalGroup(
            jPanelJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAnimacao, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelCategoria1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelCategoria1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCategoria1.setText("Categoria: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCategoria1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCategoria1)
                .addContainerGap())
        );

        jMenuBarMenuPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBarMenuPrincipal.setName(""); // NOI18N

        jMenuInf.setText("Informações");

        jMenuItemAjuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAjuda.setText("Ajuda");
        jMenuItemAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAjudaActionPerformed(evt);
            }
        });
        jMenuInf.add(jMenuItemAjuda);

        jMenuItemSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItemSobre.setText("Sobre");
        jMenuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSobreActionPerformed(evt);
            }
        });
        jMenuInf.add(jMenuItemSobre);
        jMenuInf.add(jSeparator1);

        jMenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenuInf.add(jMenuItemSair);

        jMenuBarMenuPrincipal.add(jMenuInf);

        setJMenuBar(jMenuBarMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanelJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelPowerUp, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                            .addComponent(jPanelJogador, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelPalavra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelPowerUp, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelJogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(816, 694));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAjudaActionPerformed
        // TODO add your handling code here:
        MenuAjuda ajuda = new MenuAjuda();
        ajuda.pack();
        ajuda.setVisible(true);
    }//GEN-LAST:event_jMenuItemAjudaActionPerformed

    private void jMenuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSobreActionPerformed
        // TODO add your handling code here:
        MenuSobre sobre = new MenuSobre();
        sobre.pack();
        sobre.setVisible(true);
    }//GEN-LAST:event_jMenuItemSobreActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void iActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iActionPerformed
        try {
            letra = "I";
            i.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_iActionPerformed

    private void eActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eActionPerformed
        try {
            letra = "E";
            e.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_eActionPerformed

    private void uActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uActionPerformed
        try {
            letra = "U";
            u.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_uActionPerformed

    private void fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fActionPerformed
        try {
            letra = "F";
            f.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_fActionPerformed

    private void jActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jActionPerformed
        letra = "J";
        j.setEnabled(false);
        try {
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jActionPerformed

    private void pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pActionPerformed
        letra = "P";
        p.setEnabled(false);
        try {
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pActionPerformed

    private void wActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wActionPerformed
        letra = "W";
        w.setEnabled(false);
        try {
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_wActionPerformed

    private void xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xActionPerformed
        try {
            letra = "X";
            x.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_xActionPerformed

    private void vActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vActionPerformed
        try {
            letra = "V";
            v.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_vActionPerformed

    private void dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dActionPerformed
        try {
            letra = "D";
            d.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dActionPerformed

    private void nActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nActionPerformed
        letra = "N";
        n.setEnabled(false);
        try {
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nActionPerformed

    private void oActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oActionPerformed
        try {
            letra = "O";
            o.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_oActionPerformed

    private void zActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zActionPerformed
        try {
            letra = "Z";
            z.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_zActionPerformed

    private void mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mActionPerformed
        try {
            letra = "M";
            m.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mActionPerformed

    private void hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hActionPerformed
        try {
            letra = "H";
            h.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_hActionPerformed

    private void sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sActionPerformed
        try {
            letra = "S";
            s.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sActionPerformed

    private void cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cActionPerformed
        try {
            letra = "C";
            c.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cActionPerformed

    private void bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActionPerformed
        try {
            letra = "B";
            b.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bActionPerformed

    private void rActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rActionPerformed
        try {
            letra = "R";
            r.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rActionPerformed

    private void gActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gActionPerformed
        try {
            letra = "G";
            g.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_gActionPerformed

    private void yActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yActionPerformed
        try {
            letra = "Y";
            y.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_yActionPerformed

    private void aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aActionPerformed
        try {
            letra = "A";
            a.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_aActionPerformed

    private void lActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lActionPerformed
        try {
            letra = "L";
            l.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lActionPerformed

    private void tActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tActionPerformed
        try {
            letra = "T";
            t.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tActionPerformed

    private void qActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qActionPerformed
        try {
            letra = "Q";
            q.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_qActionPerformed

    private void kActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kActionPerformed
        try {
            letra = "K";
            k.setEnabled(false);
            confere();
        } catch (SQLException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_kActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // POWERUP EXIBE LETRA
        if(power.isExibeLetra()==true){ 
            if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja usar o PowerUp EXIBE LETRA? \n(exibe uma letra na Palavra/Frase)", "PowerUp Exibe letra",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                letra = power.exibeLetra(palavraSorteada.getPalavra(), tentativas);
                //System.out.println(letra);
                power.setExibeLetra(false);
                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/powerup/l_pb.png"))); // NOI18N
                                
                //JButton temp = new javax.swing.JButton();
                if(letra.equals(a.getText())) a.setEnabled(false);
                if(letra.equals(b.getText())) b.setEnabled(false);
                if(letra.equals(c.getText())) c.setEnabled(false);
                if(letra.equals(d.getText())) d.setEnabled(false);
                if(letra.equals(e.getText())) e.setEnabled(false);
                if(letra.equals(f.getText())) f.setEnabled(false);
                if(letra.equals(g.getText())) g.setEnabled(false);
                if(letra.equals(h.getText())) h.setEnabled(false);
                if(letra.equals(i.getText())) i.setEnabled(false);
                if(letra.equals(j.getText())) j.setEnabled(false);
                if(letra.equals(l.getText())) l.setEnabled(false);
                if(letra.equals(m.getText())) m.setEnabled(false);
                if(letra.equals(n.getText())) n.setEnabled(false);
                if(letra.equals(k.getText())) k.setEnabled(false);
                if(letra.equals(o.getText())) o.setEnabled(false);
                if(letra.equals(p.getText())) p.setEnabled(false);
                if(letra.equals(q.getText())) q.setEnabled(false);
                if(letra.equals(r.getText())) r.setEnabled(false);
                if(letra.equals(s.getText())) s.setEnabled(false);
                if(letra.equals(v.getText())) v.setEnabled(false);
                if(letra.equals(x.getText())) x.setEnabled(false);
                if(letra.equals(z.getText())) z.setEnabled(false);
                if(letra.equals(y.getText())) y.setEnabled(false);
                if(letra.equals(w.getText())) w.setEnabled(false);
                
                
                
                try {
                    confere();
                } catch (SQLException ex) {
                    Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else JOptionPane.showMessageDialog(null, "Você ainda não possui o PowerUp EXIBE LETRA, para adiquirir complete a palavra ou frase.");
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(power.isUltimaChance()==true) JOptionPane.showMessageDialog(null, "Este PowerUp é automático. \nCaso você não acerte a palavra/frase, automaticamente terá uma ultima chance.");
        else JOptionPane.showMessageDialog(null, "Você ainda não possui o PowerUp ULTIMA CHANCE, para adiquirir complete a palavra ou frase em menos de 20 segundos.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(power.isVidaExtra()==true) JOptionPane.showMessageDialog(null, "Este PowerUp é automático. \nCaso você não acerte a palavra/frase, automaticamente passará para a próxima rodada sem prejuízo em sua pontuação.");
        else JOptionPane.showMessageDialog(null, "Você ainda não possui o PowerUp VIDA EXTRA, para adiquirir complete a palavra ou frase sem errar nenhum letra.");
    }//GEN-LAST:event_jButton3ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton a;
    private javax.swing.JButton b;
    private javax.swing.JButton c;
    private javax.swing.JButton d;
    private javax.swing.JButton e;
    private javax.swing.JButton f;
    private javax.swing.JButton g;
    private javax.swing.JButton h;
    private javax.swing.JButton i;
    private javax.swing.JButton j;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAnimacao;
    private javax.swing.JLabel jLabelCategoria1;
    private javax.swing.JLabel jLabelJogador;
    private javax.swing.JLabel jLabelModo;
    private javax.swing.JLabel jLabelModoJogo;
    private javax.swing.JLabel jLabelNomeJogador;
    private javax.swing.JLabel jLabelPalavraFrase;
    private javax.swing.JLabel jLabelPontuacao;
    private javax.swing.JLabel jLabelPontuacao2;
    private javax.swing.JLabel jLabelTempo;
    private javax.swing.JLabel jLabelTempo2;
    private javax.swing.JLabel jLabelTentativas;
    private javax.swing.JLabel jLabelTentativasRestantes;
    private javax.swing.JMenuBar jMenuBarMenuPrincipal;
    private javax.swing.JMenu jMenuInf;
    private javax.swing.JMenuItem jMenuItemAjuda;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelJogador;
    private javax.swing.JPanel jPanelJogo;
    private javax.swing.JPanel jPanelPalavra;
    private javax.swing.JPanel jPanelPowerUp;
    private javax.swing.JPanel jPanelTeclado;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton k;
    private javax.swing.JButton l;
    private javax.swing.JButton m;
    private javax.swing.JButton n;
    private javax.swing.JButton o;
    private javax.swing.JButton p;
    private javax.swing.JButton q;
    private javax.swing.JButton r;
    private javax.swing.JButton s;
    private javax.swing.JButton t;
    private javax.swing.JButton u;
    private javax.swing.JButton v;
    private javax.swing.JButton w;
    private javax.swing.JButton x;
    private javax.swing.JButton y;
    private javax.swing.JButton z;
    // End of variables declaration//GEN-END:variables
}

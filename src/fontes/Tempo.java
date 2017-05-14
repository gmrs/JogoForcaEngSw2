/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Or4cl3
 */
public class Tempo
{
    private static Timer cronometro = new Timer();
    private static int contador = 7;
    
    public static void main(String[] args)
    {
        
        TimerTask tarefa = run();
        cronometro.scheduleAtFixedRate(tarefa, 1000, 1000);
    }

    public static TimerTask run()
    {
        return new TimerTask()
        {
            public void run()
            {
                contador=contador-1;
                System.out.println(contador);
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
    
    
}
